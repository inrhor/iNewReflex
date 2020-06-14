package cn.mcres.iNewReflex.cmd.subCmd;

import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.expansion.block.RandomSpawnBlock;
import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;
import cn.mcres.iNewReflex.utils.LogUtil;
import cn.mcres.iNewReflex.utils.MathTool;
import cn.mcres.iNewReflex.utils.MsgUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockSpawn {
    public static boolean execute(CommandSender sender/*, Command command, String label*/, String[] args) {

        if (!(sender instanceof Player)) {
            LogUtil.send(COMMAND_CONSOLE);
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("inewrx.admin")) {
            MsgUtil.send(player, COMMAND_NO_PERMISSION);
            return true;
        }

        if (INewReflex.getInfo().isOldVersion()) {
            MsgUtil.send(player, RANDOM_SPAWN_BLOCK_IS_OLD_VERSION);
            return true;
        }

        if (args.length == 4 ) { // inewrx blockspawn x y z
            if (!MathTool.isIntNumber(args[1]) || !MathTool.isIntNumber(args[2]) || !MathTool.isIntNumber(args[3])) {
                MsgUtil.send(player, NOT_INT);
                return true;
            }

            int radiusX = Integer.parseInt(args[1]);
            int radiusY = Integer.parseInt(args[2]);
            int radiusZ = Integer.parseInt(args[3]);

            RandomSpawnBlock randomSpawnBlock = new RandomSpawnBlock(player, radiusX, radiusY, radiusZ);
            randomSpawnBlock.spawn();
            MsgUtil.send(player, COMMAND_SPAWN_BLOCK);
        }else {
            MsgUtil.send(sender, BLOCK_SPAWN_WRONG_FORMAT);
        }

        return true;
    }
}
