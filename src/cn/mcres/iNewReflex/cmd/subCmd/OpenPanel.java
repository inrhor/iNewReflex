package cn.mcres.iNewReflex.cmd.subCmd;

import cn.mcres.iCraftNew.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.Panel;

import java.util.Optional;

import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;

public class OpenPanel {
	public static boolean execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 2) {
				Optional<Panel> optionalPanel = IManager.getPanel(args[1]);
				if (!optionalPanel.isPresent()) {
					Msg.send(sender, PANEL_NOT_EXIST);
					return true;
				}
				Panel panel = optionalPanel.get();
				if (args.length == 3) {
					if (!openByOther(sender, args, panel)) {
						return true;
					}
				}
				IManager.openCraftingGUI(player, optionalPanel.get());
			} else {
				Msg.send(sender, OPEN_WRONG_FORMAT);
			}
		} else {
			if (args.length == 3) {
				Optional<Panel> optionalPanel = IManager.getPanel(args[1]);
				if (!optionalPanel.isPresent()) {
					Msg.send(sender, PANEL_NOT_EXIST);
					return true;
				}
				Panel panel = optionalPanel.get();
				openByOther(sender, args, panel);
			} else {
				Msg.send(sender, OPEN_WRONG_FORMAT);
			}
		}
		return true;
	}

	private static boolean openByOther(CommandSender sender, String[] args, Panel panel) {
		StringBuilder sb = new StringBuilder();
		for (int i = 2; i < args.length; i++) {
			sb.append(args[i]);
		}
		Player openPlayer = Bukkit.getPlayer(sb.toString());
		if (openPlayer != null) {
			IManager.openCraftingGUI(openPlayer, panel);
			return true;
		} else {
			Msg.send(sender, PLAYER_NOT_ONLINE);
		}
		return false;
	}
}
