package cn.mcres.iNewReflex.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MsgUtil {
    // 发送给玩家
    public static void send(CommandSender player, List<String> msgList) {
        for (String msg : msgList) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    public static void send(CommandSender player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
