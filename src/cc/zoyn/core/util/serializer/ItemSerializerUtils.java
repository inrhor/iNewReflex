package cc.zoyn.core.util.serializer;

import cc.zoyn.core.util.nms.NMSUtils;
import cn.mcres.iCraftNew.util.LogUtil;
import cn.mcres.iNewReflex.INewReflex;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 物品序列化工具类
 *
 * @author Zoyn | Dolphin
 * @version 1.2 更新 2019/11/3 更新内容: 修复兼容1.14的问题
 * @since 2017/?/?
 */
public final class ItemSerializerUtils {

    private static Method WRITE_NBT;
    private static Method READ_NBT;

    // Prevent accidental construction
    private ItemSerializerUtils() {
    }

    /**
     * 将物品序列化为Base64数据
     *
     * @param items 物品
     * @return 物品的Base64数据
     */
    public static String toBase64(List<ItemStack> items) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
        Object localNBTTagList = null;
        try {
            localNBTTagList = NMSUtils.getNMSClass("NBTTagList").getConstructor().newInstance();
        } catch (Exception e) {
            LogUtil.send("错误-Error: " + e.getMessage());
        }
        try {
            for (ItemStack item : items) {
                // CraftBukkit对象
                Object localCraftItemStack = Class
                        .forName("org.bukkit.craftbukkit." + NMSUtils.getVersion() + ".inventory.CraftItemStack")
                        .getMethod("asCraftCopy", ItemStack.class).invoke(item, item);
                // nbt
                Object localNBTTagCompound = NMSUtils.getNMSClass("NBTTagCompound").getConstructor().newInstance();
                if (localCraftItemStack != null) {
                    try {
                        Object nmsItem = NMSUtils.getNMSItem(item);
                        nmsItem.getClass().getMethod("save", NMSUtils.getNMSClass("NBTTagCompound")).invoke(nmsItem,
                                localNBTTagCompound);
                    } catch (NullPointerException localNullPointerException) {
                        LogUtil.send("错误-Error: " + localNullPointerException.getMessage());
                    }
                }
                String version = NMSUtils.getVersion();
                int subVersion = Integer.parseInt(version.split("_")[1]);
                /*
                 * 1.14 NbtTagList add 方法添加 index 参数
                 */
                if (subVersion >= 14)
                {
                    localNBTTagList.getClass().getMethod("add", int.class, NMSUtils.getNMSClass("NBTBase")).invoke(localNBTTagList,
                            0, localNBTTagCompound);
                }
                else
                {
                    localNBTTagList.getClass().getMethod("add", NMSUtils.getNMSClass("NBTBase")).invoke(localNBTTagList,
                            localNBTTagCompound);
                }
            }
        } catch (Exception e) {
            LogUtil.send("错误-Error: " + e.getMessage());
        }

        if (WRITE_NBT == null) {
            try {
                WRITE_NBT = NMSUtils.getNMSClass("NBTCompressedStreamTools").getDeclaredMethod("a",
                        NMSUtils.getNMSClass("NBTBase"), DataOutput.class);
                WRITE_NBT.setAccessible(true);
            } catch (Exception localException1) {
                throw new IllegalStateException("未找到写入方法", localException1);
            }
        }
        try {
            WRITE_NBT.invoke(null, localNBTTagList, localDataOutputStream);
        } catch (Exception localException2) {
            throw new IllegalArgumentException("无法写入" + localNBTTagList + "至" + localDataOutputStream, localException2);
        }
        return Base64Coder.encodeLines(localByteArrayOutputStream.toByteArray());
    }

    /**
     * 将Base64数据反序列化为物品
     *
     * @param paramString Base64数据
     * @return 物品数组
     */
    public static List<ItemStack> fromBase64(String paramString) {
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(paramString));
        Object localNBTTagList = readNbt(new DataInputStream(localByteArrayInputStream)); // NBTTagList
        ItemStack[] arrayOfItemStack;
        try {
            arrayOfItemStack = new ItemStack[(int) localNBTTagList.getClass().getMethod("size").invoke(localNBTTagList)];

            for (int i = 0; i < arrayOfItemStack.length; i++) {
                Object localNBTTagCompound = localNBTTagList.getClass().getMethod("get", Integer.TYPE).invoke(localNBTTagList, i);

                //判断NBT是否不为空
                if (!(boolean) localNBTTagCompound.getClass().getMethod("isEmpty").invoke(localNBTTagCompound)) {
                    String version = NMSUtils.getVersion();
                    //子版本 如 v1_10_R1 中的10
                    int subVersion = Integer.parseInt(version.split("_")[1]);
                    /*
                     * 1.11版本及以上删除了createStack方法所以只能使用其构造方法来创建
                     */
                    if (subVersion >= 11) {
                        //构造器
                        Constructor<?> constructor = NMSUtils.getNMSClass("ItemStack").getDeclaredConstructor(NMSUtils.getNMSClass("NBTTagCompound"));
                        constructor.setAccessible(true);
                        Object nmsItem = constructor.newInstance(localNBTTagCompound);
                        arrayOfItemStack[i] = (ItemStack) NMSUtils.getOBCClass("inventory.CraftItemStack").getMethod("asCraftMirror", NMSUtils.getNMSClass("ItemStack")).invoke(nmsItem, nmsItem);
                    } else {
                        arrayOfItemStack[i] = (ItemStack) Class
                                .forName("org.bukkit.craftbukkit." + NMSUtils.getVersion() + ".inventory.CraftItemStack")
                                .getMethod("asCraftMirror", NMSUtils.getNMSClass("ItemStack"))
                                .invoke(localNBTTagCompound, NMSUtils.getNMSClass("ItemStack")
                                        .getMethod("createStack", NMSUtils.getNMSClass("NBTTagCompound"))
                                        .invoke(localNBTTagCompound, localNBTTagCompound));
                    }
                }
            }
            if (!INewReflex.getInfo().isOldVersion()) {
                List<ItemStack> itemsList = new ArrayList<>(Arrays.asList(arrayOfItemStack));
                Collections.reverse(itemsList);
                return itemsList;
            }else {
                return new ArrayList<>(Arrays.asList(arrayOfItemStack));
            }
        } catch (Exception e) {
            LogUtil.send("错误-Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * 读取NBT
     *
     * @param paramDataInput 数据输入流
     */
    private static Object readNbt(DataInput paramDataInput) {
        if (READ_NBT == null) {
            try {
                READ_NBT = NMSUtils.getNMSClass("NBTCompressedStreamTools").getDeclaredMethod("a",
                        DataInput.class, Integer.TYPE, NMSUtils.getNMSClass("NBTReadLimiter"));
                READ_NBT.setAccessible(true);
            } catch (Exception localException1) {
                throw new IllegalStateException("未找到方法.", localException1);
            }
        }
        try {
            Object limiter = NMSUtils.getNMSClass("NBTReadLimiter").getConstructor(Long.TYPE)
                    .newInstance(Long.MAX_VALUE);
            return READ_NBT.invoke(null, paramDataInput, 0, limiter);
        } catch (Exception localException2) {
            throw new IllegalArgumentException("无法从该位置读取数据" + paramDataInput, localException2);
        }
    }
}
