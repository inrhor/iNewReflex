package cn.mcres.iNewReflex.utils;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScriptUtil {
    public static void run(List<String> scriptList, Player player) {
        for (String script : scriptList) {
            String command = script.substring(script.indexOf(":") + 1).replaceAll("@player", player.getName());
            if (script.startsWith("exp:")) {
                player.giveExp(Integer.parseInt(command));
            }
            if (script.startsWith("console:")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
            if (script.startsWith("command_player:")) {
                player.performCommand(command);
            }
            if (script.startsWith("command_op:")) {
                if (!player.isOp()) {
                    try {
                        player.setOp(true);
                        player.performCommand(command);
                    } finally {
                        player.setOp(false);
                    }
                } else {
                    player.performCommand(command);
                }
            }
        }
    }
}
