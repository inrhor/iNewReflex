package cn.mcres.iNewReflex.load.checkPlugin;

import cn.mcres.iNewReflex.utils.LogUtil;
import org.bukkit.Bukkit;

public class BlockStoreLib {
    public static boolean blockStoreEnable = false;

    public static void load() {
        String plugin = "BlockStore";
        if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            LogUtil.send("&r┝ §a已安装插件：§r"+plugin);
            blockStoreEnable = true;
        }else {
            LogUtil.send("&r┝ §c未安装插件：§r"+plugin);
        }
    }
}
