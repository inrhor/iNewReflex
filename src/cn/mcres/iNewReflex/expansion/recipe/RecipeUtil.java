package cn.mcres.iNewReflex.expansion.recipe;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.Recipe;
import cn.mcres.iNewReflex.api.item.ItemBuild;
import cn.mcres.iNewReflex.expansion.item.material.MaterialItem;
import cn.mcres.iNewReflex.expansion.item.weapon.MeleeItem;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

class RecipeUtil {
    static void fastRecipe(HashMap<String, ItemBuild> map, String obsidianResult, Material itemMatrixMaterial, Material itemMatrixMaterial2) {
        if (map.get(obsidianResult) != null) {
            List<ItemStack> itemMatrixList = ReturnMatrix.getEmptyMatrix();
            itemMatrixList.set(12, new ItemStack(itemMatrixMaterial));
            ItemStack itemMatrix = new ItemStack(itemMatrixMaterial2);
            fastMaterial(obsidianResult, itemMatrixList, itemMatrix, map);
        }
    }

    static void fastLightsaberRecipe(String lightsaber, String crystal) {
        if (MeleeItem.meleeItemList.get(lightsaber) != null
                && MaterialItem.materialItemList.get(crystal) != null) {
            List<ItemStack> itemMatrixList = ReturnMatrix.getEmptyMatrix();
            itemMatrixList.set(12, new ItemStack(Material.DIAMOND_SWORD));
            ItemStack crystalItem = MaterialItem.materialItemList.get(crystal).getItem();
            fastMaterial(lightsaber, itemMatrixList, crystalItem, MeleeItem.meleeItemList);
        }
    }

    static void fastUmbrellaSunnyDoll(String umbrellaSunnyDoll, String umbrella) {
        if (MeleeItem.meleeItemList.get(umbrellaSunnyDoll) != null
                && MaterialItem.materialItemList.get("sunny_doll") != null
                && MeleeItem.meleeItemList.get(umbrella) !=null) {
            List<ItemStack> itemMatrixList = ReturnMatrix.getEmptyMatrix();
            itemMatrixList.set(6, MeleeItem.meleeItemList.get(umbrella).getItem());
            Material lead = ReturnMaterial.getMaterial("LEAD");
            itemMatrixList.set(5, new ItemStack(lead));
            itemMatrixList.set(10, new ItemStack(/*ReturnMaterial.lead()*/lead));
            itemMatrixList.set(9, MaterialItem.materialItemList.get("sunny_doll").getItem());
            List<ItemStack> results = Collections.singletonList(MeleeItem.meleeItemList.get(umbrellaSunnyDoll).getItem());
            Recipe recipe = new Recipe(umbrellaSunnyDoll,"inewrx", itemMatrixList, results);
            IManager.registerRecipe(recipe);
        }
    }

    static void fastUmbrella(String umbrella, ItemStack wool) {
        if (MaterialItem.materialItemList.get("sunny_doll") != null
                && MeleeItem.meleeItemList.get(umbrella) !=null) {
            List<ItemStack> itemMatrixList = ReturnMatrix.setMatrix(wool);
            itemMatrixList.set(6, new ItemStack(Material.DIAMOND_SWORD));
            Material lead = ReturnMaterial.getMaterial("LEAD");
            itemMatrixList.set(5, new ItemStack(lead));
            itemMatrixList.set(10, new ItemStack(lead));
            List<ItemStack> results = Collections.singletonList(MeleeItem.meleeItemList.get(umbrella).getItem());
            Recipe recipe = new Recipe(umbrella,"inewrx", itemMatrixList, results);
            IManager.registerRecipe(recipe);
        }
    }

    private static void fastMaterial(String result, List<ItemStack> itemMatrixList, ItemStack itemMatrix, HashMap<String, ItemBuild> itemList) {
        itemMatrixList.set(3, itemMatrix);
        itemMatrixList.set(6, itemMatrix);
        itemMatrixList.set(9, itemMatrix);
        List<ItemStack> results = Collections.singletonList(itemList.get(result).getItem());
        Recipe recipe = new Recipe(result,"inewrx", itemMatrixList, results);
        IManager.registerRecipe(recipe);
    }
}
