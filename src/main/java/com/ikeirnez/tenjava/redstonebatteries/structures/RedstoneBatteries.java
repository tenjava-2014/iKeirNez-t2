package com.ikeirnez.tenjava.redstonebatteries.structures;

import com.ikeirnez.tenjava.redstonebatteries.commands.build.BuildCommand;
import com.ikeirnez.tenjava.redstonebatteries.listeners.StructureLocationListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class.
 */
public class RedstoneBatteries extends JavaPlugin {

    private static RedstoneBatteries instance;

    public static RedstoneBatteries getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("build").setExecutor(new BuildCommand());

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new StructureLocationListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

}
