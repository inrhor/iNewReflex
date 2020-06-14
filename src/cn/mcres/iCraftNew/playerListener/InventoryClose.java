package cn.mcres.iCraftNew.playerListener;

import cn.mcres.iNewReflex.cmd.subCmd.EditRecipe;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.util.ItemStackUtil;
import cn.mcres.iCraftNew.api.Events.PanelCloseEvent;
import cn.mcres.iCraftNew.model.ICraftTechHolder;
import cn.mcres.iCraftNew.model.Panel;

public class InventoryClose implements Listener {
	@EventHandler(ignoreCancelled=true)
	public void panelClose(InventoryCloseEvent event) {
		InventoryHolder holder = event.getInventory().getHolder();
		if (holder instanceof ICraftTechHolder) {
			String id = ((ICraftTechHolder) holder).getPanelId();
			if (EditRecipe.EDITING.containsKey(event.getPlayer().getName())) {
				EditRecipe.EDITING.remove(event.getPlayer().getName());
			} else {
				Optional<Panel> optionalPanel = IManager.getPanel(id);
				if (optionalPanel.isPresent())
				{
					Panel panel = optionalPanel.get();
					Inventory inventory = event.getInventory();
					boolean isCraft = true;
					List<Integer> bs = panel.getButtonSlots();
					for (Integer slot : bs) {
						if (!ItemStackUtil.isSimilar(inventory.getItem(slot), ItemStackUtil.getButton())) {
							isCraft = false;
						}
					}
					if (isCraft) {
						PanelCloseEvent panelCloseEvent = new PanelCloseEvent(panel, (Player) event.getPlayer());
						Bukkit.getPluginManager().callEvent(panelCloseEvent);
						List<Integer> matrix = panel.getMatrixSlots();
						List<Integer> resultSlots = panel.getResultsSlots();

						returnItemStack(event, matrix);
						returnItemStack(event, resultSlots);
					}
				}
			}
		}
	}

	/**
	 * 关闭返还物品
	 * @param event 触发的事件
	 * @param slots 遍历的槽位
	 */
	private void returnItemStack(InventoryCloseEvent event, List<Integer> slots) {
		Location location = event.getPlayer().getLocation();
		World world = location.getWorld();
		slots.stream()
				.map(event.getInventory()::getItem)
				.filter(Objects::nonNull)
				.forEach(itemStack -> {
					if (event.getPlayer().getInventory().first(itemStack) == -1 && event.getPlayer().getInventory().firstEmpty() == -1) {
						world.dropItem(location, itemStack);
					} else {
						event.getPlayer().getInventory().addItem(itemStack);
					}
				});
	}
}
