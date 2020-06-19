package cn.mcres.iNewReflex.crop;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.api.block.BlockDropUtil;
import cn.mcres.iNewReflex.api.item.FoodBuild;
import cn.mcres.iNewReflex.expansion.item.food.FoodItem;
import cn.mcres.iNewReflex.load.checkPlugin.BlockStoreLib;
import cn.mcres.iNewReflex.utils.LoreReplace;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import java.util.ArrayList;
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

    public static void cropHarvest(FoodBuild foodBuild, Location loc, Player player, Block block) {
        UUID cropUUID = CropManager.getCropUUID(block);
        Location newLoc = loc.add(foodBuild.getCropShowX(), foodBuild.getCropShowY(), foodBuild.getCropShowZ());
        CropData.cropHarvest.putIfAbsent(cropUUID, 0);
        int nowHarvest = CropData.cropHarvest.get(cropUUID);
        int maxHarvest = foodBuild.getHarvestMaxValue();
        if (nowHarvest < maxHarvest) {
            CropData.cropHarvest.put(cropUUID, CropData.cropHarvest.get(cropUUID)+1);
        }else {
            block.setType(Material.AIR);
            CropData.removeMap(cropUUID);
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
                for (UUID cropUUID : CropData.cropDataList) {
                    Location loc = CropData.cropDataMap.get(cropUUID);
                    if (!(loc.getBlock().getState() instanceof Skull)) continue;
                    Block crop = loc.getBlock();
                    String itemId = CropManager.getCropId(crop);
                    FoodBuild foodBuild = FoodItem.foodItemList.get(itemId);
                    if (!CropManager.hasData(crop)) continue;
                    if (CropManager.getGrowthValue(crop) < foodBuild.getGrowthRipe()) {
                        CropManager.addGrowthValue(crop, foodBuild.getCropGrowthAddValue());
                    }
                }
            }
        }.runTaskTimer(getMain(), 0, CropData.cropTime);

        new BukkitRunnable() {
            public void run() {
                CropData.saveData();
            }
        }.runTaskTimer(getMain(), 0, CropData.cropSaveTime);
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

    public static void hasAndSetBlockFace(Block block, Directional dir) {
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
}
