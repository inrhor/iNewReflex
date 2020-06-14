package cn.mcres.iNewReflex.cmd.subCmd;

import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;
import cn.mcres.iNewReflex.gui.guide.GuideHome;
import cn.mcres.iNewReflex.utils.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenGuide {
    public static boolean execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            MsgUtil.send(sender, COMMAND_CONSOLE);
            return true;
        }

        Player player = (Player) sender;

        if (sender.hasPermission("inewrx.openguide")) {
            GuideHome.open(player);
        } else {
            MsgUtil.send(sender, COMMAND_NO_PERMISSION);
        }
        return true;
    }
}
