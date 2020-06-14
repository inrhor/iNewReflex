package cn.mcres.iCraftNew.util;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackUtil {
	private static ItemStack baffle;
	private static ItemStack button;
	private static ItemStack close;
	private static ItemStack back;
	/**
	 * @return 挡板物品
	 */
	public static ItemStack getBaffle() {
		if (baffle == null) {
			/*if (INewReflex.getInfo().isOldMaterialVersion()) {
				*//*ItemStack item = new ItemStack(ReturnMaterial.blackStatinedGlassPane(), 1,  (short)15);*//*
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§r§l");
				item.setItemMeta(meta);
				baffle = item;
				return baffle;
			}*/
			ItemStack item = new ItemStack(/*Material.BLACK_STAINED_GLASS_PANE*/ReturnMaterial.getMaterial("BLACK_STAINED_GLASS_PANE"));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§r§l");
			item.setItemMeta(meta);
			baffle = item;
		}
		return baffle;
	}
	/**
	 * @return 合成按钮物品
	 */
	public static ItemStack getButton() {
		if (button == null) {
			ItemStack item = new ItemStack(Material.SLIME_BALL);
			ItemMeta meta = item.getItemMeta();
			String itemName = INewReflex.getMain().getConfig().getString("iCraft.inventory.button").replaceAll("&", "§");
			meta.setDisplayName(itemName);
			item.setItemMeta(meta);
			button = item;
		}
		return button;
	}
	/**
	 * @return 关闭按钮物品
	 */
	public static ItemStack getClose() {
		if (close == null) {
			ItemStack item = new ItemStack(Material.BARRIER);
			ItemMeta meta = item.getItemMeta();
			String itemName = INewReflex.getMain().getConfig().getString("iCraft.inventory.close").replaceAll("&", "§");
			meta.setDisplayName(itemName);
			item.setItemMeta(meta);
			close = item;
		}
		return close;
	}

	/**
	 * @return 返回键物品
	 */
	public static ItemStack getBack() {
		if (back == null) {
			ItemStack item = new ItemStack(Material.BOOK);
			ItemMeta meta = item.getItemMeta();
			String itemName = INewReflex.getMain().getConfig().getString("iCraft.inventory.back").replaceAll("&", "§");
			meta.setDisplayName(itemName);
			item.setItemMeta(meta);
			back = item;
		}
		return back;
	}

	public static boolean isSimilar(ItemStack a, ItemStack b) {
		if (a != null) {
			return a.isSimilar(b);
		}
		return false;
	}
}
