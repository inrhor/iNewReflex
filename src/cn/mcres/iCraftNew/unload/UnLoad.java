package cn.mcres.iCraftNew.unload;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.ICraftTechHolder;
import cn.mcres.iCraftNew.model.Panel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public class UnLoad {
    public static void run() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Inventory inventory = player.getOpenInventory().getTopInventory();
                InventoryHolder holder = inventory.getHolder();
                if (!(holder instanceof ICraftTechHolder)) continue;
                String id = ((ICraftTechHolder) holder).getPanelId();
                if (id != null) {
                    player.closeInventory();
                    Optional<Panel> optionalPanel = IManager.getPanel(id);
                    if (optionalPanel.isPresent())
                    {
                        Panel panel = optionalPanel.get();
                        List<Integer> matrix = panel.getMatrixSlots();
                        List<Integer> resultSlots = panel.getResultsSlots();
                        Location location = player.getLocation();
                        World world = location.getWorld();
                        if (world == null )return;
                        for (int i : matrix) {
                            ItemStack item = inventory.getItem(i);
                            if (item != null) {
                                world.dropItem(location, item);
                            }
                        }
                        for (int i : resultSlots) {
                            ItemStack item = inventory.getItem(i);
                            if (item != null) {
                                if (player.getInventory().first(item) == -1 && player.getInventory().firstEmpty() == -1) {
                                    world.dropItem(location, item);
                                } else {
                                    player.getInventory().addItem(item);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
