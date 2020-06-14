package cn.mcres.iCraftNew.playerListener;

import cn.mcres.iNewReflex.INewReflex;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.util.ItemStackUtil;
import cn.mcres.iCraftNew.model.ICraftTechHolder;
import cn.mcres.iCraftNew.model.Panel;

public class InventoryDrag implements Listener {
	@EventHandler(ignoreCancelled=true)
	public void onDrag(InventoryDragEvent event) {
		Inventory inventory = event.getInventory();
		if (inventory != null) {
			InventoryHolder holder = inventory.getHolder();
			if (holder instanceof ICraftTechHolder) {
				Optional<Panel> optionalPanel = IManager.getPanel(((ICraftTechHolder) holder).getPanelId());
				if (optionalPanel.isPresent())
				{
					Panel panel = optionalPanel.get();
					boolean isCraft = true;
					List<Integer> bs = panel.getButtonSlots();
					for (Integer slot : bs) {
						if (!ItemStackUtil.isSimilar(inventory.getItem(slot), ItemStackUtil.getButton())) {
							isCraft = false;
						}
					}
					if (!isCraft) {
						event.setCancelled(true);
						return;
					}
					List<Integer> rs = panel.getResultsSlots();
					HashSet<Integer> removeSet = new HashSet<>();
					int a = 0;
					Map<Integer, ItemStack> ni = event.getNewItems();
					for (Integer slot : ni.keySet()) {
						if (rs.contains(slot)) {
							removeSet.add(slot);
							ItemStack item = ni.get(slot);
							if (item == null) continue;
							a += item.getAmount();
						}
					}
					ItemStack item = event.getOldCursor().clone();
					if (event.getCursor() != null) a += event.getCursor().getAmount();
					item.setAmount(a);
					event.setCursor(item);
					Bukkit.getScheduler().runTaskLaterAsynchronously(INewReflex.getMain(), () -> removeSet.forEach(inventory::clear), 0L);
				}
			}
		}
	}
}
