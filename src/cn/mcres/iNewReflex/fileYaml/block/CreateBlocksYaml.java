package cn.mcres.iNewReflex.fileYaml.block;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.CreateYaml;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.logging.Level;

public class CreateBlocksYaml {
    public static final FileConfiguration blocksYaml = new YamlConfiguration();
    private static final Plugin plugin = INewReflex.getMain();

    public static void reload() {
        try {
            CreateYaml.RunResponse<Void> run = CreateYaml.copyFile(plugin, "blocks.yml", false);
            blocksYaml.load(run.file);
        } catch (IOException | InvalidConfigurationException error) {
            plugin.getLogger().log(Level.WARNING, "无法加载blocks.yml", error);
        }
    }

    static{
        reload();
    }
}
