package cn.mcres.iNewReflex.gui.guide;

import static cn.mcres.iNewReflex.fileYaml.gui.GetGuiYaml.*;
import cn.mcres.iNewReflex.gui.GuiUtil;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class GuideBook implements Listener {
    private static String guide = "§r§9§9§9§d§5§3§b§a§r";

    public static void open(Player player, ItemStack itemStack, List<String> wiki) {
        Inventory gui = Bukkit.createInventory(player, 6*9, guide+guideItemShowTitle+itemStack.getItemMeta().getDisplayName());

        gui.setItem(48, GuiUtil.itemBuild(Material.STONE_BUTTON, previousPage));
        gui.setItem(50, GuiUtil.itemBuild(Material.STONE_BUTTON, previousPage));
        gui.setItem(49, GuiUtil.itemBuild(Material.MAGMA_CREAM, close));

        gui.setItem(20, GuiUtil.itemBuild(Material.BOOK, "§r                                             ", wiki)); // 45个空格
        gui.setItem(22, itemStack);
        gui.setItem(24, GuiUtil.itemBuild(/*ReturnMaterial.craftingTable()*/ReturnMaterial.getMaterial("CRAFTING_TABLE"), viewRecipe));

        player.openInventory(gui);
    }

    @EventHandler
    void clickGuide(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(guide)) {
            int slot = ev.getRawSlot();
            Player player = (Player) ev.getWhoClicked();
            ev.setCancelled(true);
            if (slot == 49 || GuiUtil.previousPageGui.get(player) == null || GuiUtil.openRecipe.get(player) == null) {
                player.closeInventory();
                return;
            }
            if (slot == 48 || slot == 50) {
                player.openInventory(GuiUtil.previousPageGui.get(player));
                return;
            }
            if (slot == 24) {
                GuiUtil.openRecipeView(player);
            }
        }
    }
}
