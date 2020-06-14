package cn.mcres.iNewReflex.listener;

import cn.mcres.iNewReflex.utils.LoreReplace;
import cn.mcres.iNewReflex.utils.MathTool;
import cn.mcres.iNewReflex.utils.Nbt;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemDurability implements Listener {
    /*@EventHandler
    void test(PlayerMoveEvent ev) {
        System.out.println(ev.getPlayer().getEquipment().getItemInMainHand().getType());
    }*/

    // 耐久性系统
    @EventHandler
    void itemDurability(PlayerItemDamageEvent ev) {
        ItemStack itemStack = ev.getItem();
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta!=null && Nbt.hasItemMetadataInt(itemStack, "iNewReflex_item_durability")) {
                ev.setCancelled(true); // 防止物品损坏
                int durability = Nbt.getItemMetadataInt(itemStack, "iNewReflex_item_durability")-1;
                int maxDurability = Nbt.getItemMetadataInt(itemStack, "iNewReflex_item_maxDurability");
                itemMeta.setLore(LoreReplace.loreDurability(itemMeta.getLore(), durability, maxDurability, true));
                itemStack.setItemMeta(itemMeta);
                itemMeta = Nbt.addItemMetaMetadata(itemStack, "iNewReflex_item_durability", MathTool.rounding(durability));
                itemStack.setItemMeta(itemMeta);
                if (durability <= 0) {
                    itemStack.setAmount(itemStack.getAmount() - 1);
                }
            }
        }
    }
}
