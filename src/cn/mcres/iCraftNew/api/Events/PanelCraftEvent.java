package cn.mcres.iCraftNew.api.Events;

import java.util.LinkedList;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import cn.mcres.iCraftNew.model.Panel;
import cn.mcres.iCraftNew.model.Recipe;

public class PanelCraftEvent  extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Panel panel;
    private Player player;
    private Recipe recipe;
    private LinkedList<ItemStack> items;
    private boolean cancelled;
 
    public PanelCraftEvent(Panel panel, Player player, Recipe recipe, LinkedList<ItemStack> items) {
    	this.panel = panel;
    	this.player = player;
    	this.recipe = recipe;
    	this.items = items;
    }
 
    public Panel getPanel() {
        return this.panel;
    }
    
 	public Player getPlayer() {
		return this.player;
	}
 	
    public Recipe getRecipe() {
		return recipe;
	}

	public LinkedList<ItemStack> getItems() {
		return items;
	}

	public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
