package cn.mcres.iNewReflex.api.block;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.LoreReplace;
import cn.mcres.iNewReflex.utils.Nbt;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import static cn.mcres.iNewReflex.fileYaml.block.CreateBlocksYaml.blocksYaml;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class BlockBuild {
    private String blockId;
    private int modelId;
    private int customModelData;
    private String displayName;
    private List<String> lore;
    private Material material;
    private Boolean canBreak;
    private List<String> breakDrop;
    private int breakStrength;
    private String breakType;
    private boolean spawnEnable;
    private int spawnAmount;
    private List<Material> spawnType;
    private List<String> wiki;
    private String blockUrl;

    public BlockBuild(String blockId, int modelId, int customModelData, String displayName, List<String> lore, Material material) {
        this.blockId = blockId;
        this.modelId = modelId;
        this.customModelData = customModelData;
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
        this.blockUrl = blocksYaml.getString("blocks."+this.blockId+".headUrl");
        this.canBreak = blocksYaml.getBoolean("blocks."+this.blockId+".break.enable");
        this.breakStrength = blocksYaml.getInt("blocks."+this.blockId+".break.strength");
        this.breakType = blocksYaml.getString("blocks."+this.blockId+".break.type");

        List<String> wiki = blocksYaml.getStringList("blocks."+this.blockId+".wiki");
        this.wiki = LoreReplace.lore(wiki);

        if (this.canBreak) {
            this.breakDrop = blocksYaml.getStringList("blocks."+ this.blockId+".break.drop");
        }

        this.spawnEnable = blocksYaml.getBoolean("blocks."+this.blockId+".spawn.enable");
        this.spawnAmount = blocksYaml.getInt("blocks."+this.blockId+".spawn.amount");
        List<Material> materialTypeList = new ArrayList<>();
        for (String materialType : blocksYaml.getStringList("blocks."+this.blockId+".spawn.type")) {
            materialTypeList.add(ReturnMaterial.getMaterial(materialType));
        }
        this.spawnType = materialTypeList;
    }

    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(this.material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.displayName.replaceAll("&", "§"));
        List<String> newLore = new ArrayList<>();
        for (String s : this.lore) {
            newLore.add(s.replaceAll("&", "§"));
        }
        itemMeta.setLore(newLore);

        if (!INewReflex.getInfo().isOldVersion()) {
            itemMeta.setCustomModelData(this.customModelData);
        }

        itemStack.setItemMeta(itemMeta);

        itemStack = Nbt.addItemMetadata(itemStack, "blockId", this.blockId);
        /*if (!INewReflex.getInfo().isOldVersion()) {
            itemMeta.setCustomModelData(this.customModelData);
            NamespacedKey key = new NamespacedKey(INewReflex.getMain(), "blockId");
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, this.blockId);
        }*/
        return itemStack;
    }

    public String getBlockId() {
        return blockId;
    }

    public int getModelId() {
        return modelId;
    }

    public Boolean getCanBreak() {
        return canBreak;
    }

    public int getBreakStrength() {
        return breakStrength;
    }

    public String getBreakType() {
        return breakType;
    }

    public boolean isSpawnEnable() {
        return spawnEnable;
    }

    public int getSpawnAmount() {
        return spawnAmount;
    }

    public List<Material> getSpawnType() {
        return spawnType;
    }

    public List<String> getBreakDrop() {
        return breakDrop;
    }

    public List<String> getWiki() {
        return wiki;
    }

    public String getBlockUrl() {
        return blockUrl;
    }
}
