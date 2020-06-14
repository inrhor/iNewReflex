package cn.mcres.iNewReflex.listener.place;

import cn.mcres.iNewReflex.api.block.BlockBuild;
import cn.mcres.iNewReflex.api.block.FastGetBlocks;
import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlaceBlockOld implements Listener {

    @EventHandler
    void placeCustomBlock(PlayerInteractEvent ev) {
        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK  && ev.getHand() != EquipmentSlot.HAND) return;
        if (BlocksBuffer.blocksMap.isEmpty()) return;
        if (!ev.hasItem()) return;

        ItemStack handItemStack = ev.getItem();
        Player player = ev.getPlayer();
        Block clickBlock = ev.getClickedBlock();
        Block newBlock;

        if (handItemStack==null) return;
        if (clickBlock==null) return;
        if (clickBlock.getType().equals(Material.AIR)) return;

        newBlock = clickBlock.getRelative(ev.getBlockFace());

        BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(
                newBlock,
                newBlock.getState(),
                clickBlock,
                handItemStack,
                player,
                true,
                Objects.requireNonNull(ev.getHand()));

        if (FastGetBlocks.hasCustomBlock(handItemStack)) {
            for (BlockBuild blockBuild : BlocksBuffer.blockList) {
                String blockId = blockBuild.getBlockId();
                if (FastGetBlocks.isCustomBlock(handItemStack, blockId)) {
                    Bukkit.getPluginManager().callEvent(blockPlaceEvent);
                    if (!blockPlaceEvent.canBuild() || blockPlaceEvent.isCancelled()) return;
                    PlaceUtil.takeItem(ev.getPlayer(), handItemStack);
                    PlaceUtil.placeCustomSkullBlock(newBlock, blockId, blockBuild.getBlockUrl(), ev);
                    return;
                }
            }
        }
    }
}
