package cn.mcres.iNewReflex;

import cn.mcres.iCraftNew.unload.UnLoad;
import cn.mcres.iNewReflex.crop.CropData;
import cn.mcres.iNewReflex.server.Info;
import cn.mcres.iNewReflex.load.Load;
import cn.mcres.iNewReflex.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class INewReflex extends JavaPlugin {
    private static INewReflex main;
    private static Info info;

    // 减少YAML读取量
    public static String durabilityTag;

    @Override
    public void onEnable() {
        main = this;

        info = new Info(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3], getServer().getName());

        durabilityTag = getConfig().getString("durability.tag").replaceAll("&", "§");

        Load.run();

        new Metrics(this, 6435);
    }

    @Override
    public void onDisable() {
        // 存储CropData
        CropData.saveData();
        UnLoad.run();
        main = null;
    }

    public static INewReflex getMain() {
        return main;
    }

    public static Info getInfo() {
        return info;
    }

}
