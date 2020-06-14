package cn.mcres.iNewReflex.load;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.fileYaml.YamlUpdate;
import cn.mcres.iNewReflex.fileYaml.crop.CreateCropDataYaml;
import cn.mcres.iNewReflex.fileYaml.gui.CreateGuiYaml;
import cn.mcres.iNewReflex.fileYaml.icraft.CreatePanelYaml;
import cn.mcres.iNewReflex.fileYaml.icraft.CreateRecipeYaml;
import cn.mcres.iNewReflex.fileYaml.item.CreateFoodYaml;
import cn.mcres.iNewReflex.fileYaml.item.CreateMaterialYaml;
import cn.mcres.iNewReflex.fileYaml.item.CreateToolYaml;
import cn.mcres.iNewReflex.fileYaml.item.CreateWeaponYaml;
import cn.mcres.iNewReflex.fileYaml.lang.CreateLangYaml;
import cn.mcres.iNewReflex.fileYaml.mobsBook.CreateMobsBookYaml;
import cn.mcres.iNewReflex.utils.LogUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class YamlLoad {
    private static JavaPlugin main() {
        return INewReflex.getMain();
    }

    void reload() {
        main().saveDefaultConfig();
        File configFile = new File(main().getDataFolder(), "config.yml");
        try {
            YamlUpdate.update(main(), "config.yml", configFile, Collections.emptyList());
        } catch (IOException e) {
            LogUtil.send("update config error");
        }
        File langFile = new File(main().getDataFolder(), "lang.yml");
        try {
            YamlUpdate.update(main(), "lang.yml", langFile, Collections.emptyList());
        } catch (IOException e) {
            LogUtil.send("update lang error");
        }
        File weaponFile = new File(main().getDataFolder(), "weapon.yml");
        try {
            YamlUpdate.update(main(), "weapon.yml", weaponFile, Collections.emptyList());
        } catch (IOException e) {
            LogUtil.send("update weapon error");
        }
        File toolFile = new File(main().getDataFolder(), "tool.yml");
        try {
            YamlUpdate.update(main(), "tool.yml", toolFile, Collections.emptyList());
        } catch (IOException e) {
            LogUtil.send("update tool error");
        }
        File materialFile = new File(main().getDataFolder(), "material.yml");
        try {
            YamlUpdate.update(main(), "material.yml", materialFile, Collections.emptyList());
        } catch (IOException e) {
            LogUtil.send("update material error");
        }
        File foodFile = new File(main().getDataFolder(), "food.yml");
        try {
            YamlUpdate.update(main(), "food.yml", foodFile, Collections.emptyList());
        } catch (IOException e) {
            LogUtil.send("update material error");
        }
        File guiFile = new File(main().getDataFolder(), "gui.yml");
        try {
            YamlUpdate.update(main(), "gui.yml", guiFile, Collections.emptyList());
        } catch (IOException e) {
            LogUtil.send("update gui error");
        }
        main().reloadConfig();
        CreateLangYaml.reload();
        CreateWeaponYaml.reload();
        CreateToolYaml.reload();
        CreateMaterialYaml.reload();
        CreateFoodYaml.reload();
        CreateCropDataYaml.reload();
        CreateRecipeYaml.reload();
        CreatePanelYaml.reload();
        CreateGuiYaml.reload();
        CreateMobsBookYaml.reload();
        IManager.hook(main());
        IManager.titleSelGui.put("selTitle", main().getConfig().getString("iCraft.selectGui.title").replaceAll("&", "§"));
    }
}
