package cn.mcres.iNewReflex.cmd.subCmd;

import cn.mcres.iNewReflex.expansion.block.BlocksBuffer;
import cn.mcres.iNewReflex.expansion.item.food.FoodItem;
import cn.mcres.iNewReflex.expansion.item.material.MaterialItem;
import cn.mcres.iNewReflex.expansion.item.tool.ToolItem;
import cn.mcres.iNewReflex.expansion.item.weapon.MeleeItem;
import cn.mcres.iNewReflex.utils.MathTool;
import cn.mcres.iNewReflex.utils.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static cn.mcres.iNewReflex.fileYaml.lang.GetLangYaml.*;

public class Give {
    public static boolean execute(CommandSender sender,/* Command command, String label, */String[] args) {

            if (!sender.hasPermission("inewrx.admin")) {
                MsgUtil.send(sender, COMMAND_NO_PERMISSION);
                return true;
            }
            if (args.length == 4 || args.length == 5 ) { // inewrx give type blockId player amount
                Player target = Bukkit.getPlayer(args[3]);
                if (target==null) {
                    MsgUtil.send(sender, PLAYER_NOT_ONLINE);
                    return true;
                }
                String type = args[1];
                String itemId = args[2];
                switch (type) {
                    case "block":
                        if (BlocksBuffer.blocksMap.get(itemId) == null) {
                            MsgUtil.send(sender, ID_NOT_EXIST);
                            return true;
                        }
                        ItemStack getBlock = BlocksBuffer.blocksMap.get(itemId);
                        sendItem(sender, args, target, getBlock);
                        break;
                    case "weapon": {
                        if (MeleeItem.meleeItemList.get(itemId) == null) {
                            MsgUtil.send(sender, ID_NOT_EXIST);
                            return true;
                        }
                        ItemStack itemStack = MeleeItem.meleeItemList.get(itemId).getItem();
                        sendItem(sender, args, target, itemStack);
                        break;
                    }
                    case "tool": {
                        if (ToolItem.toolItemList.get(itemId) == null) {
                            MsgUtil.send(sender, ID_NOT_EXIST);
                            return true;
                        }
                        ItemStack itemStack = ToolItem.toolItemList.get(itemId).getItem();
                        sendItem(sender, args, target, itemStack);
                        break;
                    }
                    case "material": {
                        if (MaterialItem.materialItemList.get(itemId) == null) {
                            MsgUtil.send(sender, ID_NOT_EXIST);
                            return true;
                        }
                        ItemStack itemStack = MaterialItem.materialItemList.get(itemId).getItem();
                        sendItem(sender, args, target, itemStack);
                        break;
                    }
                    case "food": {
                        if (FoodItem.foodItemList.get(itemId) == null) {
                            MsgUtil.send(sender, ID_NOT_EXIST);
                            return true;
                        }
                        ItemStack itemStack = FoodItem.foodItemList.get(itemId).getItem();
                        sendItem(sender, args, target, itemStack);
                        break;
                    }
                    default:
                        MsgUtil.send(sender, TYPE_NOT_EXIST);
                        break;
                }
            }else {
                MsgUtil.send(sender, COMMAND_GIVE_ERROR_FORMAT);
            }

        return true;
    }

    private static void sendItem(CommandSender sender, String[] args, Player target, ItemStack itemStack) {
        int amount = 1;
        if (args.length == 5) {
            if (MathTool.isIntNumber(args[4])) {
                amount = Integer.parseInt(args[4]);
            }else {
                MsgUtil.send(sender, NOT_INT);
            }
        }
        while (amount-->0) {
            target.getInventory().addItem(itemStack);
        }
        List<String> newMsg = new ArrayList<>();
        for (String s : SUCCESSFULLY_GIVE) {
            newMsg.add(s
                    .replaceAll("@target", target.getName())
                    .replaceAll("@itemName", itemStack.getItemMeta().getDisplayName())
                    .replaceAll("@amount", String.valueOf(amount)));
        }
        if (sender == Bukkit.getConsoleSender()) {
            MsgUtil.send(sender, newMsg);
        }else {
            Player player = (Player) sender;
            if (!target.equals(player)) {
                MsgUtil.send(sender, newMsg);
            }
        }
    }
}
