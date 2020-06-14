package cn.mcres.iNewReflex.cmd.subCmd;

import java.util.HashMap;
import java.util.Optional;

import cn.mcres.iCraftNew.util.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.Panel;
import cn.mcres.iCraftNew.model.Recipe;

import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;

public class EditRecipe {
	public static HashMap<String, String> EDITING = new HashMap<>();
	public static boolean execute(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length >= 3) {
				Optional<Panel> optionalPanel = IManager.getPanel(args[1]);
				if (optionalPanel.isPresent()) {
					Panel panel = optionalPanel.get();
					Optional<Recipe> optionalRecipe = IManager.getRecipe(args[1], args[2]);
					if (optionalRecipe.isPresent()) {
						Recipe recipe = optionalRecipe.get();
						IManager.openEditRecipeGUI(player, panel, recipe);
						EditRecipe.EDITING.put(player.getName(), args[2]);
					} else {
						Msg.send(player, PANEL_NOT_EXIST);
					}
				} else {
					Msg.send(player, PANEL_NOT_EXIST);
				}
			} else {
				Msg.send(player, EDIT_WRONG_FORMAT);
			}
		}
		return true;
	}
}
