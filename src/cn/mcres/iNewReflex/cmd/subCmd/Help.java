package cn.mcres.iNewReflex.cmd.subCmd;

import cn.mcres.iNewReflex.utils.MsgUtil;
import org.bukkit.command.CommandSender;

import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;

public class Help {
    public static boolean execute(CommandSender sender) {
        if (sender.hasPermission("inewrx.help")) {
            MsgUtil.send(sender, COMMAND_HELP);
        } else {
            MsgUtil.send(sender, COMMAND_NO_PERMISSION);
        }
        return true;
    }
}
