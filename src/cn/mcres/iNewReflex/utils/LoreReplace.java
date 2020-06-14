package cn.mcres.iNewReflex.utils;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.api.item.FoodBuild;
import cn.mcres.iNewReflex.api.item.ItemBuild;
import cn.mcres.iNewReflex.crop.CropData;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class LoreReplace {
    public static List<String> lore(ItemBuild itemBuild, List<String> lore, String optifine) {
        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(s
                    .replaceAll("@optifine", optifine)
                    .replaceAll("@attribute_tag", attributeTag())
                    .replaceAll("@attribute_damage", attributeDamage()+itemBuild.getAttackDamage())
                    .replaceAll("@attribute_attackSpeed", attributeAttackSpeed()+itemBuild.getAttackSpeed())
                    .replaceAll("@durability_tag", INewReflex.durabilityTag)
                    .replaceAll("@durability_info", durabilityLore(itemBuild.getNowDurability(), itemBuild.getDurability(), false))
                    .replaceAll("&", "§"));
        }
        return newLore;
    }

    public static List<String> lore(List<String> lore, String optifine) {
        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(s
                    .replaceAll("@optifine", optifine)
                    .replaceAll("@attribute_tag", attributeTag())
                    .replaceAll("&", "§"));
        }
        return newLore;
    }

    public static List<String> lore(FoodBuild foodBuild, List<String> lore, String optifine) {
        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(s
                    .replaceAll("@optifine", optifine)
                    .replaceAll("@food_tag", foodTag())
                    .replaceAll("@food_addFood", foodAddFood()+foodBuild.getAddFood())
                    .replaceAll("&", "§"));
        }
        return newLore;
    }

    public static List<String> lore(List<String> lore) {
        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(s.replaceAll("&", "§"));
        }
        return newLore;
    }

    public static List<String> loreMobsFind(List<String> lore, Player player, String modelId,String notFine, String isFine) {
        String fine = notFine;
        if (player.hasPermission("inewrx.mobsbook."+modelId)) {
            fine = isFine;
        }
        List<String> newLore = new ArrayList<>();
        for (String s : lore) {
            newLore.add(s.replaceAll("@inewrx_mobsFind", fine));
        }
        return newLore;
    }

    public static List<String> loreDurability(List<String> lore, int min, int max, boolean addEmpty) {
        int i = 0;
        for (String s : lore) {
            if (s.contains(durabilityCheckTag())) {
                lore.set(i, durabilityLore(min, max, addEmpty));
            }
            i++;
        }
        return lore;
    }

    public static List<String> loreHarvest(List<String> lore, int min, int max) {
        int i = 0;
        for (String s : lore) {
            if (s.contains(harvestCheckTag()) || s.toLowerCase().contains("@inewrx_harvest")) {
                lore.set(i, harvestLore(min, max));
            }
            i++;
        }
        return lore;
    }
    private static String harvestCheckTag() {
        return "§4§4§5§5§a§r§r";
    }

    private static String durabilityCheckTag() {
        return "§4§4§5§5§a§c§r";
    }

    private static String harvestLore(int min, int max) {
        return harvestCheckTag() + CropData.harvestTag  + "§7§l[ " + InfoSend.getProgressBar(
                min,
                max,
                20,
                "|",
                "§a",
                "§7") + "§7§l ]";
    }

    private static String durabilityLore(int min, int max, boolean addEmpty) {
        String empty = "";
        if (addEmpty) {
            empty = INewReflex.durabilityTag;
        }
        return durabilityCheckTag() + empty + "§7§l[ " + InfoSend.getProgressBar(
                min,
                max,
                60,
                "|",
                "§a",
                "§7") + "§7§l ]";
    }

    private static String attributeTag() {
        return INewReflex.getMain().getConfig().getString("attribute.tag");
    }

    private static String foodTag() {
        return INewReflex.getMain().getConfig().getString("food.tag");
    }

    private static String foodAddFood() {
        return INewReflex.getMain().getConfig().getString("food.addFood");
    }

    private static String attributeDamage() {
        return INewReflex.getMain().getConfig().getString("attribute.damage");
    }

    private static String attributeAttackSpeed() {
        return INewReflex.getMain().getConfig().getString("attribute.attackSpeed");
    }

}
