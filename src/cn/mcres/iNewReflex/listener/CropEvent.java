package cn.mcres.iNewReflex.listener;

import cn.mcres.iNewReflex.api.item.FoodBuild;
import cn.mcres.iNewReflex.crop.CropData;
import cn.mcres.iNewReflex.crop.CropManager;
import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import cn.mcres.iNewReflex.expansion.item.food.FoodItem;
import cn.mcres.iNewReflex.listener.place.PlaceUtil;
import cn.mcres.iNewReflex.utils.NBTEditor;
import cn.mcres.iNewReflex.utils.Nbt;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CropEvent implements Listener {
    @EventHandler
    void breakMaterial(BlockBreakEvent ev) {
        Block block = ev.getBlock();
        Material material = block.getType();
        if (CropData.cropConditions.get(material) != null) {
            List<String> list = CropData.cropConditions.get(material);
            Random rand = new Random();
            int randInt = rand.nextInt(list.size());
            String[] result = list.get(randInt).split(":");
            int amount = Integer.parseInt(result[1]);
            double chance = Double.parseDouble(result[2]);
            Location loc = block.getLocation();
            String itemId = result[0];
            if (FoodItem.foodItemList.get(itemId) == null) return;
            if (ThreadLocalRandom.current().nextDouble() < chance) {
                while (amount-- > 0) {
                    loc.getWorld().dropItem(loc, FoodItem.foodItemList.get(itemId).getItem());
                }
            }
        }
    }

    @EventHandler
    void breakCrop(BlockBreakEvent ev) {
        Block block = ev.getBlock();
        if (CropManager.hasData(block)) {
            UUID cropUUID = CropManager.getCropUUID(block);
            CropData.removeMap(cropUUID);
        }
    }

    @EventHandler
    void clickCrop(PlayerInteractEvent ev) {
        Block clickBlock = ev.getClickedBlock();
        if (clickBlock == null) return;
        if (clickBlock.getType().equals(Material.AIR)) return;
        if (!CropManager.hasData(clickBlock)) return;
        Player player = ev.getPlayer();
        String itemId = CropManager.getCropId(clickBlock);
        if (FoodItem.foodItemList.get(itemId) == null) return;
        FoodBuild foodBuild = FoodItem.foodItemList.get(itemId);
        ev.setCancelled(true);
        Location loc = clickBlock.getLocation();
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK && ev.getHand() == EquipmentSlot.HAND) {
            CropManager.cropShow(foodBuild, loc, player, clickBlock);
        }
        if (ev.getAction() == Action.LEFT_CLICK_BLOCK) {
            CropManager.cropHarvest(foodBuild, loc, player, clickBlock);
        }
    }

    @EventHandler
    void place(PlayerInteractEvent ev) {
        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!ev.hasItem()) return;

        ItemStack itemStack = ev.getItem();

        if (itemStack == null) return;

        if (!Nbt.hasItemMetadataString(itemStack, "iNewReflex_food_id")) return;
        String itemId = Nbt.getItemMetadataString(itemStack, "iNewReflex_food_id");
        if (FoodItem.foodItemList.get(itemId) == null) return;
        FoodBuild foodBuild = FoodItem.foodItemList.get(itemId);
        if (!foodBuild.isCropCanPlace()) return;

        Block clickBlock = ev.getClickedBlock();

        if (clickBlock == null) return;
        if (clickBlock.getType().equals(Material.AIR)) return;

        if (!canPlace(foodBuild.getCropMaterialList(), clickBlock.getType())) return;

        BlockFace blockFace = ev.getBlockFace();

        if (!isCanPlaceFace(blockFace, foodBuild.getCropFace())) return;

        Block newBlock = clickBlock.getRelative(blockFace);

        Player player = ev.getPlayer();

        BlockPlaceEvent blockPlaceEvent = new BlockPlaceEvent(
                newBlock,
                newBlock.getState(),
                clickBlock,
                itemStack,
                player,
                true,
                Objects.requireNonNull(ev.getHand()));
        Bukkit.getPluginManager().callEvent(blockPlaceEvent);
        if (!blockPlaceEvent.canBuild() || blockPlaceEvent.isCancelled()) return;

        newBlock.setType(/*ReturnMaterial.skullItem()*/ReturnMaterial.getMaterial("PLAYER_HEAD"));

        NBTEditor.setSkullTexture(newBlock, foodBuild.getCropUrl());

        UUID cropUUID = UUID.randomUUID();
        CropData.saveMap(cropUUID, newBlock.getLocation());
        CropManager.saveDataAll(newBlock, foodBuild.getItemId(), String.valueOf(cropUUID), 0);

        PlaceUtil.takeItem(player, itemStack);
//        itemStack.setAmount(itemStack.getAmount()-1);
    }

    private static boolean canPlace(List<Material> materials, Material blockType) {
        for (Material material : materials) {
            if (material.equals(blockType)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCanPlaceFace(BlockFace blockFace, String face) {
        String newFace = face.toUpperCase();
        switch (newFace) {
            case "ALL":
                return true;
            case "NORTH":
                return blockFace.equals(BlockFace.NORTH);
            case "EAST":
                return blockFace.equals(BlockFace.EAST);
            case "SOUTH":
                return blockFace.equals(BlockFace.SOUTH);
            case "WEST":
                return blockFace.equals(BlockFace.WEST);
            case "UP":
                return blockFace.equals(BlockFace.UP);
            case "DOWN":
                return blockFace.equals(BlockFace.DOWN);
        }
        return false;
    }
}
