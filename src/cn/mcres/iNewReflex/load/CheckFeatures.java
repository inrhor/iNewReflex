package cn.mcres.iNewReflex.load;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.crop.CropData;
import cn.mcres.iNewReflex.crop.CropManager;
import cn.mcres.iNewReflex.expansion.item.food.FoodItem;
import cn.mcres.iNewReflex.expansion.item.material.MaterialItem;
import cn.mcres.iNewReflex.expansion.recipe.BlockRecipe;
import cn.mcres.iNewReflex.expansion.recipe.MaterialRecipe;
import cn.mcres.iNewReflex.expansion.recipe.ToolRecipe;
import cn.mcres.iNewReflex.expansion.recipe.WeaponRecipe;
import cn.mcres.iNewReflex.fileYaml.YamlUpdate;
import cn.mcres.iNewReflex.fileYaml.block.CreateBlocksYaml;
import cn.mcres.iNewReflex.fileYaml.mobsBook.CreateMobsBookYaml;
import cn.mcres.iNewReflex.listener.*;
import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import cn.mcres.iNewReflex.expansion.item.tool.ToolItem;
import cn.mcres.iNewReflex.expansion.item.weapon.MeleeItem;
import cn.mcres.iNewReflex.listener.place.PlaceBlockNew;
import cn.mcres.iNewReflex.listener.place.PlaceBlockOld;
import cn.mcres.iNewReflex.load.checkPlugin.*;
import cn.mcres.iNewReflex.mobsBook.MobsBookManager;
import cn.mcres.iNewReflex.mythicmobs.MythicMobsEvents;
import cn.mcres.iNewReflex.utils.LogUtil;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CheckFeatures {
    public static boolean openMMAttack;

    public static boolean mobFeature = false;

    public static void load() {
        JavaPlugin javaPlugin = INewReflex.getMain();
        FileConfiguration config = javaPlugin.getConfig();

        LogUtil.send("&r┝ §3功能");

        if (config.getBoolean("features.mythicMobs")) {
            if (ImiPetLib.imiPetEnable && MMLib.MMLibEnable) {
                mobFeature = true;
                javaPlugin.getServer().getPluginManager().registerEvents(new MythicMobsEvents(), javaPlugin);
                LogUtil.send("&r┝ §a已启用功能：§r生物");
                openMMAttack = true;
            }else {
                LogUtil.send("&r┝ §c未启用功能：§r生物");
            }
        }else {
            LogUtil.send("&r┝ §c未启用功能：§r生物");
        }

        if (config.getBoolean("features.block")) {
//            if (!INewReflex.getInfo().isOldVersion()) {
            File blocksFile = new File(javaPlugin.getDataFolder(), "blocks.yml");
            try {
                YamlUpdate.update(javaPlugin, "blocks.yml", blocksFile, Collections.emptyList());
            } catch (IOException e) {
                LogUtil.send("update blocks error");
            }
            CreateBlocksYaml.reload();
            // 注册事件
            if (INewReflex.getInfo().isOldVersion()) {
                javaPlugin.getServer().getPluginManager().registerEvents(new PlaceBlockOld(), javaPlugin);
            }else {
                javaPlugin.getServer().getPluginManager().registerEvents(new PlaceBlockNew(), javaPlugin);
            }
            // 注册方块，放入缓冲区
            new BlocksBuffer().run();
            LogUtil.send("&r┝ §a已启用功能：§r方块");
            /*}else {
                LogUtil.send("&r┝ §c版本过低，不可使用功能：§r方块");
            }*/
        }else {
            LogUtil.send("&r┝ §c未启用功能：§r方块");
        }

        if (config.getBoolean("features.weapon")) {
            // 加载物品
            new MeleeItem().loadAll();
            LogUtil.send("&r┝ §a已启用功能：§r武器");
        }else {
            LogUtil.send("&r┝ §c未启用功能：§r武器");
        }

        if (config.getBoolean("features.tool")) {
            new ToolItem().loadAll();
            LogUtil.send("&r┝ §a已启用功能：§r工具");
        }else {
            LogUtil.send("&r┝ §c未启用功能：§r工具");
        }

        if (config.getBoolean("features.material")) {
            new MaterialItem().loadAll();
            LogUtil.send("&r┝ §a已启用功能：§r装饰材料");
        }else {
            LogUtil.send("&r┝ §c未启用功能：§r装饰材料");
        }

        if (config.getBoolean("features.food")) {
            new FoodItem().loadAll();
            javaPlugin.getServer().getPluginManager().registerEvents(new FoodEvent(), javaPlugin);
            LogUtil.send("&r┝ §a已启用功能：§r食物");
        }else {
            LogUtil.send("&r┝ §c未启用功能：§r食物");
        }

        LogUtil.send("&r┝ §3系统");

        LogUtil.send("&r┝ §a已启用系统：§r合成系统");
        new WeaponRecipe().run();
        new ToolRecipe().run();
        new BlockRecipe().run();
        new MaterialRecipe().run();

        LogUtil.send("&r┝ §a已启用系统：§r耐久性系统");

        if (/*!INewReflex.getInfo().isOldVersion() &&*/
                config.getBoolean("features.block") &&
                BlockStoreLib.blockStoreEnable &&
                ProLib.ProLibEnable) {
            javaPlugin.getServer().getPluginManager().registerEvents(new BreakBlock(), javaPlugin);
            LogUtil.send("&r┝ §a已启用系统：§r块破坏系统");
        }else {
            LogUtil.send("&r┝ §c禁用的系统：§r块破坏系统");
        }

        if (config.getBoolean("crop.enable") && BlockStoreLib.blockStoreEnable) {
            javaPlugin.getServer().getPluginManager().registerEvents(new CropEvent(), javaPlugin);
            // 读取CropData
            CropData.load();
            // 生长定时器
            CropManager.task();
            LogUtil.send("&r┝ §a已启用系统：§r农作物系统");
        }else {
            LogUtil.send("&r┝ §c禁用的系统：§r农作物系统");
        }

        BreakRandomBlock.randomSpawnFindMax = config.getInt("randomSpawnBlock.findMax");
        BreakRandomBlock.randomSpawnDebug = config.getBoolean("randomSpawnBlock.debug");
        BreakRandomBlock.randomSpawnBlockConditions = config.getStringList("randomSpawnBlock.naturalSpawn.conditions");
        BreakRandomBlock.randomSpawnRadiusX = config.getInt("randomSpawnBlock.naturalSpawn.radiusX");
        BreakRandomBlock.randomSpawnRadiusY = config.getInt("randomSpawnBlock.naturalSpawn.radiusY");
        BreakRandomBlock.randomSpawnRadiusZ = config.getInt("randomSpawnBlock.naturalSpawn.radiusZ");
        BreakRandomBlock.randomSpawnEnableWorld = config.getStringList("randomSpawnBlock.naturalSpawn.enableWorld");

        if (/*!INewReflex.getInfo().isOldVersion() &&*/
                config.getBoolean("randomSpawnBlock.naturalSpawn.enable") &&
                BlockStoreLib.blockStoreEnable &&
                ProLib.ProLibEnable) {
            javaPlugin.getServer().getPluginManager().registerEvents(new BreakRandomBlock(), javaPlugin);
            LogUtil.send("&r┝ §a已启用系统：§r自然生成块系统");
        }else {
            LogUtil.send("&r┝ §c禁用的系统：§r自然生成块系统");
        }

        if (CreateMobsBookYaml.mobsBookYaml.getBoolean("mobsBook.enable")
                && ImiPetLib.imiPetEnable
                && MMLib.MMLibEnable) {
            MobsBookManager.load();
            javaPlugin.getServer().getPluginManager().registerEvents(new MobsBookManager(), javaPlugin);
            LogUtil.send("&r┝ §a已启用系统：§r生物图鉴系统");
        }else {
            LogUtil.send("&r┝ §c禁用的系统：§r生物图鉴系统");
        }
    }
}
