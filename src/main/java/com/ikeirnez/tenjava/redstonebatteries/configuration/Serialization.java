package com.ikeirnez.tenjava.redstonebatteries.configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper methods to serialize and deserialze Locations to a map form
 */
public class Serialization {

    private Serialization(){}

    public static Map<String, Object> serializeLocation(Location location){
        Map<String, Object> data = new HashMap<>();
        data.put("world", location.getWorld().getName());
        data.put("x", location.getBlockX());
        data.put("y", location.getBlockX());
        data.put("z", location.getBlockZ());
        return data;
    }

    public static Location deserializeLocation(Map<String, Object> data){
        String worldName = (String) data.get("world");
        World world = Bukkit.getWorld(worldName);

        if (world == null){
            throw new IllegalArgumentException("World \"" + worldName + "\" not found");
        }

        return new Location(world, (int) data.get("x"), (int) data.get("y"), (int) data.get("z"));
    }

}
