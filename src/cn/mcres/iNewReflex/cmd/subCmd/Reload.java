package cn.mcres.iNewReflex.cmd.subCmd;

import cn.mcres.iNewReflex.load.Load;
import cn.mcres.iNewReflex.utils.LogUtil;
import cn.mcres.iNewReflex.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;

public class Reload {
    public static boolean execute(CommandSender sender) {
        if (sender == Bukkit.getConsoleSender()) {
            Load.reload();
            LogUtil.send(COMMAND_RELOAD);
        } else {
            if (sender.hasPermission("inewrx.admin")) {
                Load.reload();
                MsgUtil.send(sender, COMMAND_RELOAD);
            } else {
                MsgUtil.send(sender, COMMAND_NO_PERMISSION);
            }
        }
        return true;
    }
}
