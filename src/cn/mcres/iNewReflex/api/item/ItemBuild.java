package cn.mcres.iNewReflex.api.item;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.LoreReplace;
import cn.mcres.iNewReflex.utils.NBTEditor;
import cn.mcres.iNewReflex.utils.Nbt;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemBuild {
    private ItemStack itemStack;

    private FileConfiguration yaml;
    private String itemId;
    private String type;
    private int customModelData;
    private String displayName;
    private List<String> lore;
    private List<String> wiki;
    private Material material;
    private double attackDamage;
    private double attackSpeed;
    private int durability;
    private int nowDurability;
    private int breakZeroValue;
    private int breakOneValue;
    private int breakTwoValue;
    private int breakThreeValue;
    private boolean canBreakCustomBlock;
    private boolean canAddBreakVanillaBlock;
    private int breakVanillaValue;
    private boolean cropHarvestEnable;
    private int cropHarvestAdd;
    private List<String> cropHarvestCost;

    public ItemBuild(FileConfiguration yaml, String itemId, Material material, int customModelData, String optifine, String type) {
        this.yaml = yaml;
        this.type = type;

        this.itemId = itemId;

        this.customModelData = customModelData;

        this.displayName = this.yaml.getString(type+"."+itemId+".displayName").replaceAll("&", "§");
        this.attackDamage = this.yaml.getDouble(type+"."+itemId+".attackDamage");
        if (this.yaml.getBoolean(type+"."+itemId+".durability.enable")) {
            this.durability = this.yaml.getInt(type+"."+itemId+".durability.value");
            this.nowDurability = this.durability;
        }

        // 属性
        String yamlGetSpeed = type+"."+itemId+".attackSpeed";
        if (this.yaml.contains(yamlGetSpeed)) {
            this.attackSpeed = this.yaml.getDouble(yamlGetSpeed);
        }

        List<String> lore = this.yaml.getStringList(type+"."+itemId+".lore");
        List<String> wiki = this.yaml.getStringList(type+"."+itemId+".wiki");

        this.lore = LoreReplace.lore(this, lore, optifine);
        this.wiki = LoreReplace.lore(wiki);

        // 附加块破坏力量
        this.canBreakCustomBlock = this.yaml.getBoolean(type+"."+itemId+".breakBlock.customBlock.enable");
        if (this.canBreakCustomBlock) {
            this.breakZeroValue = this.yaml.getInt(type+"."+itemId+".breakBlock.customBlock.zeroValue");
            this.breakOneValue= this.yaml.getInt(type+"."+itemId+".breakBlock.customBlock.oneValue");
            this.breakTwoValue = this.yaml.getInt(type+"."+itemId+".breakBlock.customBlock.twoValue");
            this.breakThreeValue = this.yaml.getInt(type+"."+itemId+".breakBlock.customBlock.threeValue");
        }

        this.canAddBreakVanillaBlock = this.yaml.getBoolean(type+"."+itemId+".breakBlock.vanillaBlock.enable");
        if (this.canAddBreakVanillaBlock) {
            this.breakVanillaValue = this.yaml.getInt(type+"."+itemId+".breakBlock.vanillaBlock.value");
        }

        this.cropHarvestEnable = this.yaml.getBoolean(type+"."+itemId+".crop.harvest.enable");
        if (this.cropHarvestEnable) {
            this.cropHarvestAdd = this.yaml.getInt(type+"."+itemId+".crop.harvest.add");
            String cost = type+"."+itemId+".crop.harvest.cost";
            if (this.yaml.contains(cost)) {
                this.cropHarvestCost = this.yaml.getStringList(cost);
            }else {
                this.cropHarvestCost = new ArrayList<>();
            }
        }

        this.material = material;
    }

    public void createItem() {
        ItemStack itemStack = new ItemStack(material);

        // 属性
        itemStack = Nbt.addAttributeMainHand(itemStack, this.attackSpeed, this.attackDamage-1);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.displayName);
        if (!INewReflex.getInfo().isOldVersion()) {
            itemMeta.setCustomModelData(this.customModelData);
        }
        itemMeta.setDisplayName(this.displayName);
        if (!this.lore.isEmpty()) {
            itemMeta.setLore(this.lore);
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        this.itemStack = itemStack;

        // 实现耐久性
        if (this.yaml.getBoolean(this.type+"."+itemId+".durability.enable")) {
            if (this.durability > 0) {
                itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_durability", this.durability);
                itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_maxDurability", this.durability);
            }
        }else {
            if (INewReflex.getInfo().isNoFlagVersion()) {
                itemStack = NBTEditor.set( itemStack, ( byte ) 1, "Unbreakable" );
            }else {
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
            }
        }

        if (this.canBreakCustomBlock) {
                itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_customBlock_0", this.getBreakZeroValue());
                itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_customBlock_1", this.getBreakOneValue());
                itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_customBlock_2", this.getBreakTwoValue());
                itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_customBlock_3", this.getBreakThreeValue());
                itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_customBlock_can", "can");
        }

        if (this.canAddBreakVanillaBlock) {
            itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_vanillaBlock_canAdd", this.breakVanillaValue);
        }

        if (this.cropHarvestEnable) {
            itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_item_cropHarvest_Add", this.cropHarvestAdd);
        }

        this.itemStack = itemStack;

    }

    public ItemStack getItem() {
        return this.itemStack;
    }

    public void setCustomModelData(int customModelData) {
        this.customModelData = customModelData;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public int getDurability() {
        return durability;
    }

    public int getNowDurability() {
        return nowDurability;
    }

    public int getBreakZeroValue() {
        return breakZeroValue;
    }

    public int getBreakOneValue() {
        return breakOneValue;
    }

    public int getBreakTwoValue() {
        return breakTwoValue;
    }

    public int getBreakThreeValue() {
        return breakThreeValue;
    }

    public String getItemId() {
        return itemId;
    }

    public List<String> getWiki() {
        return wiki;
    }

    public int getCropHarvestAdd() {
        return cropHarvestAdd;
    }
}
