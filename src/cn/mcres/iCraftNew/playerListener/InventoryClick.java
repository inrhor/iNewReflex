package cn.mcres.iCraftNew.playerListener;

import cn.mcres.iNewReflex.cmd.subCmd.EditRecipe;
import cn.mcres.iNewReflex.gui.GuiUtil;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.util.ItemStackUtil;
import cn.mcres.iCraftNew.craft.Craft;
import cn.mcres.iCraftNew.model.ICraftTechHolder;
import cn.mcres.iCraftNew.model.Panel;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {
	@EventHandler
	void onSelGuiClick(InventoryClickEvent ev) {
		InventoryView gui = ev.getView();
		if (gui.getTitle().equals(IManager.titleSelGui.get("selTitle"))) {
			if (ev.getCurrentItem()!=null && ev.getCurrentItem().getItemMeta()!=null) {
				ItemStack item = ev.getCurrentItem();
				Player player = (Player) ev.getWhoClicked();
				if (item.getType().equals(Material.CHEST)) {
					player.openWorkbench(null ,true);
				}else if (item.getType().equals(Material.ENDER_CHEST)) {
					Optional<Panel> optionalPanel = IManager.getPanel(INewReflex.getMain().getConfig().getString("iCraft.setting.gui"));
					if (optionalPanel.isPresent()) {
						Panel panel = optionalPanel.get();
						IManager.openCraftingGUI(player, panel);
					}
				}
			}
			ev.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled=true)
	public void clickBaffle(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory != null && event.getCurrentItem() != null) {
			InventoryHolder holder = inventory.getHolder();
			if (holder instanceof ICraftTechHolder) {
				if (ItemStackUtil.isSimilar(event.getCurrentItem(), ItemStackUtil.getBaffle())) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler(ignoreCancelled=true)
	public void clickButton(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory != null && event.getCurrentItem() != null) {
			InventoryHolder holder = inventory.getHolder();
			if (holder instanceof ICraftTechHolder) {
				if (event.getCurrentItem().isSimilar(ItemStackUtil.getButton())) {
					Optional<Panel> optionalPanel = IManager.getPanel(((ICraftTechHolder) holder).getPanelId());
					if (optionalPanel.isPresent()) {
						Panel panel = optionalPanel.get();
						event.setCancelled(true);
						Craft.craft((Player) event.getWhoClicked(), panel, event.getClickedInventory());
					}
				}
			}
		}
	}
	@EventHandler(ignoreCancelled=true)
	public void clickBack(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory != null && event.getCurrentItem() != null) {
			InventoryHolder holder = inventory.getHolder();
			if (holder instanceof ICraftTechHolder) {
				if (event.getCurrentItem().isSimilar(ItemStackUtil.getBack())) {
					event.setCancelled(true);
					Player player = (Player) event.getWhoClicked();
					if (GuiUtil.previousPageGui.get(player) != null) {
						player.openInventory(GuiUtil.previousPageGui.get(player));
					}else {
						player.closeInventory();
					}
				}
			}
		}
	}
	@EventHandler(ignoreCancelled=true)
	public void clickClose(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory != null && event.getCurrentItem() != null) {
			InventoryHolder holder = inventory.getHolder();
			if (holder instanceof ICraftTechHolder) {
				if (event.getCurrentItem().isSimilar(ItemStackUtil.getClose())) {
					event.setCancelled(true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(INewReflex.getMain(), event.getWhoClicked()::closeInventory, 0L);
				}
			}
		}
	}
	@EventHandler(ignoreCancelled=true)
	public void clickResult(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory != null && event.getCursor().getType() != Material.AIR) {
			InventoryHolder holder = inventory.getHolder();
			if (holder instanceof ICraftTechHolder) {
				if (!EditRecipe.EDITING.containsKey(event.getWhoClicked().getName())) {
					Optional<Panel> optionalPanel = IManager.getPanel(((ICraftTechHolder) holder).getPanelId());
					if (optionalPanel.isPresent()) {
						Panel panel = optionalPanel.get();
						if (panel.getResultsSlots().contains(event.getSlot())) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler(ignoreCancelled=true)
	public void shiftClick(InventoryClickEvent event) {
		if (event.isShiftClick()) {
			Inventory inventory = event.getWhoClicked().getOpenInventory().getTopInventory();
			if (event.getClickedInventory()!=null && event.getCurrentItem() != null && !event.getClickedInventory().equals(inventory)) {
				InventoryHolder holder = inventory.getHolder();
				if (holder instanceof ICraftTechHolder) {
					Optional<Panel> optionalPanel = IManager.getPanel(((ICraftTechHolder) holder).getPanelId());
					if (optionalPanel.isPresent()) {
						Panel panel = optionalPanel.get();
						if (panel.getResultsSlots().contains(inventory.first(event.getCurrentItem()) != -1 ? inventory.first(event.getCurrentItem()) : inventory.firstEmpty())) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler(ignoreCancelled=true)
	public void clickView(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory != null) {
			InventoryHolder holder = inventory.getHolder();
			if (holder instanceof ICraftTechHolder) {
				Optional<Panel> optionalPanel = IManager.getPanel(((ICraftTechHolder) holder).getPanelId());
				if (optionalPanel.isPresent()) {
					Panel panel = optionalPanel.get();
					if (!EditRecipe.EDITING.containsKey(event.getWhoClicked().getName())) {
						boolean isCraft = true;
						List<Integer> bs = panel.getButtonSlots();
						for (Integer slot : bs) {
							if (!ItemStackUtil.isSimilar(inventory.getItem(slot), ItemStackUtil.getButton())) {
								isCraft = false;
							}
						}
						if (!isCraft) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	@EventHandler(ignoreCancelled=true)
	public void doubleClick(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		InventoryHolder holder = inventory.getHolder();
		if (holder instanceof ICraftTechHolder) {
			if (event.getClick() == ClickType.DOUBLE_CLICK) {
				event.setCancelled(true);
			}
		}
	}
}
