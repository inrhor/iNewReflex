package cn.mcres.iNewReflex.gui.guide;

import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import static cn.mcres.iNewReflex.fileYaml.gui.GetGuiYaml.*;
import cn.mcres.iNewReflex.gui.GuiUtil;
import cn.mcres.iNewReflex.mobsBook.MobsBookManager;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class GuideTypeMobs implements Listener {
    private static String guide = "§r§1§l§l§r§5§7§d§5§7§9§r";

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
        Inventory gui = Bukkit.createInventory(e, 6*9, guide +guideTypeMobsTitle);

        GuiUtil.createItemListGui(e, gui, value, page, guiValue, guiPage, guiShowAmount, guide);

        String notFine = MobsBookManager.notFine;
        String isFine = MobsBookManager.isFine;

        if (!MobsBookManager.mobDataList.isEmpty()) {
            GuiUtil.setItemMobsLoc(e, gui, MobsBookManager.mobDataList, guiValue, guiShowAmount, value, 11, notFine, isFine);
            if (MobsBookManager.mobDataList.size() > 5) {
                GuiUtil.setItemMobsLoc(e, gui, MobsBookManager.mobDataList, guiValue, guiShowAmount, guiValue.get(e), 20, notFine, isFine);
            }
            if (MobsBookManager.mobDataList.size() > 10) {
                GuiUtil.setItemMobsLoc(e, gui, MobsBookManager.mobDataList, guiValue, guiShowAmount, guiValue.get(e), 29, notFine, isFine);
            }
        }

        e.openInventory(gui);
    }

    @EventHandler
    void clickGuide(InventoryClickEvent ev) {
        InventoryView gui = ev.getView();
        if (gui.getTitle().startsWith(guide)) {
            ev.setCancelled(true);
            int slot = ev.getRawSlot();
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
                    GuideHome.open(player);
                }else {
                    open(player, startValue-showAmount-15, page-1);
                }
                return;
            }
            if (slot == 50) {
                if (MobsBookManager.mobDataList.size()>(15*page)) {
                    open(player, startValue, page+1);
                }
            }
        }
    }
}
