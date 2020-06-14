package cn.mcres.iNewReflex.cmd.subCmd;

import cn.mcres.iCraftNew.util.Msg;
import org.bukkit.command.CommandSender;
import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.loader.Yaml;
import cn.mcres.iCraftNew.model.Panel;
import cn.mcres.iCraftNew.model.Recipe;

import java.util.Optional;

import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;

public class DelRecipe {
	public static boolean execute(CommandSender sender, String[] args) {
		if (args.length >= 3) {
			Optional<Panel> optionalPanel = IManager.getPanel(args[1]);
			if (optionalPanel.isPresent()) {
				Optional<Recipe> optionalRecipe = IManager.getRecipe(args[1], args[2]);
				if (optionalRecipe.isPresent()) {
					Recipe recipe = optionalRecipe.get();
					IManager.unregisterRecipe(args[1], args[2]);
					Yaml.delRecipe(recipe);
					Msg.send(sender, RECIPE_SUCCESSFULLY_DEL);
				} else {
					Msg.send(sender, RECIPE_NOT_EXIST);
				}
			} else {
				Msg.send(sender, PANEL_NOT_EXIST);
			}
		} else {
			Msg.send(sender, DEL_WRONG_FORMAT);
		}
		return true;
	}
}
