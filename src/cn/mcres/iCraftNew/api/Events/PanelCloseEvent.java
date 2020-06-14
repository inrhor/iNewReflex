package cn.mcres.iCraftNew.api.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import cn.mcres.iCraftNew.model.Panel;

public class PanelCloseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Panel panel;
    private Player player;
    public PanelCloseEvent(Panel panel, Player player) {
    	this.panel = panel;
    	this.player = player;
    }
 
    public Panel getPanel() {
        return this.panel;
    }
    
 	public Player getPlayer() {
		return this.player;
	}
 	
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
