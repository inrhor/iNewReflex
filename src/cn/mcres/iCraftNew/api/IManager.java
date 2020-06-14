package cn.mcres.iCraftNew.api;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import cn.mcres.iCraftNew.util.ItemStackUtil;
import cn.mcres.iCraftNew.util.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import cn.mcres.iCraftNew.api.Events.PanelOpenEvent;
import cn.mcres.iCraftNew.loader.Yaml;
import cn.mcres.iCraftNew.model.ICraftTechHolder;
import cn.mcres.iCraftNew.model.Panel;
import cn.mcres.iCraftNew.model.Recipe;
import cn.mcres.iNewReflex.INewReflex;

public class IManager {
	private static final HashMap<String, HashMap<String, Recipe>> RECIPES = new HashMap<>();
	private static final HashMap<String, Panel> PANELS = new HashMap<>();

	/**
	 * 注册附属
	 * @param plugin 目标插件实例
	 * @param recipe 配方配置文件
	 * @param panel 面板配置文件
	 */
	public static void hook(JavaPlugin plugin, File recipe, File panel)
	{
		if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
			if (recipe.exists()) {
				Yaml.loadRecipes(YamlConfiguration.loadConfiguration(recipe));
			}
			if (panel.exists()) {
				Yaml.loadPanels(YamlConfiguration.loadConfiguration(panel));
			}
		} else {
			LogUtil.send("§f[iNewReflex] §4附属插件" + plugin.getName() + "未启用, Hook失败");
		}
	}
	/**
	 * 注册附属
	 * @param plugin 目标插件实例
	 */
	public static void hook(JavaPlugin plugin) {
		String path = plugin.getDataFolder().getPath();
		String recipeYml = "recipe";
		if (INewReflex.getInfo().isOldVersion()) {
			recipeYml = "recipeold";
		}
		if (INewReflex.getInfo().isOceanVersion()) {
			recipeYml = "recipeocean";
		}
		File recipe = new File(path + File.separator + recipeYml+".yml");
		File panel = new File(path + File.separator + "panel.yml");
		hook(plugin, recipe, panel);
	}
	/**
	 * 注册配方
	 * @param recipe 配方
	 */
	public static void registerRecipe(Recipe recipe) {
		RECIPES.computeIfAbsent(recipe.getPanelId(), k -> new HashMap<>());
		RECIPES.get(recipe.getPanelId()).put(recipe.getId(), recipe);
	}

	/**
	 * 注销配方
	 * @param panelId 合成面板id
	 * @param RecipeId 配方id
	 */
	public static void unregisterRecipe(String panelId, String RecipeId) {
		RECIPES.get(panelId).remove(RecipeId);
	}

	/**
	 * 获得配方
	 * @param panelId  合成面板id
	 * @param recipeId 配方id
	 * @return 配方
	 */
	public static Optional<Recipe> getRecipe(String panelId, String recipeId)
	{
		return Optional.ofNullable(RECIPES.get(panelId)).map(recipes -> recipes.get(recipeId));
	}

	/**
	 * 获得所有配方
	 * @return 所有配方
	 */
	public static Collection<Recipe> getRecipes() {
		return RECIPES.values().stream().map(HashMap::values).flatMap(Collection::stream).collect(Collectors.toList());
	}
	/**
	 * 获得指定id合成面板的所有配方
	 * @return 所有配方
	 */
	public static Collection<Recipe> getRecipes(String panelID) {
		return Optional.ofNullable(RECIPES.get(panelID)).map(HashMap::values).orElse(Collections.emptyList());
	}

	/**
	 * 注册合成面板
	 * @param panel 合成面板
	 */
	public static void registerPanel(Panel panel) {
		PANELS.put(panel.getId(), panel);
	}

	/**
	 * 注册合成面板
	 * @param panel 合成面板
	 */
	public static void unregisterPanel(Panel panel) {
		PANELS.remove(panel.getId());
	}

	/**
	 * 通过合成面板id获得面板
	 * @param panelId 合成面板id
	 * @return 合成面板
	 */
	public static Optional<Panel> getPanel(String panelId) {
		return Optional.ofNullable(PANELS.get(panelId));
	}

	/**
	 * 获得所有合成面板
	 * @return 所有合成面板
	 */
	public static Collection<Panel> getPanels() {
		return PANELS.values();
	}

	/**
	 * 打开合成面板
	 * @param player 玩家
	 * @param panel 合成面板
	 */
	public static void openCraftingGUI(Player player, Panel panel) {
		openGUI(player, panel, null, false);
	}

	/**
	 * 打开配方编辑面板
	 * @param player 玩家
	 * @param panel 合成面板
	 * @param recipe 配方
	 */
	public static void openEditRecipeGUI(Player player, Panel panel, Recipe recipe) {
		openGUI(player, panel, recipe, false);
	}
	/**
	 * 打开合成面板
	 * @param player 玩家
	 * @param panel 合成面板
	 * @param recipe 配方
	 * @param isView 是否为演示
	 */
	public static void openGUI(Player player, Panel panel, Recipe recipe, boolean isView) {
		boolean isCraft = recipe == null;
		List<ItemStack> results = null;
		List<ItemStack> matrix = null;
		if (!isCraft) {
			results = recipe.getResults();
			matrix = recipe.getItemMatrix();
		}
		PanelOpenEvent panelOpenEvent = new PanelOpenEvent(panel, player);
		Bukkit.getPluginManager().callEvent(panelOpenEvent);
		if (!panelOpenEvent.isCancelled()) {
			String titleGui = INewReflex.getMain().getConfig().getString("iCraft.inventory.title").replaceAll("&", "§");
			Inventory GUI = Bukkit.createInventory(new ICraftTechHolder(panel.getId()), 54, titleGui);
			int j = 0;
			int k = 0;
			for (int i = 0; i < GUI.getSize(); i++) {
				if (panel.getMatrixSlots().contains(i)) {
					if (!isCraft) {
						if (matrix.size() >= j + 1) {
							GUI.setItem(i, matrix.get(j));
							j++;
						}	
					}
				} else if (panel.getButtonSlots().contains(i) && !isView) {
					GUI.setItem(i, ItemStackUtil.getButton());
				} else if (panel.getBackSlots().contains(i) && isView) {
					GUI.setItem(i, ItemStackUtil.getBack());
				} else if (panel.getCloseSlot().contains(i) && !isView) {
					GUI.setItem(i, ItemStackUtil.getClose());
				} else if (panel.getResultsSlots().contains(i)) {
					if (!isCraft) {
						if (results.size() >= k + 1) {
							GUI.setItem(i, results.get(k));
							k++;
						}
					}
				} else {
					GUI.setItem(i, ItemStackUtil.getBaffle());
				}
			}
			player.openInventory(GUI);
		}
	}

	public static HashMap<String, String> titleSelGui = new LinkedHashMap<>();

	public static void openSelGui(Player player) {
		String vanillaGui = INewReflex.getMain().getConfig().
				getString("iCraft.selectGui.vanillaGuiButton").replaceAll("&", "§");
		String newGui = INewReflex.getMain().getConfig().
				getString("iCraft.selectGui.newGuiButton").replaceAll("&", "§");
		Inventory gui = Bukkit.createInventory(player, 9, titleSelGui.get("selTitle"));
		gui.setItem(3, itemBuild(Material.CHEST, vanillaGui));
		gui.setItem(5, itemBuild(Material.ENDER_CHEST, newGui));
		player.openInventory(gui);
	}

	private static ItemStack itemBuild(Material material, String name) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}
