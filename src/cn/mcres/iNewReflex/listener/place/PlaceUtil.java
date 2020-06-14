package cn.mcres.iNewReflex.listener.place;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.api.block.FastHandle;
import cn.mcres.iNewReflex.load.checkPlugin.BlockStoreLib;
import cn.mcres.iNewReflex.utils.NBTEditor;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import net.sothatsit.blockstore.BlockStoreApi;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlaceUtil {
    // 删除物品
    public static void takeItem(Player player, ItemStack itemStack) {
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        }
    }

    public static void placeCustomSkullBlock(Block block, String blockId, String url, PlayerInteractEvent ev) {
        block.setType(ReturnMaterial.getMaterial("PLAYER_HEAD"));
        NBTEditor.setSkullTexture(block, url);
        saveBlockData(block, blockId);
        ev.setCancelled(true);
    }

    public static void placeCustomSkullBlock(Block block, String blockId, String url) {
        saveBlockData(block, blockId); // 这个方法不能放在后面，否则无效
        block.setType(ReturnMaterial.getMaterial("PLAYER_HEAD"));
        NBTEditor.setSkullTexture(block, url);
    }

    private static void saveBlockData(Block block, String blockId) {
        if (BlockStoreLib.blockStoreEnable) {
            BlockStoreApi.setBlockMeta(block, INewReflex.getMain(), "iNewReflex:block", blockId);
        }
    }
}
