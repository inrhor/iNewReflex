package cn.mcres.iNewReflex.expansion.recipe;

import cn.mcres.iNewReflex.expansion.item.tool.ToolItem;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import org.bukkit.Material;

public class ToolRecipe {
    public void run() {
        Material diamondShovel = ReturnMaterial.getMaterial("DIAMOND_SHOVEL");

        RecipeUtil.fastRecipe(ToolItem.toolItemList,"obsidian_hoe", Material.DIAMOND_HOE, Material.NETHER_STAR);
        RecipeUtil.fastRecipe(ToolItem.toolItemList,"obsidian_pickaxe", Material.DIAMOND_PICKAXE, Material.NETHER_STAR);
        RecipeUtil.fastRecipe(ToolItem.toolItemList,"obsidian_shovel", /*ReturnMaterial.diamondShovel()*/diamondShovel, Material.NETHER_STAR);
        RecipeUtil.fastRecipe(ToolItem.toolItemList,"obsidian_axe", Material.DIAMOND_AXE, Material.NETHER_STAR);

        RecipeUtil.fastRecipe(ToolItem.toolItemList,"emerald_hoe", Material.IRON_HOE, Material.EMERALD);
        RecipeUtil.fastRecipe(ToolItem.toolItemList,"emerald_pickaxe", Material.IRON_PICKAXE, Material.EMERALD);
        RecipeUtil.fastRecipe(ToolItem.toolItemList,"emerald_shovel", /*ReturnMaterial.diamondShovel()*/diamondShovel, Material.EMERALD);
        RecipeUtil.fastRecipe(ToolItem.toolItemList,"emerald_axe", Material.IRON_AXE, Material.EMERALD);
    }
}
