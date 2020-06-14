package cn.mcres.iNewReflex.api.block;


import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import cn.mcres.iNewReflex.expansion.item.food.FoodItem;
import cn.mcres.iNewReflex.expansion.item.material.MaterialItem;
import cn.mcres.iNewReflex.expansion.item.tool.ToolItem;
import cn.mcres.iNewReflex.expansion.item.weapon.MeleeItem;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BlockDropUtil {
    public static void dropItem(BlockBuild blockBuild, Location location, Player player) {
        if (blockBuild.getCanBreak()) {
            for (String dropString : blockBuild.getBreakDrop()) {
                dropList(dropString, location, player);
            }
        }
    }

    public static void dropList(String dropString, Location location, Player player) {
        String[] result = dropString.split(":");
        String type = result[0];
        if (result.length == 4) {
            String itemId = result[1];
            int amount = Integer.parseInt(result[2]);
            double chance = Double.parseDouble(result[3]);
            if (type.equals("block")) {
                if (ThreadLocalRandom.current().nextDouble() < chance) {
                    while (amount-- > 0) {
                        location.getWorld().dropItem(location, BlocksBuffer.blockBuildMap.get(itemId).getItem());
                    }
                }
            }
            if (type.equals("weapon")) {
                if (ThreadLocalRandom.current().nextDouble() < chance) {
                    while (amount-- > 0) {
                        location.getWorld().dropItem(location, MeleeItem.meleeItemList.get(itemId).getItem());
                    }
                }
            }
            if (type.equals("tool")) {
                if (ThreadLocalRandom.current().nextDouble() < chance) {
                    while (amount-- > 0) {
                        location.getWorld().dropItem(location, ToolItem.toolItemList.get(itemId).getItem());
                    }
                }
            }
            if (type.equals("material")) {
                if (ThreadLocalRandom.current().nextDouble() < chance) {
                    while (amount-- > 0) {
                        location.getWorld().dropItem(location, MaterialItem.materialItemList.get(itemId).getItem());
                    }
                }
            }
            if (type.equals("food")) {
                if (ThreadLocalRandom.current().nextDouble() < chance) {
                    while (amount-- > 0) {
                        location.getWorld().dropItem(location, FoodItem.foodItemList.get(itemId).getItem());
                    }
                }
            }
        }
        if (type.equals("exp")) {
            double dropChance = Double.parseDouble(result[2]);
            if (ThreadLocalRandom.current().nextDouble() < dropChance) {
                int dropAmount = Integer.parseInt(result[1]);
                player.giveExp(dropAmount);
            }
        }
    }
}
