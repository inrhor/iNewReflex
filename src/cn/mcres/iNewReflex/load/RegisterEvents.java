package cn.mcres.iNewReflex.load;

import cn.mcres.iCraftNew.playerListener.InventoryClick;
import cn.mcres.iCraftNew.playerListener.InventoryClose;
import cn.mcres.iCraftNew.playerListener.InventoryDrag;
import cn.mcres.iCraftNew.playerListener.PlayerInteract;
import cn.mcres.iNewReflex.INewReflex;
import cn.mcres.iNewReflex.gui.guide.*;
import cn.mcres.iNewReflex.listener.*;
import org.bukkit.event.Listener;

import java.util.Arrays;

class RegisterEvents {
    void run() {
        for (Listener listener : Arrays.asList(
                new ItemDurability(),
                new InventoryClick(),
                new InventoryClose(),
                new InventoryDrag(),
                new PlayerInteract(),
                new GuideHome(),
                new GuideItemType(),
                new GuideTypeWeapon(),
                new GuideTypeTool(),
                new GuideTypeMaterial(),
                new GuideTypeBlock(),
                new GuideBook(),
                new GuideTypeFood(),
                new GuideTypeMobs()
        )) {
            INewReflex.getMain().getServer().getPluginManager().registerEvents(listener, INewReflex.getMain());
        }
    }
}
