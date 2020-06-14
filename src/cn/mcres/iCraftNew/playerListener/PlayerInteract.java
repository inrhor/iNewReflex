package cn.mcres.iCraftNew.playerListener;

import cn.mcres.iCraftNew.model.Panel;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iNewReflex.INewReflex;

import java.util.Optional;


public class PlayerInteract implements Listener {
	@EventHandler(ignoreCancelled=true)
	public void clickBlock(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (!event.getPlayer().isSneaking()) {
			if (event.getClickedBlock() != null
					&& event.getClickedBlock().getType() == /*ReturnMaterial.craftingTable()*/ReturnMaterial.getMaterial("CRAFTING_TABLE")
					&& event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (INewReflex.getMain().getConfig().getBoolean("iCraft.setting.default")) {
					IManager.openSelGui(player);
				}else {
					Optional<Panel> optionalPanel = IManager.getPanel(INewReflex.getMain().getConfig().getString("iCraft.setting.gui"));
					if (optionalPanel.isPresent()) {
						Panel panel = optionalPanel.get();
						event.setCancelled(true);
						IManager.openCraftingGUI(player, panel);
					}
				}
			}
		}
	}
}
