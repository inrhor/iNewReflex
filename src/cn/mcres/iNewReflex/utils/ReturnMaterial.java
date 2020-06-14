package cn.mcres.iNewReflex.utils;

import java.util.ArrayList;
import java.util.List;
import me.randomHashTags.UMaterial.UMaterial;
import org.bukkit.Material;

public class ReturnMaterial {
    public static Material getMaterial(String material) {
        return UMaterial.valueOf(material.toUpperCase()).getMaterial();
    }

    public static List<Material> getMaterial(List<String> material) {
        List<Material> newList = new ArrayList<>();
        for (String s : material) {
            Material type = UMaterial.valueOf(s.toUpperCase()).getMaterial();
            newList.add(type);
        }
        return newList;
    }
}
