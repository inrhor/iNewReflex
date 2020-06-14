package cn.mcres.iNewReflex.expansion.item.tool;

import cn.mcres.iNewReflex.api.item.ItemBuild;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import cn.mcres.iNewReflex.fileYaml.item.CreateToolYaml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

// 工具
public class ToolItem {
    public static HashMap<String, ItemBuild> toolItemList = new LinkedHashMap<>();

    public static List<ItemBuild> toolList = new ArrayList<>();

    private FileConfiguration toolYaml;

    public void loadAll() {
        this.toolYaml = CreateToolYaml.toolYaml;

        // 翡翠斧
        if (this.toolYaml.getBoolean("tool.emerald_axe.enable")) {
            emeraldAxe();
        }
        // 翡翠锄
        if (this.toolYaml.getBoolean("tool.emerald_hoe.enable")) {
            emeraldHoe();
        }
        // 翡翠镐
        if (this.toolYaml.getBoolean("tool.emerald_pickaxe.enable")) {
            emeraldPickaxe();
        }
        // 翡翠铲
        if (this.toolYaml.getBoolean("tool.emerald_shovel.enable")) {
            emeraldShovel();
        }
        // 黑曜之斧
        if (this.toolYaml.getBoolean("tool.obsidian_axe.enable")) {
            obsidianAxe();
        }
        // 黑曜之锄
        if (this.toolYaml.getBoolean("tool.obsidian_hoe.enable")) {
            obsidianHoe();
        }
        // 黑曜之镐
        if (this.toolYaml.getBoolean("tool.obsidian_pickaxe.enable")) {
            obsidianPickaxe();
        }
        // 黑曜之铲
        if (this.toolYaml.getBoolean("tool.obsidian_shovel.enable")) {
            obsidianShovel();
        }
    }

    private void addList(String itemId, ItemBuild itemBuild) {
        toolItemList.put(itemId, itemBuild);
        toolList.add(itemBuild);
    }

    private void emeraldAxe() {
        String itemId = "emerald_axe";
        ItemBuild itemBuild = new ItemBuild(this.toolYaml, itemId,
                Material.IRON_AXE,
                30021,
                "§a§l§c§c§l§b§2",
                "tool");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void emeraldHoe() {
        String itemId = "emerald_hoe";
        ItemBuild itemBuild = new ItemBuild(this.toolYaml, itemId,
                Material.IRON_HOE,
                30022,
                "§a§l§c§c§l§b§3",
                "tool");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void emeraldPickaxe() {
        String itemId = "emerald_pickaxe";
        ItemBuild itemBuild = new ItemBuild(this.toolYaml, itemId,
                Material.IRON_PICKAXE,
                30023,
                "§a§l§c§c§l§b§4",
                "tool");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void emeraldShovel() {
        String itemId = "emerald_shovel";
        ItemBuild itemBuild = new ItemBuild(this.toolYaml, itemId,
                /*ReturnMaterial.ironShovel()*/ReturnMaterial.getMaterial("IRON_SHOVEL"),
                30024,
                "§a§l§c§c§l§b§5",
                "tool");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void obsidianAxe() {
        String itemId = "obsidian_axe";
        ItemBuild itemBuild = new ItemBuild(this.toolYaml, itemId,
                Material.DIAMOND_AXE,
                30025,
                "§a§l§c§c§l§b§6",
                "tool");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void obsidianHoe() {
        String itemId = "obsidian_hoe";
        ItemBuild itemBuild = new ItemBuild(this.toolYaml, itemId,
                Material.DIAMOND_HOE,
                30026,
                "§a§l§c§c§l§b§7",
                "tool");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void obsidianPickaxe() {
        String itemId = "obsidian_pickaxe";
        ItemBuild itemBuild = new ItemBuild(this.toolYaml, itemId,
                Material.DIAMOND_PICKAXE,
                30027,
                "§a§l§c§c§l§b§8",
                "tool");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void obsidianShovel() {
        String itemId = "obsidian_shovel";
        ItemBuild itemBuild = new ItemBuild(this.toolYaml, itemId,
                /*ReturnMaterial.diamondShovel()*/ReturnMaterial.getMaterial("DIAMOND_SHOVEL"),
                30028,
                "§a§l§c§c§l§b§9",
                "tool");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }
}
