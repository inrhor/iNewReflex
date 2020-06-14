package cn.mcres.iNewReflex.load.checkPlugin;

import cn.mcres.iNewReflex.utils.LogUtil;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;

public class ProLib {
    public static boolean ProLibEnable = false;
    public static ProtocolManager protocolManager;

    public static void load() {
        String plugin = "ProtocolLib";
        if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
            LogUtil.send("&r┝ §a已安装插件：§r"+plugin);
            protocolManager = ProtocolLibrary.getProtocolManager();
            ProLibEnable = true;
        }else {
            LogUtil.send("&r┝ §c未安装插件：§r"+plugin);
        }
    }
}
