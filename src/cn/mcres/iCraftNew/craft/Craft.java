package cn.mcres.iCraftNew.craft;

import cn.mcres.iCraftNew.loader.Yaml;
import cn.mcres.iNewReflex.cmd.subCmd.EditRecipe;
import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import cn.mcres.iCraftNew.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.api.Events.PanelCraftEvent;
import cn.mcres.iCraftNew.model.Panel;
import cn.mcres.iCraftNew.model.Recipe;
import cn.mcres.iNewReflex.INewReflex;

public class Craft {
	public static void craft(Player player, Panel panel, Inventory inventory) {
		//非添加状态
		if (!EditRecipe.EDITING.containsKey(player.getName())) {
			LinkedList<ItemStack> items = new LinkedList<>();
			for (int i : panel.getMatrixSlots()) {
				items.addLast(inventory.getItem(i));
			}
			Recipe pair = null;
			Collection<Recipe> recipes = IManager.getRecipes(panel.getId());
			for (Recipe recipe : recipes) {
				if (canCraft(recipe.getItemMatrix(), items)) {
					pair = recipe;
				}
			}
			LinkedList<ItemStack> RSI = new LinkedList<>();
			for (int slot : panel.getResultsSlots()) {
				if (inventory.getItem(slot) != null) {
					RSI.addLast(inventory.getItem(slot));
				}
			}
			if (pair != null) {
				if (canOverlap(pair.getResults(), RSI)) {
					PanelCraftEvent panelCraftEvent = new PanelCraftEvent(panel, player, pair, items);
					Bukkit.getPluginManager().callEvent(panelCraftEvent);
					if (!panelCraftEvent.isCancelled()) {
						int j = 0;
						for (int i : panel.getMatrixSlots()) {
							if (inventory.getItem(i) != null) {
								ItemStack item = inventory.getItem(i);
								if (item != null) {
									item.setAmount(item.getAmount() - pair.getItemMatrix().get(j).getAmount());
								}
							}
							j++;
						}
						List<Integer> resultSlots = panel.getResultsSlots();
						for (int i = 0; i < resultSlots.size(); i++) {
							if (RSI.isEmpty()) {
								inventory.setItem(resultSlots.get(i), pair.getResults().get(i));
							} else {
								ItemStack item = inventory.getItem(resultSlots.get(i));
								if (item != null) {
									item.setAmount(item.getAmount() + pair.getResults().get(i).getAmount());
								}
							}
						}
					}
				}
			}
		} else {
			add(player, panel, inventory);
		}
	}
	private static void add(Player player, Panel panel, Inventory inventory) {
		List<Integer> ms = panel.getMatrixSlots();
		List<Integer> rs = panel.getResultsSlots();
		LinkedList<ItemStack> matrix = new LinkedList<>();
		LinkedList<ItemStack> results = new LinkedList<>();
		ItemStack none = new ItemStack(Material.AIR);
		for (Integer s : ms) {
			ItemStack item = inventory.getItem(s);
			matrix.addLast(item != null ? item : none);
		}
		for (Integer s : rs) {
			ItemStack item = inventory.getItem(s);
			results.addLast(item != null ? item : none);
		}
		Recipe recipe = new Recipe(EditRecipe.EDITING.get(player.getName()), panel.getId(), matrix, results);
		IManager.registerRecipe(recipe);
		Yaml.saveRecipe(recipe);
		Msg.send(player, RECIPE_SUCCESSFULLY_EDIT);
		Bukkit.getScheduler().scheduleSyncDelayedTask(INewReflex.getMain(), player::closeInventory, 1L);
	}
	/**
	 * 是否可以合成
	 * @param recipeList 配方堆
	 * @param craftList 合成堆
	 * @return 是否可以合成
	 */
	private static boolean canCraft(List<ItemStack> recipeList, List<ItemStack> craftList) {
		if (recipeList.size() == craftList.size()) {
			for (int i = 0; i < recipeList.size(); i++) {
				ItemStack recipeStack = recipeList.get(i);
				if (recipeStack == null || recipeStack.getType() == Material.AIR) {
					recipeStack = null;
				}
				ItemStack craftStack = craftList.get(i);
				if (craftStack == null || craftStack.getType() == Material.AIR) {
					craftStack = null;
				}
				if (recipeStack == null && craftStack == null) {
					continue;
				}
				if (recipeStack == null ||  craftStack == null ||!recipeStack.isSimilar(craftStack)/*!equalsItem(recipeStack, craftStack)*/) {
					return false;
				}
				if (recipeStack.getAmount() > craftStack.getAmount()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	/**
	 * 是否允许堆叠
	 * @param recipeResultList 配方结果
	 * @param panelResultList 已合成结果
	 * @return 是否允许对叠
	 */
	private static boolean canOverlap(List<ItemStack> recipeResultList, List<ItemStack> panelResultList) {
		if (panelResultList.isEmpty()) {
			return true;
		}
		for (int i = 0; i < recipeResultList.size(); i++) {
			ItemStack recipeResultStack = recipeResultList.get(i);
			if (recipeResultStack == null) {
				continue;
			}
			ItemStack panelResultStack = panelResultList.get(i);
			if (panelResultStack == null) {
				continue;
			}
			if (!recipeResultStack.isSimilar(panelResultStack)) {
				return false;
			}
			if (recipeResultStack.getAmount() + panelResultStack.getAmount() > panelResultStack.getMaxStackSize()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 只对材料，名称，Lore判断，不对NBT具体内容的死判断
	 */
	/*private static boolean equalsItem(ItemStack recipeStack, ItemStack craftStack) {
		if (recipeStack.getType().equals(craftStack.getType())) {
			if (recipeStack.hasItemMeta() && craftStack.hasItemMeta()) {
				ItemMeta recipeStackMeta = recipeStack.getItemMeta();
				ItemMeta craftStackMate = craftStack.getItemMeta();
				if (!recipeStackMeta.getDisplayName().equals(craftStackMate.getDisplayName())) {
					return false;
				}
			}else {
				return true;
			}
		}
		return false;
	}*/
}
