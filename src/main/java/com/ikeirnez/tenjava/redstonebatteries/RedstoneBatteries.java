package com.ikeirnez.tenjava.redstonebatteries;

import com.ikeirnez.tenjava.redstonebatteries.commands.build.BuildCommand;
import com.ikeirnez.tenjava.redstonebatteries.listeners.StructureLocationListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class.
 */
public class RedstoneBatteries extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("build").setExecutor(new BuildCommand());

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new StructureLocationListener(), this);
    }

    @Override
    public void onDisable() {

    }

}
