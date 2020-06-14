package cn.mcres.iCraftNew.loader;

import java.util.List;

import cn.mcres.iCraftNew.load.Resource;
import org.bukkit.configuration.file.YamlConfiguration;
import cc.zoyn.core.util.serializer.ItemSerializerUtils;
import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.Panel;
import cn.mcres.iCraftNew.model.Recipe;

public class Yaml {
	public static void loadRecipes() {
		YamlConfiguration yaml = Resource.getRecipe();
		loadRecipes(yaml);
	}

	public static void loadRecipes(YamlConfiguration yaml) {
		for (String key : yaml.getKeys(false)) {
			String panelId = yaml.getString(key + ".panelId");
			String itemMatrix = yaml.getString(key + ".itemMatrix");
			String results = yaml.getString(key + ".results");
			if (panelId != null && itemMatrix != null && results != null) {
				Recipe recipe = new Recipe(key, panelId, ItemSerializerUtils.fromBase64(itemMatrix), ItemSerializerUtils.fromBase64(results));
				IManager.registerRecipe(recipe);
			}
		}
	}

	public static void saveRecipe(Recipe recipe) {
		YamlConfiguration yaml = Resource.getRecipe();
		yaml.set(recipe.getId() + ".panelId", recipe.getPanelId());
		yaml.set(recipe.getId() + ".itemMatrix", ItemSerializerUtils.toBase64(recipe.getItemMatrix()).replaceAll("[\r\n]", ""));
		yaml.set(recipe.getId() + ".results", ItemSerializerUtils.toBase64(recipe.getResults()).replaceAll("[\r\n]", ""));
		Resource.saveRecipes();
	}

	public static void delRecipe(Recipe recipe) {
		YamlConfiguration yaml = Resource.getRecipe();
		yaml.set(recipe.getId(), null);
		Resource.saveRecipes();
	}

	public static void loadPanels() {
		YamlConfiguration yaml = Resource.getPanel();
		loadPanels(yaml);
	}

	public static void loadPanels(YamlConfiguration yaml) {
		for (String key : yaml.getKeys(false)) {
			List<Integer> matrix = yaml.getIntegerList(key + ".matrix");
			List<Integer> buttonSlots = yaml.getIntegerList(key + ".buttonSlots");
			List<Integer> closeSlots = yaml.getIntegerList(key + ".closeSlots");
			List<Integer> resultSlots = yaml.getIntegerList(key + ".resultSlots");
			List<Integer> backSlots = yaml.getIntegerList(key + ".backSlots");
			Panel panel = new Panel(key, buttonSlots, closeSlots, matrix, resultSlots, backSlots);
			IManager.registerPanel(panel);
		}
	}
}
