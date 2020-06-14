package cn.mcres.iNewReflex.api.item;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.LoreReplace;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MaterialBuild {
    private ItemStack itemStack;

    private FileConfiguration yaml;
    private String itemId;
    private String type;
    private int customModelData;
    private String displayName;
    private List<String> lore;
    private List<String> wiki;
    private Material material;

    public MaterialBuild(FileConfiguration yaml, String itemId, Material material, int customModelData, String optifine, String type) {
        this.yaml = yaml;
        this.type = type;

        this.itemId = itemId;

        this.customModelData = customModelData;

        this.displayName = this.yaml.getString(type+"."+itemId+".displayName").replaceAll("&", "§");

        List<String> lore = this.yaml.getStringList(type+"."+itemId+".lore");
        this.lore = LoreReplace.lore(lore, optifine);
        List<String> wiki = this.yaml.getStringList(type+"."+itemId+".wiki");
        this.wiki = LoreReplace.lore(wiki);

        this.material = material;
    }

    public void createItem() {
        ItemStack itemStack = new ItemStack(material);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.displayName);
        if (!INewReflex.getInfo().isOldVersion()) {
            itemMeta.setCustomModelData(this.customModelData);
        }
        itemMeta.setDisplayName(this.displayName);
        if (!this.lore.isEmpty()) {
            itemMeta.setLore(this.lore);
        }
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;
    }

    public ItemStack getItem() {
        return this.itemStack;
    }

    public List<String> getWiki() {
        return wiki;
    }

    public String getItemId() {
        return itemId;
    }
}
