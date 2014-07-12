package com.ikeirnez.tenjava.redstonebatteries;

import com.ikeirnez.tenjava.redstonebatteries.commands.build.BuildCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class.
 */
public class RedstoneBatteries extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("build").setExecutor(new BuildCommand());
    }

    @Override
    public void onDisable() {

    }

}
