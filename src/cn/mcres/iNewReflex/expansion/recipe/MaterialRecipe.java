package cn.mcres.iNewReflex.expansion.recipe;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.Recipe;
import cn.mcres.iNewReflex.expansion.item.material.MaterialItem;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import java.util.Collections;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialRecipe {
    public void run() {
        String sunnyDoll = "sunny_doll";
        if (MaterialItem.materialItemList.get(sunnyDoll) != null) {
            List<ItemStack> itemMatrixList = ReturnMatrix.getEmptyMatrix();
            itemMatrixList.set(6, new ItemStack(Material.SUGAR, 32));
            Material lead = ReturnMaterial.getMaterial("LEAD");
            itemMatrixList.set(5, new ItemStack(/*ReturnMaterial.lead()*/lead, 3));
            itemMatrixList.set(10, new ItemStack(/*ReturnMaterial.lead()*/lead, 3));
            itemMatrixList.set(9, new ItemStack(Material.SUGAR, 32));
            List<ItemStack> results = Collections.singletonList(MaterialItem.materialItemList.get(sunnyDoll).getItem());
            Recipe recipe = new Recipe(sunnyDoll,"inewrx", itemMatrixList, results);
            IManager.registerRecipe(recipe);
        }
    }
}
