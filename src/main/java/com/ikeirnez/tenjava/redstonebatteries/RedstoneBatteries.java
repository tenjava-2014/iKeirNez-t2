package com.ikeirnez.tenjava.redstonebatteries;

import com.ikeirnez.tenjava.redstonebatteries.commands.build.BuildCommand;
import com.ikeirnez.tenjava.redstonebatteries.configuration.CustomConfigWrapper;
import com.ikeirnez.tenjava.redstonebatteries.configuration.Setting;
import com.ikeirnez.tenjava.redstonebatteries.listeners.StructureLocationListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Main plugin class.
 */
public class RedstoneBatteries extends JavaPlugin {

    private static RedstoneBatteries instance;

    public static RedstoneBatteries getInstance() {
        return instance;
    }

    private StructureManager structureManager;

    @Override
    public void onEnable() {
        instance = this;

        // copy default settings
        for (Setting setting : Setting.values()){
            getConfig().addDefault(setting.getConfigPath(), setting.getDef());
        }

        getConfig().options().header("RedstoneBatteries by iKeirNez - Configuration\nSee BukkitDev page for more information on these settings");
        getConfig().options().copyDefaults(true);
        saveConfig();

        // actually cache values
        for (Setting setting : Setting.values()){
            setting.setValue(getConfig().get(setting.getConfigPath()));
        }

        structureManager = new StructureManager(new File(getDataFolder(), "structures.yml"));

        getCommand("build").setExecutor(new BuildCommand());

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new StructureLocationListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public StructureManager getStructureManager() {
        return structureManager;
    }
}
