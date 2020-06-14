package cn.mcres.iNewReflex.expansion.recipe;

import cn.mcres.iCraftNew.api.IManager;
import cn.mcres.iCraftNew.model.Recipe;
import cn.mcres.iNewReflex.api.block.BlockBuild;
import cn.mcres.iNewReflex.api.item.MaterialBuild;
import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import cn.mcres.iNewReflex.expansion.item.material.MaterialItem;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class BlockRecipe {
    public static void fastBlockRecipe(String blockId, String mapId) {
        HashMap<String, BlockBuild> blockBuildMap = BlocksBuffer.blockBuildMap;
        HashMap<String, MaterialBuild> materialItemList = MaterialItem.materialItemList;
        if (blockBuildMap.get(blockId) != null) {
            if (materialItemList.get(mapId) != null) {
                List<ItemStack> itemMatrixList = ReturnMatrix.setMatrix(materialItemList.get(mapId).getItem());
                List<ItemStack> results = Collections.singletonList(blockBuildMap.get(blockId).getItem());
                Recipe recipe = new Recipe(blockId,"inewrx", itemMatrixList, results);
                IManager.registerRecipe(recipe);
            }
        }
    }

    public void run() {
        String block = "crystal_block_";
        String material = "crystal_";
        fastBlockRecipe(block+"blue", material+"blue");
        fastBlockRecipe(block+"green", material+"green");
        fastBlockRecipe(block+"pink", material+"pink");
        fastBlockRecipe(block+"red", material+"red");
        fastBlockRecipe(block+"white", material+"white");
        fastBlockRecipe(block+"yellow", material+"yellow");
    }
}
