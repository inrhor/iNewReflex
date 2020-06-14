package cn.mcres.iNewReflex.fileYaml.gui;

import org.bukkit.configuration.file.FileConfiguration;

public class GetGuiYaml {
    public static String guideHomeTitle, guideTypeTitle, guideTypeWeaponTitle, guideTypeToolTitle,
            guideTypeMaterialTitle, guideTypeBlockTitle, guideItemShowTitle, guideTypeFoodTitle,
            guideTypeMobsTitle,
            close, empty, previousPage, nextPage, viewRecipe, guideItem, guideMob,
            guideTypeWeapon, guideTypeTool, guideTypeMaterial, guideTypeBlock, guideTypeFood;

    public static void get() {
        final FileConfiguration langYaml = CreateGuiYaml.guiYaml;

        guideHomeTitle = langYaml.getString("guideHomeTitle").replaceAll("&", "§");
        guideTypeTitle = langYaml.getString("guideTypeTitle").replaceAll("&", "§");
        guideTypeWeaponTitle = langYaml.getString("guideTypeWeaponTitle").replaceAll("&", "§");
        guideTypeToolTitle = langYaml.getString("guideTypeToolTitle").replaceAll("&", "§");
        guideTypeMaterialTitle = langYaml.getString("guideTypeMaterialTitle").replaceAll("&", "§");
        guideTypeBlockTitle = langYaml.getString("guideTypeBlockTitle").replaceAll("&", "§");
        guideTypeFoodTitle = langYaml.getString("guideTypeFoodTitle").replaceAll("&", "§");
        guideTypeMobsTitle = langYaml.getString("guideTypeMobsTitle").replaceAll("&", "§");

        guideItemShowTitle = langYaml.getString("guideItemShowTitle").replaceAll("&", "§");

        guideItem = langYaml.getString("guideItem").replaceAll("&", "§");
        guideMob = langYaml.getString("guideMob").replaceAll("&", "§");

        guideTypeWeapon = langYaml.getString("guideTypeWeapon").replaceAll("&", "§");
        guideTypeTool = langYaml.getString("guideTypeTool").replaceAll("&", "§");
        guideTypeMaterial = langYaml.getString("guideTypeMaterial").replaceAll("&", "§");
        guideTypeBlock = langYaml.getString("guideTypeBlock").replaceAll("&", "§");
        guideTypeFood = langYaml.getString("guideTypeFood").replaceAll("&", "§");

        empty = langYaml.getString("empty").replaceAll("&", "§");;
        close = langYaml.getString("close").replaceAll("&", "§");;
        previousPage = langYaml.getString("previousPage").replaceAll("&", "§");
        nextPage = langYaml.getString("nextPage").replaceAll("&", "§");
        viewRecipe = langYaml.getString("viewRecipe").replaceAll("&", "§");
    }
}
