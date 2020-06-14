package cn.mcres.iCraftNew.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;

public class LogUtil {
    // 日志
    public static void send(String println) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', println));
    }

    public static void send(List<String> printlnList) {
        for (String println : printlnList) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', println));
        }
    }
}
