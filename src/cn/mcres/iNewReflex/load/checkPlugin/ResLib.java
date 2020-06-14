package cn.mcres.iNewReflex.load.checkPlugin;

import cn.mcres.iNewReflex.utils.LogUtil;
import org.bukkit.Bukkit;

public class ResLib {
    public static boolean resLibEnable = false;

    public static void load() {
        String plugin = "Residence";
        if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            LogUtil.send("&r┝ §a已安装插件：§r"+plugin);
            resLibEnable = true;
        }else {
            LogUtil.send("&r┝ §c未安装插件：§r"+plugin);
        }
    }
}
