package cn.mcres.iNewReflex.expansion.item.weapon;

import cn.mcres.iNewReflex.api.item.ItemBuild;
import cn.mcres.iNewReflex.fileYaml.item.CreateWeaponYaml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

// 近战武器
public class MeleeItem {
    public static HashMap<String, ItemBuild> meleeItemList = new LinkedHashMap<>();

    public static List<ItemBuild> weaponList = new ArrayList<>();

    private void addList(String itemId, ItemBuild itemBuild) {
        meleeItemList.put(itemId, itemBuild);
        weaponList.add(itemBuild);
    }

    private FileConfiguration weaponYaml;

    public void loadAll() {
        this.weaponYaml = CreateWeaponYaml.weaponYaml;

        if (this.weaponYaml.getBoolean("weapon.emerald_sword.enable")) {
            emeraldSword();
        }
        if (this.weaponYaml.getBoolean("weapon.obsidian_sword.enable")) {
            obsidianSword();
        }
        if (this.weaponYaml.getBoolean("weapon.lightsaber_blue.enable")) {
            lightsaberBlue();
        }
        if (this.weaponYaml.getBoolean("weapon.lightsaber_red.enable")) {
            lightsaberRed();
        }
        if (this.weaponYaml.getBoolean("weapon.lightsaber_green.enable")) {
            lightsaberGreen();
        }
        if (this.weaponYaml.getBoolean("weapon.lightsaber_yellow.enable")) {
            lightsaberYellow();
        }
        if (this.weaponYaml.getBoolean("weapon.lightsaber_white.enable")) {
            lightsaberWhite();
        }

        if (this.weaponYaml.getBoolean("weapon.umbrella_blue.enable")) {
            umbrellaBlue();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_green.enable")) {
            umbrellaGreen();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_pink.enable")) {
            umbrellaPink();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_red.enable")) {
            umbrellaRed();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_white.enable")) {
            umbrellaWhite();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_yellow.enable")) {
            umbrellaYellow();
        }

        if (this.weaponYaml.getBoolean("weapon.umbrella_blue_sunny_doll.enable")) {
            umbrellaSunnyDollBlue();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_green_sunny_doll.enable")) {
            umbrellaSunnyDollGreen();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_pink_sunny_doll.enable")) {
            umbrellaSunnyDollPink();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_red_sunny_doll.enable")) {
            umbrellaSunnyDollRed();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_white_sunny_doll.enable")) {
            umbrellaSunnyDollWhite();
        }
        if (this.weaponYaml.getBoolean("weapon.umbrella_yellow_sunny_doll.enable")) {
            umbrellaSunnyDollYellow();
        }

        if (this.weaponYaml.getBoolean("weapon.zephyr_fan_1.enable")) {
            zephyrFan1();
        }
        if (this.weaponYaml.getBoolean("weapon.zephyr_fan_2.enable")) {
            zephyrFan2();
        }
        /*if (this.weaponYaml.getBoolean("weapon.umbrella_yellow.enable")) {

        }*/
    }

    // 翡翠剑
    private void emeraldSword() {
        String itemId = "emerald_sword";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.IRON_SWORD,20021,
                "§a§l§c§c§l§a§1",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    // 黑曜之剑
    private void obsidianSword() {
        String itemId = "obsidian_sword";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20022,
                "§a§l§c§c§l§a§2",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    // 蓝-激光剑
    private void lightsaberBlue() {
        String itemId = "lightsaber_blue";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20023,
                "§a§l§c§c§l§a§3",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    // 红-激光剑
    private void lightsaberRed() {
        String itemId = "lightsaber_red";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20024,
                "§a§l§c§c§l§a§4",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    // 绿-激光剑
    private void lightsaberGreen() {
        String itemId = "lightsaber_green";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20025,
                "§a§l§c§c§l§a§5",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    // 黄-激光剑
    private void lightsaberYellow() {
        String itemId = "lightsaber_yellow";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20026,
                "§a§l§c§c§l§a§6",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    // 白-激光剑
    private void lightsaberWhite() {
        String itemId = "lightsaber_white";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20027,
                "§a§l§c§c§l§a§7",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaBlue() {
        String itemId = "umbrella_blue";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20028,
                "§a§l§c§c§l§b§9§9§1",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaGreen() {
        String itemId = "umbrella_green";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20029,
                "§a§l§c§c§l§b§9§9§2",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaPink() {
        String itemId = "umbrella_pink";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20030,
                "§a§l§c§c§l§b§9§9§3",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaRed() {
        String itemId = "umbrella_red";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20031,
                "§a§l§c§c§l§b§9§9§4",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaWhite() {
        String itemId = "umbrella_white";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20032,
                "§a§l§c§c§l§b§9§9§5",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaYellow() {
        String itemId = "umbrella_yellow";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20033,
                "§a§l§c§c§l§b§9§9§6",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaSunnyDollBlue() {
        String itemId = "umbrella_blue_sunny_doll";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20034,
                "§a§l§c§c§l§c§9§9§1",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaSunnyDollGreen() {
        String itemId = "umbrella_green_sunny_doll";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20035,
                "§a§l§c§c§l§c§9§9§2",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaSunnyDollPink() {
        String itemId = "umbrella_pink_sunny_doll";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20036,
                "§a§l§c§c§l§c§9§9§3",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaSunnyDollRed() {
        String itemId = "umbrella_red_sunny_doll";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20037,
                "§a§l§c§c§l§c§9§9§4",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void umbrellaSunnyDollWhite() {
        String itemId = "umbrella_white_sunny_doll";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20038,
                "§a§l§c§c§l§c§9§9§5",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }
    private void umbrellaSunnyDollYellow() {
        String itemId = "umbrella_yellow_sunny_doll";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20039,
                "§a§l§c§c§l§c§9§9§6",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void zephyrFan1() {
        String itemId = "zephyr_fan_1";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20040,
                "§a§l§c§c§l§f§9§9§1",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }

    private void zephyrFan2() {
        String itemId = "zephyr_fan_2";
        ItemBuild itemBuild = new ItemBuild(this.weaponYaml, itemId,
                Material.DIAMOND_SWORD,20041,
                "§a§l§c§c§l§f§9§9§2",
                "weapon");
        itemBuild.createItem();
        addList(itemId, itemBuild);
    }
}
