package cn.mcres.iNewReflex.listener;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.api.block.BlockBuild;
import cn.mcres.iNewReflex.api.block.BlockDropUtil;
import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import cn.mcres.iNewReflex.load.checkPlugin.ProLib;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import net.sothatsit.blockstore.BlockStoreApi;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


public class BreakBlock implements Listener {
    // 正在破坏方块的位置
    private static HashMap<Player, Location> breakingBlock = new LinkedHashMap<>();
    // 正在破坏时长计数
    private static HashMap<Player, Integer> breakingBlockTime = new LinkedHashMap<>();
    // 破坏块裂缝阶段 0-9
    private static HashMap<Player, Integer> breakBlockStage = new LinkedHashMap<>();
    // 块被破坏时存入位置
    private static HashMap<Player, Location> breakBlockLocation = new LinkedHashMap<>();

    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent event) {
        Player player = event.getPlayer();

        if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;

        Set<Material> transparentBlocks = new HashSet<>();
        transparentBlocks.add(Material.AIR);
        Block block = player.getTargetBlock(transparentBlocks, 5);

        if (BlockStoreApi.containsBlockMeta(block, INewReflex.getMain(), "iNewReflex:block")) {

            String blockId = (String) BlockStoreApi.getBlockMeta(block, INewReflex.getMain(), "iNewReflex:block");

            // 阻止块破坏
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, -1, false, false), true);

            BlockBuild blockBuild = BlocksBuffer.blockBuildMap.get(blockId);
            if (blockBuild.getCanBreak()) {
                if (breakBlockLocation.get(player) == null) {
                    breakBlockLocation.put(player, block.getLocation());
                }

                // 自定义块破坏动画
                int strength = blockBuild.getBreakStrength();
                if (breakingBlock.get(player) == null) {
                    breakingBlock.put(player, block.getLocation());
                    breakingBlockTime.put(player, 1);
                    breakBlockStage.put(player, 0);
                } else {
                    int time = finallyBreakingBlockTime(player, blockBuild);
                    int blockStage = breakBlockStage.get(player);
                    if (time > blockStage * strength) {
                        breakBlockStage.put(player, blockStage + 1);
                        int newBlockStage = breakBlockStage.get(player);
                        if (newBlockStage < 9) {
                            breakBlockAnimation(player, block, newBlockStage);
                        }else {
                            BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
                            Bukkit.getPluginManager().callEvent(blockBreakEvent);
                            if (!blockBreakEvent.isCancelled()) {
                                block.setType(Material.AIR);
                                // 掉落
                                Location location = block.getLocation();
                                BlockDropUtil.dropItem(blockBuild, location, player);
                            }
                            removeBreakState(player, block);
                        }
                    }
                    breakingBlockTime.put(player, time + 1);
                }
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (breakBlockLocation.get(player) != null) {
                        if (!breakBlockLocation.get(player).equals(block.getLocation())) {
                            removeBreakState(player, block);
                        }
                    }
                }
            }.runTaskLaterAsynchronously(INewReflex.getMain(), 1);
        }else {
            removeBreakState(player, block);
            vanillaBlockStrength(player);
        }
    }

    private static void vanillaBlockStrength(Player player) {
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem.hasItemMeta()) {
                ItemMeta handMeta = handItem.getItemMeta();
                NamespacedKey key = new NamespacedKey(INewReflex.getMain(), "iNewReflex_item_vanillaBlock_canAdd");
                if (handMeta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                    int breakVanillaValue = handMeta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 5, breakVanillaValue, false, false), true);
                }
            }
        }
    }

    private static int finallyBreakingBlockTime(Player player, BlockBuild blockBuild) {
        int finallyValue = 0;
        int time = breakingBlockTime.get(player);
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            if (handItem.hasItemMeta()) {
                ItemMeta handMeta = handItem.getItemMeta();
                NamespacedKey key = new NamespacedKey(INewReflex.getMain(), "iNewReflex_item_customBlock_can");
                if (handMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                    String blockStrengthType = blockBuild.getBreakType();
                    switch (blockStrengthType) {
                        case "1":
                            NamespacedKey key1 = new NamespacedKey(INewReflex.getMain(), "iNewReflex_item_customBlock_1");
                            finallyValue = handMeta.getPersistentDataContainer().get(key1, PersistentDataType.INTEGER);
                            break;
                        case "2":
                            NamespacedKey key2 = new NamespacedKey(INewReflex.getMain(), "iNewReflex_item_customBlock_2");
                            finallyValue = handMeta.getPersistentDataContainer().get(key2, PersistentDataType.INTEGER);
                            break;
                        case "3":
                            NamespacedKey key3 = new NamespacedKey(INewReflex.getMain(), "iNewReflex_item_customBlock_3");
                            finallyValue = handMeta.getPersistentDataContainer().get(key3, PersistentDataType.INTEGER);
                            break;
                        default:
                            NamespacedKey key0 = new NamespacedKey(INewReflex.getMain(), "iNewReflex_item_customBlock_0");
                            finallyValue = handMeta.getPersistentDataContainer().get(key0, PersistentDataType.INTEGER);
                    }
                }
            }
        }
        return time + finallyValue;
    }

    private static void removeBreakState(Player player, Block block) {
        breakingBlock.remove(player);
        breakingBlockTime.remove(player);
        breakBlockStage.remove(player);
        breakBlockLocation.remove(player);
        if (!block.isEmpty()) {
            breakBlockAnimation(player, block, 10);
        }
    }

    private static void breakBlockAnimation(Player player, Block block, int stage) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
        packet.getBlockPositionModifier().write(0, new BlockPosition(
                block.getX(),
                block.getY(),
                block.getZ()));
        packet.getIntegers().write(1, stage); //裂纹阶段 0-9
        try {
            ProLib.protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
