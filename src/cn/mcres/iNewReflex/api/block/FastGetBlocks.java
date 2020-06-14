package cn.mcres.iNewReflex.api.block;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.utils.Nbt;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class FastGetBlocks {
    public static boolean hasCustomBlock(ItemStack itemStack) {
        /*NamespacedKey key = new NamespacedKey(INewReflex.getMain(), "blockId");
        return itemMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING);*/
        return Nbt.hasItemMetadataString(itemStack, "blockId");
    }

    public static boolean isCustomBlock(ItemStack itemStack, String blockId) {
        String key = Nbt.getItemMetadataString(itemStack, "blockId");
        return key.equals(blockId);
        /*NamespacedKey key = new NamespacedKey(INewReflex.getMain(), "blockId");
        if (hasCustomBlock(itemMeta)) {
            return itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING).equals(blockId);
        }
        return false;*/
    }

    public static boolean isMushRoom(Block block) {
        return (block.getType().equals(Material.MUSHROOM_STEM) || block.getType().equals(Material.BROWN_MUSHROOM_BLOCK) || block.getType().equals(Material.RED_MUSHROOM_BLOCK));
    }

    public static boolean isMushRoom(Material material) {
        return (material.equals(Material.MUSHROOM_STEM) || material.equals(Material.BROWN_MUSHROOM_BLOCK) || material.equals(Material.RED_MUSHROOM_BLOCK));
    }
}
