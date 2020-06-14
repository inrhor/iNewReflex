package cn.mcres.iNewReflex.load.checkPlugin;

import cn.mcres.iNewReflex.utils.LogUtil;
import org.bukkit.Bukkit;

public class MMLib {
    public static boolean MMLibEnable = false;

    public static void load() {
        String plugin = "MythicMobs";
        if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            LogUtil.send("&r┝ §a已安装插件：§r"+plugin);
            MMLibEnable = true;
        }else {
            LogUtil.send("&r┝ §c未安装插件：§r"+plugin);
        }
    }
}
