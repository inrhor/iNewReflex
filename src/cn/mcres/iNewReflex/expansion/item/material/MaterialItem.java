package cn.mcres.iNewReflex.expansion.item.material;

import cn.mcres.iNewReflex.api.item.MaterialBuild;
import cn.mcres.iNewReflex.fileYaml.item.CreateMaterialYaml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class MaterialItem {
    public static HashMap<String, MaterialBuild> materialItemList = new LinkedHashMap<>();

    public static List<MaterialBuild> materialList = new ArrayList<>();

    private FileConfiguration materialYaml;

    private void addList(String itemId, MaterialBuild materialBuild) {
        materialItemList.put(itemId, materialBuild);
        materialList.add(materialBuild);
    }

    public void loadAll() {
        this.materialYaml = CreateMaterialYaml.materialYaml;

        if (this.materialYaml.getBoolean("material.sunny_doll.enable")) {
            sunnyDoll();
        }

        if (this.materialYaml.getBoolean("material.crystal_blue.enable")) {
            crystalBlue();
        }
        if (this.materialYaml.getBoolean("material.crystal_green.enable")) {
            crystalGreen();
        }
        if (this.materialYaml.getBoolean("material.crystal_pink.enable")) {
            crystalPink();
        }
        if (this.materialYaml.getBoolean("material.crystal_red.enable")) {
            crystalRed();
        }
        if (this.materialYaml.getBoolean("material.crystal_white.enable")) {
            crystalWhite();
        }
        if (this.materialYaml.getBoolean("material.crystal_yellow.enable")) {
            crystalYellow();
        }
    }

    private void sunnyDoll() {
        String itemId = "sunny_doll";
        MaterialBuild materialBuild = new MaterialBuild(this.materialYaml, itemId,
                Material.SUGAR,12001,
                "§5§9§9§7§a§d§3§1",
                "material");
        materialBuild.createItem();
        addList(itemId, materialBuild);
    }

    private void crystalBlue() {
        String itemId = "crystal_blue";
        MaterialBuild materialBuild = new MaterialBuild(this.materialYaml, itemId,
                Material.SUGAR,12002,
                "§5§9§9§7§a§d§3§2",
                "material");
        materialBuild.createItem();
        addList(itemId, materialBuild);
    }

    private void crystalGreen() {
        String itemId = "crystal_green";
        MaterialBuild materialBuild = new MaterialBuild(this.materialYaml, itemId,
                Material.SUGAR,12003,
                "§5§9§9§7§a§d§3§3",
                "material");
        materialBuild.createItem();
        addList(itemId, materialBuild);
    }

    private void crystalPink() {
        String itemId = "crystal_pink";
        MaterialBuild materialBuild = new MaterialBuild(this.materialYaml, itemId,
                Material.SUGAR,12004,
                "§5§9§9§7§a§d§3§4",
                "material");
        materialBuild.createItem();
        addList(itemId, materialBuild);
    }

    private void crystalRed() {
        String itemId = "crystal_red";
        MaterialBuild materialBuild = new MaterialBuild(this.materialYaml, itemId,
                Material.SUGAR,12005,
                "§5§9§9§7§a§d§3§5",
                "material");
        materialBuild.createItem();
        addList(itemId, materialBuild);
    }

    private void crystalWhite() {
        String itemId = "crystal_white";
        MaterialBuild materialBuild = new MaterialBuild(this.materialYaml, itemId,
                Material.SUGAR,12006,
                "§5§9§9§7§a§d§3§6",
                "material");
        materialBuild.createItem();
        addList(itemId, materialBuild);
    }

    private void crystalYellow() {
        String itemId = "crystal_yellow";
        MaterialBuild materialBuild = new MaterialBuild(this.materialYaml, itemId,
                Material.SUGAR,12007,
                "§5§9§9§7§a§d§3§7",
                "material");
        materialBuild.createItem();
        addList(itemId, materialBuild);
    }
}
