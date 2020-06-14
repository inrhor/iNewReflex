package cn.mcres.iNewReflex.fileYaml.icraft;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.CreateYaml;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CreateRecipeYaml {
    private static final FileConfiguration recipeYaml = new YamlConfiguration();
    private static final Plugin plugin = INewReflex.getMain();

    public static void reload() {
        String recipeYml = "recipe";
        try {
            CreateYaml.RunResponse<Void> run = CreateYaml.copyFile(plugin, recipeYml+".yml", false);
            recipeYaml.load(run.file);
        } catch (IOException | InvalidConfigurationException error) {
            plugin.getLogger().log(Level.WARNING, "无法加载-Can not load: "+recipeYml+".yml", error);
        }
    }

    static{
        reload();
    }
}
