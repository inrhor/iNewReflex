package cn.mcres.iNewReflex.expansion.recipe;

import cn.mcres.iNewReflex.expansion.item.weapon.MeleeItem;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WeaponRecipe {
    public void run() {
        RecipeUtil.fastLightsaberRecipe("lightsaber_blue", "crystal_blue");
        RecipeUtil.fastLightsaberRecipe("lightsaber_red", "crystal_red");
        RecipeUtil.fastLightsaberRecipe("lightsaber_green", "crystal_green");
        RecipeUtil.fastLightsaberRecipe("lightsaber_yellow", "crystal_yellow");
        RecipeUtil.fastLightsaberRecipe("lightsaber_white", "crystal_white");

        RecipeUtil.fastRecipe(MeleeItem.meleeItemList,"obsidian_sword", Material.DIAMOND_SWORD, Material.NETHER_STAR);

        RecipeUtil.fastRecipe(MeleeItem.meleeItemList,"emerald_sword", Material.IRON_SWORD, Material.EMERALD);

        RecipeUtil.fastUmbrellaSunnyDoll("umbrella_blue_sunny_doll", "umbrella_blue");
        RecipeUtil.fastUmbrellaSunnyDoll("umbrella_green_sunny_doll", "umbrella_green");
        RecipeUtil.fastUmbrellaSunnyDoll("umbrella_pink_sunny_doll", "umbrella_pink");
        RecipeUtil.fastUmbrellaSunnyDoll("umbrella_red_sunny_doll", "umbrella_red");
        RecipeUtil.fastUmbrellaSunnyDoll("umbrella_white_sunny_doll", "umbrella_white");
        RecipeUtil.fastUmbrellaSunnyDoll("umbrella_yellow_sunny_doll", "umbrella_yellow");

        RecipeUtil.fastUmbrella("umbrella_blue", /*ReturnMaterial.blueWool()*/new ItemStack(ReturnMaterial.getMaterial("BLUE_WOOL")));
        RecipeUtil.fastUmbrella("umbrella_green", new ItemStack(ReturnMaterial.getMaterial("GREEN_WOOL")));
        RecipeUtil.fastUmbrella("umbrella_pink", new ItemStack(ReturnMaterial.getMaterial("PINK_WOOL")));
        RecipeUtil.fastUmbrella("umbrella_red", new ItemStack(ReturnMaterial.getMaterial("RED_WOOL")));
        RecipeUtil.fastUmbrella("umbrella_white", new ItemStack(ReturnMaterial.getMaterial("WHITE_WOOL")));
        RecipeUtil.fastUmbrella("umbrella_yellow", new ItemStack(ReturnMaterial.getMaterial("YELLOW_WOOL")));
        /*RecipeUtil.fastUmbrella("umbrella_green", ReturnMaterial.greenWool());
        RecipeUtil.fastUmbrella("umbrella_pink", ReturnMaterial.pinkWool());
        RecipeUtil.fastUmbrella("umbrella_red", ReturnMaterial.redWool());
        RecipeUtil.fastUmbrella("umbrella_white", ReturnMaterial.whiteWool());
        RecipeUtil.fastUmbrella("umbrella_yellow", ReturnMaterial.yellowWool());*/
    }
}
