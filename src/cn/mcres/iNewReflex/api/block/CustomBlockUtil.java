package cn.mcres.iNewReflex.api.block;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.load.checkPlugin.BlockStoreLib;
import cn.mcres.iNewReflex.utils.NBTEditor;
import cn.mcres.iNewReflex.utils.ReturnMaterial;
import net.sothatsit.blockstore.BlockStoreApi;
import org.bukkit.block.Block;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomBlockUtil {
    /**
     * 替换拓展方块
     * @param multipleFacing
     * @param block
     * @param modelId
     * @param blockId
     */
    public static void placeCustomBlock(MultipleFacing multipleFacing, Block block, int modelId, String blockId) {
        saveBlockData(block, blockId); // 这个方法不能放在后面，否则无效
        FastHandle.setBlockFacing(multipleFacing, modelId);
        block.setBlockData(multipleFacing);
    }

    /**
     * 放置拓展方块
     * @param multipleFacing
     * @param newBlock
     * @param modelId
     * @param blockId
     * @param ev
     */
    public static void placeCustomBlock(MultipleFacing multipleFacing, Block newBlock, int modelId, String blockId, PlayerInteractEvent ev) {
        FastHandle.setBlockFacing(multipleFacing, modelId);
        newBlock.setBlockData(multipleFacing);
        saveBlockData(newBlock, blockId); // 这个方法可以放在这里
        ev.setCancelled(true);
    }

    private static void saveBlockData(Block block, String blockId) {
        if (BlockStoreLib.blockStoreEnable) {
            BlockStoreApi.setBlockMeta(block, INewReflex.getMain(), "iNewReflex:block", blockId);
        }
    }

    // 数字是否在范围内
    public static boolean isWithinScope(int min, int x, int max) {
        return  (min <= x && x <max);
    }
}
