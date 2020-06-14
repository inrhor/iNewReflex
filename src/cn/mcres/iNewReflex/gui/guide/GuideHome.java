package cn.mcres.iNewReflex.gui.guide;

import static cn.mcres.iNewReflex.fileYaml.gui.GetGuiYaml.*;
import cn.mcres.iNewReflex.gui.GuiUtil;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuideHome implements Listener {
    private static String guide = "§r§r§9§9§d§5§3§b§r";

    public static void open(Player e) {
        Inventory gui = Bukkit.createInventory(e, 6*9, guide+guideHomeTitle);
        gui.setItem(49, GuiUtil.itemBuild(Material.MAGMA_CREAM, close));

        GuiUtil.addEmpty(gui);

        gui.setItem(11, GuiUtil.itemBuild(Material.DIAMOND_AXE, guideItem));
        Material head = ReturnMaterial.getMaterial("CREEPER_HEAD");
        gui.setItem(12, GuiUtil.itemBuild(head, guideMob));

        e.openInventory(gui);
    }

    @EventHandler
    void clickGuide(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(guide)) {
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            ev.setCancelled(true);
            if (slot == 49) {
                player.closeInventory();
                return;
            }
            if (slot == 11) {
                GuideItemType.open(player);
                return;
            }
            if (slot == 12) {
                GuideTypeMobs.open(player, 0, 1);
            }
        }
    }
}
