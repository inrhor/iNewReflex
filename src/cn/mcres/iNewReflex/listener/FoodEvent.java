package cn.mcres.iNewReflex.listener;

import cn.mcres.iNewReflex.utils.Nbt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class FoodEvent implements Listener {
    /*@EventHandler
    void test(PlayerDropItemEvent ev) {
        String url = "https://inrhinrh.coding.net/p/tuchuang/d/tuchuang/git/raw/59c2aea945c8c5bde6fc4db6ce8be2414dfbc055/head_food/apple.png";
        ItemStack skullItem = NBTEditor.getHead( url );
        ev.getPlayer().getInventory().addItem(skullItem);
//        ev.getPlayer().setFoodLevel(ev.getPlayer().getFoodLevel()-2);
    }*/

    @EventHandler
    void eat(PlayerItemConsumeEvent ev) {
        Player player = ev.getPlayer();
        ItemStack itemStack = ev.getItem();
        if (Nbt.hasItemMetadataInt(itemStack, "iNewReflex_food_addFood")) {
            ev.setCancelled(true);
            int playerFood = player.getFoodLevel();
            int addFood = playerFood+Nbt.getItemMetadataInt(itemStack, "iNewReflex_food_addFood");
            player.setFoodLevel(addFood);
            player.getEquipment().getItemInMainHand().setAmount(itemStack.getAmount()-1);
        }
    }
}
