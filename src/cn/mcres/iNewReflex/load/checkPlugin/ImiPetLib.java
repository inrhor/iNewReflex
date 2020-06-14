package cn.mcres.iNewReflex.load.checkPlugin;

import cn.mcres.iNewReflex.utils.LogUtil;
import org.bukkit.Bukkit;

public class ImiPetLib {
    public static boolean imiPetEnable = false;

    public static void load() {
        String plugin = "imiPet";
        if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            LogUtil.send("&r┝ §a已安装插件：§r"+plugin);
            imiPetEnable = true;
        }else {
            LogUtil.send("&r┝ §c未安装插件：§r"+plugin);
        }
    }
}
