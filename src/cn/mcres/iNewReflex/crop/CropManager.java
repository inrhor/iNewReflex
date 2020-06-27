package cn.mcres.iNewReflex.crop;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.api.block.BlockDropUtil;
import cn.mcres.iNewReflex.api.item.FoodBuild;
import cn.mcres.iNewReflex.expansion.item.food.FoodItem;
import cn.mcres.iNewReflex.load.checkPlugin.BlockStoreLib;
import cn.mcres.iNewReflex.utils.LoreReplace;
import cn.mcres.iNewReflex.utils.Nbt;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import me.arasple.mc.trhologram.hologram.Hologram;
import net.sothatsit.blockstore.BlockStoreApi;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CropManager {
    public static INewReflex getMain() {
        return INewReflex.getMain();
    }

    public static boolean hasData(Block block) {
        if (BlockStoreLib.blockStoreEnable) {
            return BlockStoreApi.containsBlockMeta(block, getMain(), "iNewReflex:cropId");
        }
        return false;
    }

    public static void saveDataAll(Block block, String cropId, String cropUUID, int growthValueStart) {
        if (BlockStoreLib.blockStoreEnable) {
            BlockStoreApi.setBlockMeta(block, getMain(), "iNewReflex:cropId", cropId);
            BlockStoreApi.setBlockMeta(block, getMain(), "iNewReflex:cropUUID", cropUUID);
            BlockStoreApi.setBlockMeta(block, getMain(), "iNewReflex:cropGrowthValue", growthValueStart);
        }
    }

    public static String getCropId(Block block) {
        return (String) BlockStoreApi.getBlockMeta(block, getMain(), "iNewReflex:cropId");
    }

    public static void setGrowthValue(Block block, int growthValue) {
        if (hasData(block)) {
            BlockStoreApi.setBlockMeta(block, getMain(), "iNewReflex:cropGrowthValue", growthValue);
        }
    }

    public static int getGrowthValue(Block block) {
        if (hasData(block)) {
            return (int) BlockStoreApi.getBlockMeta(block, getMain(), "iNewReflex:cropGrowthValue");
        }
        return 0;
    }

    public static UUID getCropUUID(Block block) {
        return UUID.fromString((String) BlockStoreApi.getBlockMeta(block, getMain(), "iNewReflex:cropUUID"));
    }

    public static void addGrowthValue(Block block, int AddGrowthValue) {
        if (hasData(block)) {
            String key = "iNewReflex:cropGrowthValue";
            int growthValue = (int) BlockStoreApi.getBlockMeta(block, getMain(), key);
            BlockStoreApi.setBlockMeta(block, getMain(), key, growthValue+AddGrowthValue);
        }
    }

    /**
     * 采集
     * @param foodBuild
     * @param loc
     * @param player
     * @param block
     */
    public static void cropHarvest(FoodBuild foodBuild, Location loc, Player player, Block block) {
        UUID cropUUID = CropManager.getCropUUID(block);
        Location newLoc = loc.add(foodBuild.getCropShowX(), foodBuild.getCropShowY(), foodBuild.getCropShowZ());
        CropData.cropHarvest.putIfAbsent(cropUUID, 0);
        int nowHarvest = CropData.cropHarvest.get(cropUUID);
        int maxHarvest = foodBuild.getHarvestMaxValue();
        if (nowHarvest < maxHarvest) {
            if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                ItemStack handItem = player.getInventory().getItemInMainHand();
                String cropHarvestTag = "iNewReflex_item_cropHarvest_Add";
                if (handItem.hasItemMeta()
                        && Nbt.hasItemMetadataInt(handItem, cropHarvestTag)) {
                    /* 消耗耐久性代码待写 */
                    // code... //
                    cropHarvestUpdate(cropUUID, Nbt.getItemMetadataInt(handItem, cropHarvestTag));
                }else {
                    cropHarvestUpdate(cropUUID, 1);
                }
            }else {
                cropHarvestUpdate(cropUUID, 1);
            }
        }else {
            removeCrop(block, cropUUID);
            int nowGrowth = getGrowthValue(block);
            int maxGrowth = foodBuild.getGrowthRipe();
            if (nowGrowth >= maxGrowth) {
                for (String s : foodBuild.getCropRewards()) {
                    BlockDropUtil.dropList(s, block.getLocation(), player);
                }
            }
            return;
        }
        int showTime = foodBuild.getCropShowTime();
        List<String> newTextList = LoreReplace.loreHarvest(CropData.harvest, nowHarvest, maxHarvest);
        senHD(foodBuild, loc, player, newLoc, showTime, newTextList);
    }

    /**
     * 更新农作物收割采集进度信息
     * @param cropUUID
     * @param value
     */
    private static void cropHarvestUpdate(UUID cropUUID, int value) {
        CropData.cropHarvest.put(cropUUID, CropData.cropHarvest.get(cropUUID)+value);
    }

    /**
     * 农作物信息
     * @param foodBuild
     * @param loc
     * @param player
     * @param newLoc
     * @param showTime
     * @param newTextList
     */
    private static void senHD(FoodBuild foodBuild, Location loc, Player player, Location newLoc, int showTime, List<String> newTextList) {
        if (foodBuild.getCropShowUse().equals("hd")) {
            com.gmail.filoghost.holographicdisplays.api.Hologram hdHologram = HologramsAPI.createHologram(getMain(), newLoc);
            for (String text : newTextList) {
                hdHologram.appendTextLine(text.replaceAll("&", "§"));
            }
            VisibilityManager visibilityManager = hdHologram.getVisibilityManager();
            visibilityManager.showTo(player);
            visibilityManager.setVisibleByDefault(false);
            new BukkitRunnable() {
                public void run() {
                    hdHologram.delete();
                }
            }.runTaskLaterAsynchronously(getMain(), showTime);
        }else if(foodBuild.getCropShowUse().equals("tr")) {
            Hologram trHologram = Hologram.Companion.createHologram(
                    getMain(),
                    String.valueOf(loc),
                    newLoc,
                    newTextList);
            trHologram.display();
            new BukkitRunnable() {
                public void run() {
                    trHologram.delete();
                }
            }.runTaskLaterAsynchronously(getMain(), showTime);
        }
    }

    /**
     * 农作物信息
     * @param foodBuild
     * @param loc
     * @param player
     * @param block
     */
    public static void cropShow(FoodBuild foodBuild, Location loc, Player player, Block block) {
        Location newLoc = loc.add(foodBuild.getCropShowX(), foodBuild.getCropShowY(), foodBuild.getCropShowZ());
        int nowGrowth = getGrowthValue(block);
        int maxGrowth = foodBuild.getGrowthRipe();
        int showTime = foodBuild.getCropShowTime();
        List<String> newTextList = replaceAll(foodBuild.getCropShowTextList(), nowGrowth, maxGrowth);
        senHD(foodBuild, loc, player, newLoc, showTime, newTextList);
    }

    private static List<String> replaceAll(List<String> stringList, int nowGrowth, int maxGrowth) {
        String tag = CropData.notRipeTag;
        if (nowGrowth >= maxGrowth) {
            tag = CropData.ripeTag;
        }
        List<String> newList = new ArrayList<>();
        for (String string : stringList) {
            newList.add(string.toLowerCase()
                    .replaceAll("@inewrx_cropnowgrowth", String.valueOf(nowGrowth))
                    .replaceAll("@inewrx_cropmaxgrowth", String.valueOf(maxGrowth))
                    .replaceAll("@inewrx_cropripe", tag));
        }
        return newList;
    }

    public static void task() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<UUID> iterator = CropData.cropDataList.iterator();
                while (iterator.hasNext()) {
                    UUID cropUUID = iterator.next();
                    Location loc = CropData.cropDataMap.get(cropUUID);
                    Block crop = loc.getBlock();
                    if (!(crop.getState() instanceof Skull)) {
                        removeCrop(crop, cropUUID);
                        continue;
                    }
                    String itemId = CropManager.getCropId(crop);
                    FoodBuild foodBuild = FoodItem.foodItemList.get(itemId);
                    if (!checkHasDadBlock(foodBuild, crop)) {
                        crop.setType(Material.AIR);
                        CropData.cropDataMap.remove(cropUUID);
                        iterator.remove();
                        break;
                    }
                    if (!CropManager.hasData(crop)) continue;
                    if (CropManager.getGrowthValue(crop) < foodBuild.getGrowthRipe()) {
                        CropManager.addGrowthValue(crop, foodBuild.getCropGrowthAddValue());
                    }
                }
            }
        }.runTaskTimer(getMain(), 0, CropData.cropTime);

        new BukkitRunnable() {
            @Override
            public void run() {
                CropData.saveData();
            }
        }.runTaskTimer(getMain(), 0, CropData.cropSaveTime);
    }

    /**
     * 检查农作物的系块
     * @param foodBuild
     * @param block
     */
    public static boolean checkHasDadBlock(FoodBuild foodBuild, Block block) {
        String cropFace = foodBuild.getCropFace();
        List<Material> materialList = foodBuild.getCropMaterialList();
        // NORTH EAST SOUTH WEST UP DOWN ALL
        UUID cropUUID = CropManager.getCropUUID(block);
        switch (cropFace) {
            case "ALL":
                if (!hasDadBlock(block, materialList)) {
                    return false;
                }
                break;
            case "NORTH":
                if (!equalsMaterial(block.getRelative(BlockFace.SOUTH).getType(), materialList)) {
                    return false;
                }
                break;
            case "EAST":
                if (!equalsMaterial(block.getRelative(BlockFace.WEST).getType(), materialList)) {
                    return false;
                }
                break;
            case "SOUTH":
                if (!equalsMaterial(block.getRelative(BlockFace.NORTH).getType(), materialList)) {
                    return false;
                }
                break;
            case "WEST":
                if (!equalsMaterial(block.getRelative(BlockFace.EAST).getType(), materialList)) {
                    return false;
                }
                break;
            case "UP":
                if (!equalsMaterial(block.getRelative(BlockFace.DOWN).getType(), materialList)) {
                    return false;
                }
                break;
            case "DOWN":
                if (!equalsMaterial(block.getRelative(BlockFace.UP).getType(), materialList)) {
                    return false;
                }
                break;
        }
        return true;
    }

    /**
     * 放置农作物
     * @param block
     * @param blockFace
     */
    public static void setCropBlock(Block block, BlockFace blockFace) {
        // PLAYER_HEAD 放置在地上
        // PLAYER_WALL_HEAD 放置在墙壁
        // 不可放置于一个块的下面
        if (blockFace.equals(BlockFace.UP)) {
            block.setType(ReturnMaterial.getMaterial("PLAYER_HEAD"));
        }else {
            block.setType(ReturnMaterial.getMaterial("PLAYER_WALL_HEAD"));
            if (block.getBlockData() instanceof Directional) {
                Directional dir = (Directional) block.getBlockData();
                if (blockFace.equals(BlockFace.DOWN)) {
                    hasAndSetBlockFace(block, dir);
                }else {
                    dir.setFacing(blockFace);
                }
                block.setBlockData(dir);
            }
        }
    }

    // 为了让PLAYER_WALL_HEAD附在某块的面
    private static void hasAndSetBlockFace(Block block, Directional dir) {
        final BlockFace[] blockFaces = new BlockFace[]{
                BlockFace.EAST, BlockFace.WEST,
                BlockFace.SOUTH, BlockFace.NORTH};
        for (BlockFace blockFace : blockFaces) {
            Material material = block.getRelative(blockFace).getType();
            if (!material.equals(Material.AIR)
                    && !material.equals(ReturnMaterial.getMaterial("PLAYER_HEAD"))
                    && !material.equals(ReturnMaterial.getMaterial("PLAYER_WALL_HEAD"))) {
                if (blockFace.equals(BlockFace.EAST)) {
                    dir.setFacing(BlockFace.WEST);
                }else if (blockFace.equals(BlockFace.WEST)) {
                    dir.setFacing(BlockFace.EAST);
                }else if (blockFace.equals(BlockFace.NORTH)) {
                    dir.setFacing(BlockFace.SOUTH);
                }else if (blockFace.equals(BlockFace.SOUTH)) {
                    dir.setFacing(BlockFace.NORTH);
                }
                return;
            }
        }
    }

    private static boolean hasDadBlock(Block block, List<Material> materialList) {
        final BlockFace[] blockFaces = new BlockFace[]{
                BlockFace.EAST, BlockFace.WEST,
                BlockFace.SOUTH, BlockFace.NORTH,
                BlockFace.DOWN, BlockFace.UP};
        for (BlockFace blockFace : blockFaces) {
            if (equalsMaterial(block.getRelative(blockFace).getType(), materialList)) return true;
        }
        return false;
    }

    private static boolean equalsMaterial(Material material, List<Material> materialList) {
        for (Material type : materialList) {
            if (material.equals(type)) return true;
        }
        return false;
    }

    private static void removeCrop(Block block, UUID cropUUID) {
        block.setType(Material.AIR);
        CropData.removeMap(cropUUID);
    }
}
