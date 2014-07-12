package com.ikeirnez.tenjava.redstonebatteries.utilities;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * A helper class for storing a Cuboid and gives various helper methods
 */
public class Cuboid implements ConfigurationSerializable, Cloneable {

    private final String worldName;
    private final int x1, y1, z1;
    private final int x2, y2, z2;

    public Cuboid(Location location1, Location location2){
        if (!location1.getWorld().equals(location2.getWorld())){
            throw new IllegalArgumentException("Both locations must be in the same world");
        }

        worldName = location1.getWorld().getName();

        x1 = Math.min(location1.getBlockX(), location2.getBlockX());
        y1 = Math.min(location1.getBlockY(), location2.getBlockY());
        z1 = Math.min(location1.getBlockZ(), location2.getBlockZ());

        x2 = Math.max(location1.getBlockX(), location2.getBlockX());
        y2 = Math.max(location1.getBlockY(), location2.getBlockY());
        z2 = Math.max(location1.getBlockZ(), location2.getBlockZ());
    }

    public Cuboid(Map<String, Object> data){
        worldName = (String) data.get("worldName");

        this.x1 = (Integer) data.get("x1");
        this.y1 = (Integer) data.get("y1");
        this.z1 = (Integer) data.get("z1");

        this.x2 = (Integer) data.get("x2");
        this.y2 = (Integer) data.get("y2");
        this.z2 = (Integer) data.get("z2");
    }

    public boolean contains(Block block){
        return contains(block.getLocation());
    }

    public boolean contains(Location location){
        return worldName.equals(location.getWorld().getName()) && contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean contains(int x, int y, int z){
        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    public int getLengthX(){
        return (x2 - x1) + 1;
    }
    public int getLengthY(){
        return (y2 - y1) + 1;
    }
    public int getLengthZ(){
        return (z2 - z1) + 1;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("worldName", worldName);

        data.put("x1", x1);
        data.put("y1", y1);
        data.put("z1", z1);

        data.put("x2", x2);
        data.put("y2", y2);
        data.put("z2", z2);
        return data;
    }
}
