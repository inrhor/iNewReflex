package cn.mcres.iNewReflex.expansion.item.food;

import cn.mcres.iNewReflex.api.item.FoodBuild;
import cn.mcres.iNewReflex.fileYaml.item.CreateFoodYaml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class FoodItem {
    public static HashMap<String, FoodBuild> foodItemList = new LinkedHashMap<>();

    public static List<FoodBuild> foodList = new ArrayList<>();

    private FileConfiguration foodYaml;

    private void addList(String itemId, FoodBuild foodBuild) {
        foodItemList.put(itemId, foodBuild);
        foodList.add(foodBuild);
    }

    public void loadAll() {
        this.foodYaml = CreateFoodYaml.foodYaml;

        if (this.foodYaml.getBoolean("food.red_apple.enable")) {
            redApple();
        }
        if (this.foodYaml.getBoolean("food.blueberry.enable")) {
            blueberry();
        }
        if (this.foodYaml.getBoolean("food.cabbage.enable")) {
            cabbage();
        }
        if (this.foodYaml.getBoolean("food.cherry.enable")) {
            cherry();
        }
        if (this.foodYaml.getBoolean("food.grape_blue.enable")) {
            grapeBlue();
        }
        if (this.foodYaml.getBoolean("food.grape_pink.enable")) {
            grapePink();
        }
        if (this.foodYaml.getBoolean("food.grape_purple.enable")) {
            grapePurple();
        }
        if (this.foodYaml.getBoolean("food.lemon.enable")) {
            lemon();
        }
        if (this.foodYaml.getBoolean("food.mango.enable")) {
            mango();
        }
        if (this.foodYaml.getBoolean("food.mangosteen.enable")) {
            mangosteen();
        }
        if (this.foodYaml.getBoolean("food.orange.enable")) {
            orange();
        }
        if (this.foodYaml.getBoolean("food.peach.enable")) {
            peach();
        }
        if (this.foodYaml.getBoolean("food.pear.enable")) {
            pear();
        }
        if (this.foodYaml.getBoolean("food.pineapple.enable")) {
            pineapple();
        }
        if (this.foodYaml.getBoolean("food.pitaya.enable")) {
            pitaya();
        }
        if (this.foodYaml.getBoolean("food.strawberry.enable")) {
            strawberry();
        }
    }

    private void redApple() {
        String itemId = "red_apple";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13001,
                "§d§r§9§7§a§d§3§1",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void blueberry() {
        String itemId = "blueberry";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13002,
                "§d§r§9§7§a§d§3§2",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void cabbage() {
        String itemId = "cabbage";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13003,
                "§d§r§9§7§a§d§3§3",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void cherry() {
        String itemId = "cherry";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13004,
                "§d§r§9§7§a§d§3§4",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void grapeBlue() {
        String itemId = "grape_blue";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13005,
                "§d§r§9§7§a§d§3§5",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void grapePink() {
        String itemId = "grape_pink";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13006,
                "§d§r§9§7§a§d§3§6",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void grapePurple() {
        String itemId = "grape_purple";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13007,
                "§d§r§9§7§a§d§3§7",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void lemon() {
        String itemId = "lemon";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13008,
                "§d§r§9§7§a§d§3§8",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void mango() {
        String itemId = "mango";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13009,
                "§d§r§9§7§a§d§3§9",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void mangosteen() {
        String itemId = "mangosteen";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13010,
                "§d§r§9§7§a§d§3§r§0",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void orange() {
        String itemId = "orange";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13011,
                "§d§r§9§7§a§d§3§r§1",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void peach() {
        String itemId = "peach";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13012,
                "§d§r§9§7§a§d§3§r§2",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void pear() {
        String itemId = "pear";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13013,
                "§d§r§9§7§a§d§3§r§3",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void pineapple() {
        String itemId = "pineapple";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13014,
                "§d§r§9§7§a§d§3§r§4",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void pitaya() {
        String itemId = "pitaya";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13015,
                "§d§r§9§7§a§d§3§r§5",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }

    private void strawberry() {
        String itemId = "strawberry";
        FoodBuild foodBuild = new FoodBuild(this.foodYaml, itemId,
                Material.APPLE,13016,
                "§d§r§9§7§a§d§3§r§6",
                "food");
        foodBuild.createItem();
        addList(itemId, foodBuild);
    }
}
