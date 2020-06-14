package cn.mcres.iNewReflex.listener;

import cn.mcres.iNewReflex.utils.ReturnMaterial;
import cn.mcres.iNewReflex.expansion.block.RandomSpawnBlock;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakRandomBlock implements Listener {
    public static int randomSpawnFindMax;
    public static List<String> randomSpawnEnableWorld;
    public static boolean randomSpawnDebug;
    public static List<String> randomSpawnBlockConditions;
    public static int randomSpawnRadiusX;
    public static int randomSpawnRadiusY;
    public static int randomSpawnRadiusZ;

    // 触发生成自然方块
    @EventHandler
    void breakRunRandomSpawnBlock(BlockBreakEvent ev) {
        Block block = ev.getBlock();
        Player player = ev.getPlayer();
        if (!inEnableWorld(player)) return;
        for (String condition : randomSpawnBlockConditions) {
            String script = condition.substring(condition.indexOf(":")+1);
            if (condition.startsWith("material:")) {
                if (block.getType().equals(ReturnMaterial.getMaterial(script))) {
                    RandomSpawnBlock randomSpawnBlock = new RandomSpawnBlock(
                            player,
                            randomSpawnRadiusX,
                            randomSpawnRadiusY,
                            randomSpawnRadiusZ);
                    randomSpawnBlock.spawn();
                }
            }
        }
    }

    private boolean inEnableWorld(Player player) {
        String playerWorld = player.getWorld().getName();
        for (String worldName : randomSpawnEnableWorld) {
            if (playerWorld.equals(worldName)) {
                return true;
            }
        }
        return false;
    }
}
