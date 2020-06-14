package cn.mcres.iNewReflex.expansion.recipe;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ReturnMatrix {
    public static List<ItemStack> getEmptyMatrix() {
        List<ItemStack> itemMatrix = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            itemMatrix.add(new ItemStack(Material.AIR));
        }
        return itemMatrix;
    }

    public static List<ItemStack> setMatrix(ItemStack itemStack) {
        List<ItemStack> itemMatrix = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            itemMatrix.add(itemStack);
        }
        return itemMatrix;
    }
}
