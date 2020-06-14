package cn.mcres.iNewReflex.cmd.subCmd;

import cn.mcres.iCraftNew.util.Msg;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.Panel;

import java.util.Optional;

import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;

public class AddRecipe {
	public static boolean execute(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 3) {
				Optional<Panel> optionalPanel = IManager.getPanel(args[1]);
				if (optionalPanel.isPresent()) {
					Panel panel = optionalPanel.get();
					if (!IManager.getRecipe(args[1], args[2]).isPresent()) {
						IManager.openCraftingGUI(player, panel);
						EditRecipe.EDITING.put(player.getName(), args[2]);
					} else {
						Msg.send(player, RECIPE_EXISTED);
					}
				} else {
					Msg.send(player, PANEL_NOT_EXIST);
				}
			} else {
				Msg.send(player, ADD_WRONG_FORMAT);
			}
		}
		return true;
	}
}
