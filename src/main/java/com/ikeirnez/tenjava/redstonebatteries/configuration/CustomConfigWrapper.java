package com.ikeirnez.tenjava.redstonebatteries.configuration;

import com.ikeirnez.tenjava.redstonebatteries.RedstoneBatteries;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Wrapper for getting, saving and reloading custom configs.
 */
public class CustomConfigWrapper {

    private File file;
    private FileConfiguration fileConfiguration;

    public CustomConfigWrapper(File file){
        this.file = file;

        try {
            file.createNewFile();
        } catch (IOException e) {
            RedstoneBatteries.getInstance().getLogger().severe("Couldn't create file for custom config: " + file.getPath());
            e.printStackTrace();
        }
    }

    public void reloadCustomConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getCustomConfig() {
        if (fileConfiguration == null) {
            reloadCustomConfig();
        }

        return fileConfiguration;
    }

    public void saveCustomConfig() {
        if (fileConfiguration == null || file == null) {
            return;
        }

        try {
            getCustomConfig().save(file);
        } catch (IOException ex) {
            RedstoneBatteries.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + file, ex);
        }
    }

}
