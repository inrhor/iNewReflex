package cn.mcres.iNewReflex.listener.place;

import cn.mcres.iNewReflex.api.block.CustomBlockUtil;
import cn.mcres.iNewReflex.api.block.FastGetBlocks;
import cn.mcres.iNewReflex.api.block.FastHandle;
import cn.mcres.iNewReflex.api.block.BlockBuild;
import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlaceBlockNew implements Listener {

    @EventHandler
    void placeCustomBlock(PlayerInteractEvent ev) {
        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (BlocksBuffer.blocksMap.isEmpty()) return;
        if (!ev.hasItem()) return;

        ItemStack handItemStack = ev.getItem();
        Player player = ev.getPlayer();
        Block clickBlock = ev.getClickedBlock();
        Block newBlock;

        if (handItemStack==null) return;
        if (clickBlock==null) return;
        if (clickBlock.getType().equals(Material.AIR)) return;

        Material handMaterial = handItemStack.getType();

        newBlock = clickBlock.getRelative(ev.getBlockFace());

        BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(
                newBlock,
                newBlock.getState(),
                clickBlock,
                handItemStack,
                player,
                true,
                Objects.requireNonNull(ev.getHand()));

        if (FastGetBlocks.isMushRoom(handMaterial)) {
            MultipleFacing mushroomBlockData = (MultipleFacing) Bukkit.createBlockData(handMaterial);
            ev.setCancelled(true); // 去除放置导致的块瞬间切换
            FastHandle.setBlockFacing(mushroomBlockData, true);
            newBlock.setBlockData(mushroomBlockData);
            PlaceUtil.takeItem(player, handItemStack);
            return;
        }

        if (FastGetBlocks.hasCustomBlock(handItemStack)) {
            for (BlockBuild blockBuild : BlocksBuffer.blockList) {
                String blockId = blockBuild.getBlockId();
//                if (!isOldVersion) {
                if (FastGetBlocks.isCustomBlock(handItemStack, blockId)) {
                    BlockBuild item = BlocksBuffer.blockBuildMap.get(blockId);
                    int modelId = item.getModelId();
                    Bukkit.getPluginManager().callEvent(blockPlaceEvent);
                    if (!blockPlaceEvent.canBuild() || blockPlaceEvent.isCancelled()) return;
                    PlaceUtil.takeItem(ev.getPlayer(), handItemStack);
                        MultipleFacing newBlockDataStem = (MultipleFacing) Bukkit.createBlockData(Material.MUSHROOM_STEM);
                        MultipleFacing newBlockDataBrown = (MultipleFacing) Bukkit.createBlockData(Material.BROWN_MUSHROOM_BLOCK);
                        MultipleFacing newBlockDataRed = (MultipleFacing) Bukkit.createBlockData(Material.RED_MUSHROOM_BLOCK);
                        if (CustomBlockUtil.isWithinScope(0, modelId, 64)) {
                            CustomBlockUtil.placeCustomBlock(newBlockDataStem, newBlock, modelId, blockId, ev);
                        } else if (CustomBlockUtil.isWithinScope(64, modelId, 128)) {
                            CustomBlockUtil.placeCustomBlock(newBlockDataBrown, newBlock, modelId, blockId, ev);
                        } else if (CustomBlockUtil.isWithinScope(128, modelId, 192)) {
                            CustomBlockUtil.placeCustomBlock(newBlockDataRed, newBlock, modelId, blockId, ev);
                        } else {
                            return;
                        }
                    }/*else {
                        CustomBlockUtil.placeCustomSkullBlock(newBlock, blockId, blockBuild.getBlockUrl(), ev);
                    }*/
//                }
            }
        }
    }

    @EventHandler
    void onMushroomPhysics(BlockPhysicsEvent event) {
        if (FastGetBlocks.isMushRoom(event.getChangedType())) {
            event.setCancelled(true);
            event.getBlock().getState().update(true, false);
        }
    }
}
