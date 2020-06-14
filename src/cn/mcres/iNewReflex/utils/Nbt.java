package cn.mcres.iNewReflex.utils;

import cn.mcres.iNewReflex.INewReflex;
import java.util.UUID;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Nbt {

    /**
     * 主手物品属性
     * @param itemStack
     * @param attackSpeed
     * @param attackDamage
     * @return
     */
    public static ItemStack addAttributeMainHand(ItemStack itemStack, double attackSpeed, double attackDamage) {
        if (INewReflex.getInfo().isOldVersion()) {
            NBTEditor.NBTCompound compound = NBTEditor.getItemNBTTag( itemStack );

            compound.set( "generic.attackSpeed", "AttributeModifiers", null, "AttributeName" );
            compound.set( "Attack Speed", "AttributeModifiers", 0, "Name" );
            compound.set( "mainhand", "AttributeModifiers", 0, "Slot" );
            compound.set( 1, "AttributeModifiers", 0, "Operation" );
            compound.set( attackSpeed, "AttributeModifiers", 0, "Amount" );
            compound.set( 100, "AttributeModifiers", 0, "UUIDMost" );
            compound.set( 50000, "AttributeModifiers", 0, "UUIDLeast" );

            compound.set( "generic.attackDamage", "AttributeModifiers", null, "AttributeName" );
            compound.set( "Attack Damage", "AttributeModifiers", 1, "Name" );
            compound.set( "mainhand", "AttributeModifiers", 1, "Slot" );
            compound.set( 1, "AttributeModifiers", 1, "Operation" );
            compound.set( attackDamage, "AttributeModifiers", 1, "Amount" );
            compound.set( 100, "AttributeModifiers", 1, "UUIDMost" );
            compound.set( 50000, "AttributeModifiers", 1, "UUIDLeast" );

            itemStack = NBTEditor.getItemFromTag(compound);
            return itemStack;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        UUID uuid = new UUID(80001, 20001);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                new AttributeModifier(uuid, "attackSpeed", attackSpeed, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND));
        UUID uuid2 = new UUID(80002, 2002);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(uuid2,"attackDamage", attackDamage, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HAND));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * 物品元数据
     * @param itemStack
     * @param metadata
     * @param value
     * @return
     */
    public static ItemStack addItemMetadata(ItemStack itemStack, String metadata, int value) {
        if (INewReflex.getInfo().isOldVersion()) {
            itemStack = NBTEditor.set( itemStack, value, metadata, "item", "inewrx" );
            return itemStack;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(INewReflex.getMain(), metadata);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * 物品元数据
     * @param itemStack
     * @param metadata
     * @param value
     * @return
     */
    public static ItemMeta addItemMetaMetadata(ItemStack itemStack, String metadata, int value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (INewReflex.getInfo().isOldVersion()) {
            itemStack = NBTEditor.set( itemStack, value, metadata, "item", "inewrx" );
            return itemStack.getItemMeta();
        }
        NamespacedKey key = new NamespacedKey(INewReflex.getMain(), metadata);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, value);
        return itemMeta;
    }

    /**
     * 物品元数据
     * @param itemStack
     * @param metadata
     * @param value
     * @return
     */
    public static ItemStack addItemMetadata(ItemStack itemStack, String metadata, String value) {
        if (INewReflex.getInfo().isOldVersion()) {
            itemStack = NBTEditor.set( itemStack, value, metadata, "item", "inewrx" );
            return itemStack;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(INewReflex.getMain(), metadata);
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * 物品元数据
     * @param itemStack
     * @param metadata
     * @return
     */
    public static int getItemMetadataInt(ItemStack itemStack, String metadata) {
        if (INewReflex.getInfo().isOldVersion()) {
            return NBTEditor.getInt( itemStack, metadata, "item", "inewrx" );
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(INewReflex.getMain(), metadata);
        return itemMeta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    /**
     * 物品元数据
     * @param itemStack
     * @param metadata
     * @return
     */
    public static boolean hasItemMetadataInt(ItemStack itemStack, String metadata) {
        if (INewReflex.getInfo().isOldVersion()) {
            return NBTEditor.contains( itemStack, metadata, "item", "inewrx" );
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(INewReflex.getMain(), metadata);
        return itemMeta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER);
    }

    /**
     * 物品元数据
     * @param itemStack
     * @param metadata
     * @return
     */
    public static String getItemMetadataString(ItemStack itemStack, String metadata) {
        if (INewReflex.getInfo().isOldVersion()) {
            return NBTEditor.getString( itemStack, metadata, "item", "inewrx" );
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(INewReflex.getMain(), metadata);
        return itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    /**
     * 物品元数据
     * @param itemStack
     * @param metadata
     * @return
     */
    public static boolean hasItemMetadataString(ItemStack itemStack, String metadata) {
        if (INewReflex.getInfo().isOldVersion()) {
            return NBTEditor.contains( itemStack, metadata, "item", "inewrx" );
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(INewReflex.getMain(), metadata);
        return itemMeta.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }
}
