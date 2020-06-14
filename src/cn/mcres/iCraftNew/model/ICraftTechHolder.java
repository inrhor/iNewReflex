package cn.mcres.iCraftNew.model;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ICraftTechHolder implements InventoryHolder {
	private String panelId;
	public ICraftTechHolder(String panelId) {this.panelId = panelId;}
	@Override
	public Inventory getInventory() {return null;}
	public String getPanelId() {return panelId;}
}
