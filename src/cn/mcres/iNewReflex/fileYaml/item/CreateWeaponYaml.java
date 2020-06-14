package cn.mcres.iNewReflex.fileYaml.item;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml;
import cn.mcres.iNewReflex.utils.CreateYaml;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CreateWeaponYaml {
    public static final FileConfiguration weaponYaml = new YamlConfiguration();
    private static final Plugin plugin = INewReflex.getMain();

    public static void reload() {
        try {
            CreateYaml.RunResponse<Void> run = CreateYaml.copyFile(plugin, "weapon.yml", false);
            weaponYaml.load(run.file);
        } catch (IOException | InvalidConfigurationException error) {
            plugin.getLogger().log(Level.WARNING, "无法加载-Can not load: weapon.yml", error);
        }
    }

    static{
        reload();
    }
}
