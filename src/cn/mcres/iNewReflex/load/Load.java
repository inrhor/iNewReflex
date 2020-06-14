package cn.mcres.iNewReflex.load;

import cn.mcres.iNewReflex.cmd.MainCmd;
import cn.mcres.iNewReflex.load.checkPlugin.*;
import cn.mcres.iNewReflex.utils.LogUtil;
import org.bukkit.Bukkit;

public class Load {
    public static void run() {
        // 开始检查
        Check.start();

        // 检查依赖
        LogUtil.send("&r┝ §3软依赖");
        ImiPetLib.load();
        BlockStoreLib.load();
        ProLib.load();
        ResLib.load();
        MMLib.load();
        HDLib.load();
        TrHDLib.load();

        // 加载文件
        new YamlLoad().reload();

        // 检查功能
        CheckFeatures.load();

        // 注册监听器
        new RegisterEvents().run();

        // 检查结束
        Check.finish();

        // 注册命令
        Bukkit.getPluginCommand("inewrx").setExecutor(new MainCmd());
    }

    public static void reload() {
        // 加载文件
        new YamlLoad().reload();
    }
}
