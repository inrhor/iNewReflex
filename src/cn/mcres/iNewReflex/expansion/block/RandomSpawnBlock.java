package cn.mcres.iNewReflex.expansion.block;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.api.block.BlockBuild;
import cn.mcres.iNewReflex.api.block.CustomBlockUtil;
import cn.mcres.iNewReflex.listener.BreakRandomBlock;
import cn.mcres.iNewReflex.listener.place.PlaceUtil;
import cn.mcres.iNewReflex.load.checkPlugin.ResLib;
import cn.mcres.iNewReflex.utils.LogUtil;
import cn.mcres.iNewReflex.utils.MathTool;
import cn.mcres.iNewReflex.utils.ResidenceUtils;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RandomSpawnBlock {
    private Player player;
    private World world;
    private int radiusX;
    private int radiusY;
    private int radiusZ;

    // 防止无限循环
    public static HashMap<String, Integer> randomSpawnMax = new LinkedHashMap<>();

    public RandomSpawnBlock(Player player, int radiusX, int radiusY, int radiusZ) {
        this.player = player;
        this.world = player.getWorld();
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.radiusZ = radiusZ;
    }

    public void spawn() {
        for (BlockBuild blockBuild : BlocksBuffer.blockList) {
            String blockId = blockBuild.getBlockId();
            if (blockBuild.isSpawnEnable()) {
                int maxAmount = blockBuild.getSpawnAmount();
                if (blockBuild.getSpawnType() == null) return;
                for (int i = 0; i < maxAmount; i++) {
                    for (Material material : blockBuild.getSpawnType()) {
                        randomSpawnMax.put(blockId, 1);
                        if (INewReflex.getInfo().isOldVersion()) {
                            replaceBlock(blockBuild, blockId, material);
                        }else {
                            Bukkit.getScheduler().runTaskAsynchronously(INewReflex.getMain(), () -> { // 异步处理，防止阻塞
                                replaceBlock(blockBuild, blockId, material);
                            });
                        }
                    }
                }
            }
        }
    }

    private void replaceBlock(BlockBuild blockBuild, String blockId, Material material) {
        Location loc = randomLoc();
        if (randomSpawnMax != null) {
            if (randomSpawnMax.get(blockId) >= BreakRandomBlock.randomSpawnFindMax) {
                return; // 防止无限循环
            }
        }
        randomSpawnMax.put(blockId, randomSpawnMax.get(blockId)+1);
        Block block = loc.getBlock();
        if (ResLib.resLibEnable) { // 防止拓展方块在玩家领地内生成
            if (ResidenceUtils.isInResidence(loc)) {
                replaceBlock(blockBuild, blockId, material);
            }
        }
        if (block.getType().equals(material)) {
            if (BreakRandomBlock.randomSpawnDebug) {
                LogUtil.send("x " + loc.getX() + "  y " + loc.getY() + "  z " + loc.getZ());
            }
            if (!INewReflex.getInfo().isOldVersion()) {
                MultipleFacing newBlockDataStem = (MultipleFacing) Bukkit.createBlockData(Material.MUSHROOM_STEM);
                MultipleFacing newBlockDataBrown = (MultipleFacing) Bukkit.createBlockData(Material.BROWN_MUSHROOM_BLOCK);
                MultipleFacing newBlockDataRed = (MultipleFacing) Bukkit.createBlockData(Material.RED_MUSHROOM_BLOCK);
                int modelId = blockBuild.getModelId();
                if (CustomBlockUtil.isWithinScope(0, modelId, 64)) {
                    CustomBlockUtil.placeCustomBlock(newBlockDataStem, block, modelId, blockId);
                } else if (CustomBlockUtil.isWithinScope(64, modelId, 128)) {
                    CustomBlockUtil.placeCustomBlock(newBlockDataBrown, block, modelId, blockId);
                } else if (CustomBlockUtil.isWithinScope(128, modelId, 192)) {
                    CustomBlockUtil.placeCustomBlock(newBlockDataRed, block, modelId, blockId);
                } else {
                    randomSpawnMax.remove(blockId);
                    return;
                }
            }else {
                PlaceUtil.placeCustomSkullBlock(block, blockId, blockBuild.getBlockUrl());
            }
            randomSpawnMax.remove(blockId);
        }else {
            replaceBlock(blockBuild, blockId, material);
        }
    }

    private Location randomLoc() {
        Random random = new Random();
        Location playerLoc = this.player.getLocation();
        int minX = MathTool.rounding(playerLoc.getX());
        int minY = MathTool.rounding(playerLoc.getY());
        int minZ = MathTool.rounding(playerLoc.getZ());

        int radiusNegativeX = minX-this.radiusX;
        int radiusNegativeY = minY-this.radiusY;
        int radiusNegativeZ = minZ-this.radiusZ;

        int x = random.nextInt((this.radiusX+minX) - (radiusNegativeX) + 1) + (radiusNegativeX);
        int y = random.nextInt((this.radiusY+minY) - (radiusNegativeY) + 1) + (radiusNegativeY);
        int z = random.nextInt((this.radiusZ+minZ) - (radiusNegativeZ) + 1) + (radiusNegativeZ);
        return new Location(this.world, x,y,z);
    }

}
