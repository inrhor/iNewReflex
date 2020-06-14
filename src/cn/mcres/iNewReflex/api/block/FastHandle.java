package cn.mcres.iNewReflex.api.block;

import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;


public class FastHandle {
    /**
     * 设置方块纹理
     * @param blockData
     * @param id
     */
    public static void setBlockFacing(MultipleFacing blockData, int id) {
        final BlockFace[] blockFaces = new BlockFace[]{BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.DOWN, BlockFace.UP};
        for (int i = 0; i < blockFaces.length; i++) {
//            System.out.println(i+"  "+((id & 0x1 << i) != 0));
            blockData.setFace(blockFaces[i], (id & 0x1 << i) != 0);
        }
    }

    /**
     * 设置方块纹理所有面同一值
     * @param blockData
     * @param all
     */
    public static void setBlockFacing(MultipleFacing blockData, boolean all) {
        final BlockFace[] properties = new BlockFace[]{BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.DOWN, BlockFace.UP};
        for (BlockFace property : properties) {
            blockData.setFace(property, all);
        }
    }
}
