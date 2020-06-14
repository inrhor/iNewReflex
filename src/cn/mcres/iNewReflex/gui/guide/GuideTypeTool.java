package cn.mcres.iNewReflex.gui.guide;

import cn.mcres.iNewReflex.api.item.ItemBuild;
import cn.mcres.iNewReflex.expansion.item.tool.ToolItem;
import cn.mcres.iNewReflex.expansion.item.weapon.MeleeItem;
import static cn.mcres.iNewReflex.fileYaml.gui.GetGuiYaml.*;
import cn.mcres.iNewReflex.gui.GuiUtil;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuideTypeTool implements Listener {
    private static String guide = "§r§1§d§7§l§d§5§3§b§r";

    /**
     * 开始的位置
     */
    public static HashMap<Player, Integer> guiValue = new LinkedHashMap<>();
    /**
     * 页数
     */
    public static HashMap<Player, Integer> guiPage = new LinkedHashMap<>();
    /**
     * 已显示的物品数
     */
    public static HashMap<Player, Integer> guiShowAmount = new LinkedHashMap<>();

    public static void open(Player e, int value, int page) {
        Inventory gui = Bukkit.createInventory(e, 6*9, guide +guideTypeToolTitle);

        GuiUtil.createItemListGui(e, gui, value, page, guiValue, guiPage, guiShowAmount, guide);

        if (ToolItem.toolList != null) {
            GuiUtil.setItemLoc(e, gui, ToolItem.toolList, guiValue, guiShowAmount, value, 11);
            if (ToolItem.toolList.size() > 5) {
                GuiUtil.setItemLoc(e, gui, ToolItem.toolList, guiValue, guiShowAmount, guiValue.get(e), 20);
            }
            if (ToolItem.toolList.size() > 10) {
                GuiUtil.setItemLoc(e, gui, ToolItem.toolList, guiValue, guiShowAmount, guiValue.get(e), 29);
            }
        }

        e.openInventory(gui);
    }

    @EventHandler
    void clickGuide(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(guide)) {
            int slot = ev.getRawSlot();
            ev.setCancelled(true);
            Player player = (Player) ev.getWhoClicked();
            if (slot == 49 || guiValue.get(player) == null || guiPage.get(player) == null || guiShowAmount.get(player) == null) {
                player.closeInventory();
                return;
            }
            int startValue = guiValue.get(player);
            int page = guiPage.get(player);
            int showAmount = guiShowAmount.get(player);
            if (slot == 48) {
                if (page == 1) {
                    GuideItemType.open(player);
                }else {
                    open(player, startValue - showAmount - 15, page - 1);
                }
                return;
            }
            if (slot == 50) {
                if (ToolItem.toolList.size()>(15*page)) {
                    open(player, startValue, page+1);
                }
                return;
            }
            if (ev.getCurrentItem() != null && !ev.getCurrentItem().getType().equals(Material.BARRIER) && GuiUtil.isClickSlot(slot)) {
                int getList = GuiUtil.getClickItemInt(slot, page);
                ItemBuild itemBuild =  ToolItem.toolList.get(getList);
                GuiUtil.openRecipe.put(player, itemBuild.getItemId());
                GuideBook.open(player, itemBuild.getItem(), itemBuild.getWiki());
            }
        }
    }
}
