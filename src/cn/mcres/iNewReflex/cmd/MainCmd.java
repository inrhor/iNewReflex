package cn.mcres.iNewReflex.cmd;

import cn.mcres.iNewReflex.cmd.subCmd.*;
import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.COMMAND_HELP;
import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.COMMAND_NO_PERMISSION;
import cn.mcres.iNewReflex.utils.MsgUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("inewrx")) {
            if (args.length == 0) {
                if (sender.hasPermission("inewrx.help")) {
                    MsgUtil.send(sender, COMMAND_HELP);
                } else {
                    MsgUtil.send(sender, COMMAND_NO_PERMISSION);
                }
                return true;
            }
            String subCmd = SafeThis.get(args, 0, "");
            switch (subCmd) {
                case "reload":
                    if (Reload.execute(sender)) {
                        return true;
                    }
                    break;
                case "help":
                    if (Help.execute(sender)) {
                        return true;
                    }
                    break;
                case "give":
                    if (Give.execute(sender, args)) {
                        return true;
                    }
                    break;
                case "blockspawn":
                    if (BlockSpawn.execute(sender/*, command, label*/, args)) {
                        return true;
                    }
                    break;
                case "openpanel":
                    if (sender.hasPermission("inewrx.use")) {
                        if (OpenPanel.execute(sender, args)) {
                            return true;
                        }
                    }else {
                        MsgUtil.send(sender, COMMAND_NO_PERMISSION);
                    }
                    break;
                case "addrecipe":
                    if (sender.hasPermission("inewrx.admin")) {
                        if (AddRecipe.execute(sender, args)) {
                            return true;
                        }
                    }else {
                        MsgUtil.send(sender, COMMAND_NO_PERMISSION);
                    }
                    break;
                case "delrecipe":
                    if (sender.hasPermission("inewrx.admin")) {
                        if (DelRecipe.execute(sender, args)) {
                            return true;
                        }
                    }else {
                        MsgUtil.send(sender, COMMAND_NO_PERMISSION);
                    }
                    break;
                case "editrecipe":
                    if (sender.hasPermission("inewrx.admin")) {
                        if (EditRecipe.execute(sender, command, label, args)) {
                            return true;
                        }
                    }else {
                        MsgUtil.send(sender, COMMAND_NO_PERMISSION);
                    }
                    break;
                case "viewrecipe":
                    if (sender.hasPermission("inewrx.use")) {
                        if (ViewRecipe.execute(sender, command, label, args)) {
                            return true;
                        }
                    }else {
                        MsgUtil.send(sender, COMMAND_NO_PERMISSION);
                    }
                    break;
                case "openguide":
                    if (OpenGuide.execute(sender)) {
                        return true;
                    }
                    break;
                /*case "test":
                    if (Test.execute(sender, command, label, args)) {
                        return true;
                    }
                    break;*/
            }
        }
        return true;
    }
}