package cn.mcres.iNewReflex.crop;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.fileYaml.crop.CreateCropDataYaml;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;

public class CropData {
    public static INewReflex getMain() {
        return INewReflex.getMain();
    }

    // 为了具体操作
    public static HashMap<UUID, Location> cropDataMap = new LinkedHashMap<>();
    // 为了在定时器使用，IO操作
    public static List<UUID> cropDataList = new ArrayList<>();

    // 收割操作
    public static HashMap<UUID, Integer> cropHarvest = new LinkedHashMap<>();

    public static int cropTime, cropSaveTime;
    public static String ripeTag, notRipeTag, harvestTag;
    public static List<String> harvest;

    // 为了破坏材料获得农作物的操作
    public static HashMap<Material, List<String>> cropConditions = new LinkedHashMap<>();

    public static void load() {
        FileConfiguration config = getMain().getConfig();
        cropTime = config.getInt("crop.growthTime")*20;
        cropSaveTime = config.getInt("crop.saveTime")*20;
        ripeTag = config.getString("crop.ripeTag").replaceAll("&", "§");
        notRipeTag = config.getString("crop.notRipeTag").replaceAll("&", "§");
        harvestTag = config.getString("crop.harvestTag").replaceAll("&", "§");
        harvest = config.getStringList("crop.harvest");
        for (String conditions : config.getConfigurationSection("crop.conditions").getKeys(false)) {
            cropConditions.put(ReturnMaterial.getMaterial(conditions), config.getStringList("crop.conditions."+conditions));
        }
        FileConfiguration cropData = CreateCropDataYaml.cropDataYaml;
        if (!cropData.contains("crops")) return;
        for (String cropUUIDString : cropData.getConfigurationSection("crops").getKeys(false)) {
            Location loc = getSkullLoc(cropData, cropUUIDString);
            if (loc.getBlock().getState() instanceof Skull) {
                if (CropManager.hasData(loc.getBlock())) {
                    UUID cropUUID = UUID.fromString(cropUUIDString);
                    saveMap(cropUUID, loc);
                }
            }
        }
    }

    public static void saveMap(UUID cropUUID, Location loc) {
        cropDataMap.put(cropUUID, loc);
        if (!cropDataList.contains(cropUUID)) {
            cropDataList.add(cropUUID);
        }
    }

    public static void removeMap(UUID cropUUID) {
        cropDataMap.remove(cropUUID);
        cropDataList.remove(cropUUID);
    }

    public static void saveData() {
        FileConfiguration cropData = CreateCropDataYaml.cropDataYaml;
        if (!cropData.contains("crops")) return;
        for (String cropUUIDString : cropData.getConfigurationSection("crops").getKeys(false)) {
            Location loc = getSkullLoc(cropData, cropUUIDString);
            if (!(loc.getBlock().getState() instanceof Skull) || !CropManager.hasData(loc.getBlock()) ) {
                delData(cropData, cropUUIDString);
            }
        }
        for (UUID cropUUID : cropDataList) {
            String cropUUIDString = String.valueOf(cropUUID);
            Location loc = cropDataMap.get(cropUUID);
            String world = loc.getWorld().getName();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            if (loc.getBlock().getState() instanceof Skull) {
                if (CropManager.hasData(loc.getBlock())) {
                    cropData.set("crops." + cropUUIDString + ".location.world", world);
                    cropData.set("crops." + cropUUIDString + ".location.x", x);
                    cropData.set("crops." + cropUUIDString + ".location.y", y);
                    cropData.set("crops." + cropUUIDString + ".location.z", z);
                }
            }
        }
        saveYaml(cropData);
    }

    private static Location getSkullLoc(FileConfiguration cropData, String cropUUIDString) {
        World world = Bukkit.getWorld(cropData.getString("crops." + cropUUIDString + ".location.world"));
        double x = cropData.getDouble("crops." + cropUUIDString + ".location.x");
        double y = cropData.getDouble("crops." + cropUUIDString + ".location.y");
        double z = cropData.getDouble("crops." + cropUUIDString + ".location.z");
        return new Location(world, x, y, z);
    }

    private static void delData(FileConfiguration cropData, String cropUUIDString) {
        if (cropData.contains("crops." + cropUUIDString)) {
            cropData.set("crops." + cropUUIDString, null);
        }
    }

    private static void saveYaml(FileConfiguration cropData) {
        File yaml = new File(INewReflex.getMain().getDataFolder(), "cropData.yml");
        try {
            cropData.save(yaml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
