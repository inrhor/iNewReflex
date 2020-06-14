package cn.mcres.iNewReflex.gui.guide;

import static cn.mcres.iNewReflex.fileYaml.gui.GetGuiYaml.*;
import cn.mcres.iNewReflex.gui.GuiUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuideItemType implements Listener {
    private static String guide = "§r§9§9§9§d§5§3§b§r";

    public static void open(Player e) {
        Inventory gui = Bukkit.createInventory(e, 6*9, guide+guideTypeTitle);

        gui.setItem(48, GuiUtil.itemBuild(Material.STONE_BUTTON, previousPage));
        gui.setItem(50, GuiUtil.itemBuild(Material.STONE_BUTTON, previousPage));
        gui.setItem(49, GuiUtil.itemBuild(Material.MAGMA_CREAM, close));

        GuiUtil.addEmpty(gui);

        gui.setItem(11, GuiUtil.itemBuild(Material.DIAMOND_SWORD, guideTypeWeapon));
        gui.setItem(12, GuiUtil.itemBuild(Material.STONE_PICKAXE, guideTypeTool));
        gui.setItem(13, GuiUtil.itemBuild(Material.SUGAR, guideTypeMaterial));
        gui.setItem(14, GuiUtil.itemBuild(Material.GOLD_ORE, guideTypeBlock));
        gui.setItem(15, GuiUtil.itemBuild(Material.APPLE, guideTypeFood));

        e.openInventory(gui);
    }

    @EventHandler
    void clickGuide(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(guide)) {
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            if (slot == 49) {
                player.closeInventory();
            }
            if (slot == 48 || slot == 50) {
                GuideHome.open(player);
            }
            if (slot == 11) {
                GuideTypeWeapon.open(player, 0, 1);
            }
            if (slot == 12) {
                GuideTypeTool.open(player, 0, 1);
            }
            if (slot == 13) {
                GuideTypeMaterial.open(player, 0, 1);
            }
            if (slot == 14) {
                GuideTypeBlock.open(player, 0, 1);
            }
            if (slot == 15) {
                GuideTypeFood.open(player, 0, 1);
            }
            ev.setCancelled(true);
        }
    }
}
