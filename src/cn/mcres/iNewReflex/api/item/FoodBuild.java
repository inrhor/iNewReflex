package cn.mcres.iNewReflex.api.item;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.load.checkPlugin.HDLib;
import cn.mcres.iNewReflex.load.checkPlugin.TrHDLib;
import cn.mcres.iNewReflex.utils.LoreReplace;
import cn.mcres.iNewReflex.utils.Nbt;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FoodBuild {
    private ItemStack itemStack;

    private FileConfiguration yaml;
    private String itemId;
    private String type;
    private int customModelData;
    private String displayName;
    private List<String> lore;
    private List<String> wiki;
    private Material material;
    private int addFood;

    private String cropUrl;
    private boolean cropCanPlace;
    private List<Material> cropMaterialList;
    private int cropGrowthAddValue;
    private List<String> cropRewards;
    private String cropFace;
    private int growthRipe;
    private int harvestMaxValue;

    /*private List<Material> cropGetFromList;
    private double cropGetFromChance;
    private int cropGetFromAmount;*/

    private String cropShowUse;
    private double cropShowX;
    private double cropShowY;
    private double cropShowZ;
    private List<String> cropShowTextList;
    private int cropShowTime;

    public FoodBuild(FileConfiguration yaml, String itemId, Material material, int customModelData, String optifine, String type) {
        this.yaml = yaml;
        this.type = type;

        this.itemId = itemId;

        this.customModelData = customModelData;

        this.displayName = this.yaml.getString(type+"."+itemId+".displayName").replaceAll("&", "§");

        List<String> lore = this.yaml.getStringList(type+"."+itemId+".lore");
        List<String> wiki = this.yaml.getStringList(type+"."+itemId+".wiki");
        this.wiki = LoreReplace.lore(wiki);
        this.addFood = this.yaml.getInt(type+"."+itemId+".addFood");

        this.material = material;

        this.lore = LoreReplace.lore(this, lore, optifine);

        this.cropCanPlace = this.yaml.getBoolean(type+"."+itemId+".crop.canPlace");
        this.cropGrowthAddValue = this.yaml.getInt(type+"."+itemId+".crop.growthAddValue");
        this.cropRewards = this.yaml.getStringList(type+"."+itemId+".crop.rewards");
        this.cropMaterialList = ReturnMaterial.getMaterial(this.yaml.getStringList(type+"."+itemId+".crop.material"));
        this.cropUrl = this.yaml.getString(type+"."+itemId+".crop.headUrl");;
        this.cropFace = this.yaml.getString(type+"."+itemId+".crop.face");
        this.growthRipe = this.yaml.getInt(type+"."+itemId+".crop.growthRipe");
        this.harvestMaxValue = this.yaml.getInt(type+"."+itemId+".crop.harvest.maxValue");

        /*this.cropGetFromList = ReturnMaterial.getMaterial(this.yaml.getStringList(type+"."+itemId+".crop.getFrom.material"));
        this.cropGetFromChance = this.yaml.getDouble(type+"."+itemId+".crop.getFrom.chance");
        this.cropGetFromAmount = this.yaml.getInt(type+"."+itemId+".crop.getFrom.amount");
*/
        cropShowUse = this.yaml.getString(type+"."+itemId+".crop.show.use");
        if (cropShowUse.equals("hd")) {
            if (!HDLib.HDLibEnable) {
                cropShowUse = "tr";
            }
        }
        if (cropShowUse.equals("tr")) {
            if (!TrHDLib.TrHDLibEnable) {
                cropShowUse = "null";
            }
        }
        cropShowX = this.yaml.getDouble(type+"."+itemId+".crop.show."+cropShowUse+".x");
        cropShowY = this.yaml.getDouble(type+"."+itemId+".crop.show."+cropShowUse+".y");
        cropShowZ = this.yaml.getDouble(type+"."+itemId+".crop.show."+cropShowUse+".z");
        cropShowTextList = this.yaml.getStringList(type+"."+itemId+".crop.show."+cropShowUse+".textList");
        cropShowTime = this.yaml.getInt(type+"."+itemId+".crop.show.time")*20;
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

        itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_food_addFood", this.addFood);

        itemStack = Nbt.addItemMetadata(itemStack, "iNewReflex_food_id", this.itemId);

        this.itemStack = itemStack;
    }

    public int getGrowthRipe() {
        return growthRipe;
    }

    public int getCropShowTime() {
        return cropShowTime;
    }

    public int getHarvestMaxValue() {
        return harvestMaxValue;
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

    public int getAddFood() {
        return addFood;
    }

    public void setAddFood(int addFood) {
        this.addFood = addFood;
    }

    public int getCropGrowthAddValue() {
        return cropGrowthAddValue;
    }

    public boolean isCropCanPlace() {
        return cropCanPlace;
    }

    public String getCropUrl() {
        return cropUrl;
    }

    public List<Material> getCropMaterialList() {
        return cropMaterialList;
    }

    public List<String> getCropRewards() {
        return cropRewards;
    }

    public String getCropFace() {
        return cropFace;
    }

    public double getCropShowX() {
        return cropShowX;
    }

    public double getCropShowY() {
        return cropShowY;
    }

    public double getCropShowZ() {
        return cropShowZ;
    }

    public String getCropShowUse() {
        return cropShowUse;
    }

    public List<String> getCropShowTextList() {
        return cropShowTextList;
    }
}
