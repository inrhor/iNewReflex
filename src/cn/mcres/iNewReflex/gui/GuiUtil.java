package cn.mcres.iNewReflex.gui;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.Panel;
import cn.mcres.iCraftNew.model.Recipe;
import cn.mcres.iNewReflex.api.block.BlockBuild;
import cn.mcres.iNewReflex.api.item.FoodBuild;
import cn.mcres.iNewReflex.api.item.ItemBuild;
import cn.mcres.iNewReflex.api.item.MaterialBuild;
import static cn.mcres.iNewReflex.fileYaml.gui.GetGuiYaml.*;
import cn.mcres.iNewReflex.mobsBook.MobsBookData;
import cn.mcres.iNewReflex.utils.LoreReplace;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtil {
    /**
     * 为了在打开物品浏览图鉴介绍时，返回该界面
     */
    public static HashMap<Player, Inventory> previousPageGui = new LinkedHashMap<>();

    /**
     * 为了打开配方公式界面
     */
    public static HashMap<Player, String> openRecipe = new LinkedHashMap<>();

    public static ItemStack itemBuild(Material material, String name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack itemBuild(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void addEmpty(Inventory inv) {
        for (int i = 11; i < 16; i++ ) {
            inv.setItem(i, itemBuild(Material.BARRIER, empty));
        }
        for (int i = 20; i < 25; i++ ) {
            inv.setItem(i, itemBuild(Material.BARRIER, empty));
        }
        for (int i = 29; i < 34; i++ ) {
            inv.setItem(i, itemBuild(Material.BARRIER, empty));
        }
    }

    public static void setItemLoc(Player e, Inventory gui,
                                  List<ItemBuild> list,
                                  HashMap<Player, Integer> guiValue,
                                  HashMap<Player, Integer> guiShowAmount,
                                  int value,
                                  int startLoc) {
        int addLoc = 0;
        for (int i = value; i < value+5; i++) {
            if (!list.isEmpty() && list.size() > i) {
                gui.setItem(startLoc+addLoc, list.get(i).getItem());
                guiValue.put(e, guiValue.get(e)+1);
                guiShowAmount.put(e, guiShowAmount.get(e)+1);
                addLoc++;
            }
        }
    }

    public static void setItemMaterialLoc(Player e, Inventory gui,
                                  List<MaterialBuild> list,
                                  HashMap<Player, Integer> guiValue,
                                  HashMap<Player, Integer> guiShowAmount,
                                  int value,
                                  int startLoc) {
        int addLoc = 0;
        for (int i = value; i < value+5; i++) {
            if (!list.isEmpty() && list.size() > i) {
                gui.setItem(startLoc+addLoc, list.get(i).getItem());
                guiValue.put(e, guiValue.get(e)+1);
                guiShowAmount.put(e, guiShowAmount.get(e)+1);
                addLoc++;
            }
        }
    }

    public static void setItemFoodLoc(Player e, Inventory gui,
                                          List<FoodBuild> list,
                                          HashMap<Player, Integer> guiValue,
                                          HashMap<Player, Integer> guiShowAmount,
                                          int value,
                                          int startLoc) {
        int addLoc = 0;
        for (int i = value; i < value+5; i++) {
            if (!list.isEmpty() && list.size() > i) {
                gui.setItem(startLoc+addLoc, list.get(i).getItem());
                guiValue.put(e, guiValue.get(e)+1);
                guiShowAmount.put(e, guiShowAmount.get(e)+1);
                addLoc++;
            }
        }
    }

    public static void setItemBlockLoc(Player e, Inventory gui,
                                          List<BlockBuild> list,
                                          HashMap<Player, Integer> guiValue,
                                          HashMap<Player, Integer> guiShowAmount,
                                          int value,
                                          int startLoc) {
        int addLoc = 0;
        for (int i = value; i < value+5; i++) {
            if (!list.isEmpty() && list.size() > i) {
                gui.setItem(startLoc+addLoc, list.get(i).getItem());
                guiValue.put(e, guiValue.get(e)+1);
                guiShowAmount.put(e, guiShowAmount.get(e)+1);
                addLoc++;
            }
        }
    }

    public static void setItemMobsLoc(Player e, Inventory gui,
                                       List<MobsBookData> list,
                                       HashMap<Player, Integer> guiValue,
                                       HashMap<Player, Integer> guiShowAmount,
                                       int value,
                                       int startLoc,
                                      String notFine,
                                      String isFine) {
        int addLoc = 0;
        for (int i = value; i < value+5; i++) {
            if (!list.isEmpty() && list.size() > i) {
                MobsBookData mobsBookData = list.get(i);
                ItemStack itemStack = mobsBookData.getItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setLore(LoreReplace.loreMobsFind(mobsBookData.getItemLore(), e, mobsBookData.getModelId(), notFine, isFine));
                itemStack.setItemMeta(itemMeta);
                gui.setItem(startLoc+addLoc, itemStack);
                guiValue.put(e, guiValue.get(e)+1);
                guiShowAmount.put(e, guiShowAmount.get(e)+1);
                addLoc++;
            }
        }
    }

    public static void createItemListGui(Player e, Inventory gui, int value, int page, HashMap<Player, Integer> guiValue, HashMap<Player, Integer> guiPage, HashMap<Player, Integer> guiShowAmount, String guide) {
        guiValue.put(e, value);
        guiPage.put(e, page);
        guiShowAmount.put(e, 0);
        GuiUtil.previousPageGui.put(e, gui);

        gui.setItem(48, GuiUtil.itemBuild(Material.STONE_BUTTON, previousPage));
        gui.setItem(50, GuiUtil.itemBuild(Material.STONE_BUTTON, nextPage));
        gui.setItem(49, GuiUtil.itemBuild(Material.MAGMA_CREAM, close));

        GuiUtil.addEmpty(gui);
    }

    public static void openRecipeView(Player player) {
        String panelId = "inewrx";
        String recipeId = GuiUtil.openRecipe.get(player);
        Optional<Panel> optionalPanel = IManager.getPanel(panelId);
        if (optionalPanel.isPresent()) {
            Panel panel = optionalPanel.get();
            Optional<Recipe> optionalRecipe = IManager.getRecipe(panelId, recipeId);
            if (optionalRecipe.isPresent()) {
                Recipe recipe = optionalRecipe.get();
                IManager.openGUI(player, panel, recipe, true);
            }
        }
    }

    /*public static int getClickSlot(int slot) {
        if (10 < slot && slot < 16) {
            return slot-11;
        }
        if (19 < slot && slot < 25) {
            return slot-14;
        }
        if (28 < slot && slot < 34) {
            return slot-18;
        }
        return 0;
    }*/
    public static int getClickItemInt(int slot, int page) {
        int newPage = page-1;
        if (10 < slot && slot < 16) {
            return (slot-11)+15*newPage;
        }
        if (19 < slot && slot < 25) {
            return (slot-15)+15*newPage;
        }
        if (28 < slot && slot < 34) {
            return (slot-19)+15*newPage;
        }
        return 0;
    }

    public static boolean isClickSlot(int slot) {
        if (10 < slot && slot < 16) {
            return true;
        }
        if (19 < slot && slot < 25) {
            return true;
        }
        return 28 < slot && slot < 34;
    }

    /*public static int getItemInt(Inventory gui) {
        int s = 0;
        for ( ItemStack item : gui.getContents() ) {
            if ( item != null ){
                if (!item.getType().equals(Material.MAGMA_CREAM)
                        || !item.getType().equals(Material.STONE_BUTTON)
                        || !item.getType().equals(Material.BARRIER)) {
                    s++;
                }
            }
        }
        return s;
    }*/
}
