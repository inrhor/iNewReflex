package cn.mcres.iNewReflex.expansion.block;

import cn.mcres.iNewReflex.api.block.BlockBuild;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static cn.mcres.iNewReflex.fileYaml.block.CreateBlocksYaml.blocksYaml;

public class BlocksBuffer {
    // 缓存方块
    public static HashMap<String, ItemStack> blocksMap = new LinkedHashMap<>();
    public static HashMap<String, BlockBuild> blockBuildMap = new LinkedHashMap<>();
    // 存入方块ID
    public static List<BlockBuild> blockList = new ArrayList<>();

    public void run() {
        final FileConfiguration yaml = blocksYaml;
        Material mushroom_stem_or_brown = Material.STONE;
        if (yaml.contains("blocks")) {
            for (String blockId : yaml.getConfigurationSection("blocks").getKeys(false)) {
                BlockBuild item = new BlockBuild(
                        blockId,
                        yaml.getInt("blocks."+blockId+".modelId"),
                        yaml.getInt("blocks."+blockId+".customModelData"),
                        yaml.getString("blocks."+blockId+".displayName"),
                        yaml.getStringList("blocks."+blockId+".lore"),
                        mushroom_stem_or_brown);
                blockBuildMap.put(blockId, item);
                blocksMap.put(blockId, item.getItem());
                blockList.add(item);
            }
        }
    }
}
