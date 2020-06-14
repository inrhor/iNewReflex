package cn.mcres.iNewReflex.fileYaml.mobsBook;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.CreateYaml;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CreateMobsBookYaml {
    public static final FileConfiguration mobsBookYaml = new YamlConfiguration();
    private static final Plugin plugin = INewReflex.getMain();

    public static void reload() {
        try {
            CreateYaml.RunResponse<Void> run = CreateYaml.copyFile(plugin, "mobsBook.yml", false);
            mobsBookYaml.load(run.file);
        } catch (IOException | InvalidConfigurationException error) {
            plugin.getLogger().log(Level.WARNING, "无法加载mobsBook.yml", error);
        }
    }

    static{
        reload();
    }
}
