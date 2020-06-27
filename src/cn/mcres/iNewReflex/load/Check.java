package cn.mcres.iNewReflex.load;

import cn.mcres.iNewReflex.utils.LogUtil;

class Check {
    static void start() {
        LogUtil.send("&r┍ §a正在加载 §fiNewReflex §a插件");
        LogUtil.send("&r┝ §r欢迎使用");
        LogUtil.send("&r┝ §r插件当前版本：§r1.6.3");
        LogUtil.send("&r┝ §e正在检查");
    }

    static void finish() {
        LogUtil.send("&r┝ §e检查完毕");
        LogUtil.send("&r┕ §a插件正常运行");
    }
}
