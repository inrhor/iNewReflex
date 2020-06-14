package cn.mcres.iNewReflex.fileYaml.gui;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.CreateYaml;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CreateGuiYaml {
    public static final FileConfiguration guiYaml = new YamlConfiguration();
    private static final Plugin plugin = INewReflex.getMain();

    public static void reload() {
        try {
            CreateYaml.RunResponse<Void> run = CreateYaml.copyFile(plugin, "gui.yml", false);
            guiYaml.load(run.file);
        } catch (IOException | InvalidConfigurationException error) {
            plugin.getLogger().log(Level.WARNING, "无法加载-Can not load: gui.yml", error);
        }
        // 实现获取YAML内容
        GetGuiYaml.get();
    }

    static{
        reload();
    }
}
