package com.ikeirnez.tenjava.redstonebatteries.listeners;

import com.ikeirnez.tenjava.redstonebatteries.commands.build.BuildCommandStructure;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class StructureLocationListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            BuildCommandStructure.BATTERY.build(e.getClickedBlock().getLocation().add(0, 3, 0), 3); // test ;)
        }
    }

}
