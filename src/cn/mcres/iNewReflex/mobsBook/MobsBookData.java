package cn.mcres.iNewReflex.mobsBook;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.LoreReplace;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MobsBookData {
    private String modelId, mobName, itemName;
    private List<String> script, itemLore;
    private int customModelData;
    private ItemStack itemStack;

    public MobsBookData(String modelId, FileConfiguration mobsBookYaml) {
        this.modelId = modelId;
        String section = "mobsBook.list."+modelId+".";
        this.mobName = mobsBookYaml.getString(section+"name").replaceAll("&", "§");;
        this.itemName = mobsBookYaml.getString(section+"itemName").replaceAll("&", "§");;
        this.itemLore = LoreReplace.lore(mobsBookYaml.getStringList(section+"itemLore"));
        this.customModelData = mobsBookYaml.getInt(section+"customModelData");
        this.script = mobsBookYaml.getStringList(section+"script");

        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
        if (!INewReflex.getInfo().isOldVersion()) {
            itemMeta.setCustomModelData(customModelData);
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);
        this.itemStack = item;
    }

    public String getModelId() {
        return modelId;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public List<String> getScript() {
        return script;
    }

    public String getItemName() {
        return itemName;
    }

    public String getMobName() {
        return mobName;
    }

    public ItemStack getItem() {
        return itemStack;
    }

    public List<String> getItemLore() {
        return itemLore;
    }
}
