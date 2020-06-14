package cn.mcres.iCraftNew.load;

import cn.mcres.iNewReflex.INewReflex;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class Resource {
    private static JavaPlugin main() {
        return INewReflex.getMain();
    }
    
    private static YamlConfiguration recipe;
    private static YamlConfiguration panel;

    /*public static void saveResource(String resourcePath, String savePath, boolean replace) {
        if ((resourcePath == null) || (resourcePath.equals(""))) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
        JavaPlugin main = main();
        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = main.getResource(resourcePath);
        if (in != null) {
            File outFile = new File(main.getDataFolder() + savePath, resourcePath);
            int lastIndex = resourcePath.lastIndexOf('/');
            File outDir = new File(main.getDataFolder() + savePath, resourcePath.substring(0, Math.max(lastIndex, 0)));
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            try {
                if ((!outFile.exists()) || (replace)) {
                    OutputStream out = new FileOutputStream(outFile);
                    byte[] buf = new byte['?'];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.close();
                    in.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }*/

    public static YamlConfiguration getRecipe() {
        if (recipe == null) {
            reloadRecipe();
        }
        return recipe;
    }

    public static void reloadRecipe() {
        String recipeYml = "recipe";
        if (INewReflex.getInfo().isOldVersion()) {
            recipeYml = "recipeold";
        }
        if (INewReflex.getInfo().isOceanVersion()) {
            recipeYml = "recipeocean";
        }
        recipe = YamlConfiguration.loadConfiguration(new File(main().getDataFolder().getPath() + File.separator + recipeYml+".yml"));
    }

    public static void saveRecipes() {
        String recipeYml = "recipe";
        if (INewReflex.getInfo().isOldVersion()) {
            recipeYml = "recipeold";
        }
        if (INewReflex.getInfo().isOceanVersion()) {
            recipeYml = "recipeocean";
        }
        try {
            recipe.save(new File(main().getDataFolder().getPath() + File.separator + recipeYml+".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void reloadPanel() {
        panel = YamlConfiguration.loadConfiguration(new File(main().getDataFolder().getPath() + File.separator + "panel.yml"));
    }
    public static YamlConfiguration getPanel() {
        if (panel == null) {
            reloadPanel();
        }
        return panel;
    }
    public static void savePanels() {
        try {
            panel.save(new File(main().getDataFolder().getPath() + File.separator + "panel.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
