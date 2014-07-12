package com.ikeirnez.tenjava.redstonebatteries.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A helper class for storing a Cuboid and gives various helper methods
 */
public class Cuboid implements ConfigurationSerializable, Cloneable {

    private final String worldName;
    private final int x1, y1, z1;
    private final int x2, y2, z2;
    private final List<Location> locations = new ArrayList<>();

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

        cacheLocations();
    }

    public Cuboid(Map<String, Object> data){
        this.worldName = (String) data.get("world");

        if (getWorld() == null){
            throw new IllegalArgumentException("World \"" + worldName + "\" doesn't exist.");
        }

        this.x1 = (Integer) data.get("x1");
        this.y1 = (Integer) data.get("y1");
        this.z1 = (Integer) data.get("z1");

        this.x2 = (Integer) data.get("x2");
        this.y2 = (Integer) data.get("y2");
        this.z2 = (Integer) data.get("z2");

        cacheLocations();
    }

    private void cacheLocations(){
        World world = getWorld();

        for (int x = getLowerX(); x <= getUpperX(); x++){
            for (int y = getLowerY(); y <= getUpperY(); y++){
                for (int z = getLowerZ(); z <= getUpperZ(); z++){
                    locations.add(new Location(world, x, y, z));
                }
            }
        }
    }

    public World getWorld(){
        return Bukkit.getWorld(worldName);
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

    public int getUpperX(){
        return x2;
    }

    public int getUpperY(){
        return y2;
    }

    public int getUpperZ(){
        return z2;
    }

    public int getLowerX(){
        return x1;
    }

    public int getLowerY(){
        return y1;
    }

    public int getLowerZ(){
        return z1;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Block> getBlocks(){
        List<Block> blocks = new ArrayList<>();

        for (Location location : getLocations()){
            blocks.add(location.getBlock());
        }

        return blocks;
    }

    public Location getCentre(){
        int x = getUpperX() + 1;
        int y = getUpperY() + 1;
        int z = getUpperZ() + 1;

        return new Location(getWorld(), getLowerX() + (x - getLowerX()) / 2.0,
                            getLowerY() + (y - getLowerY()) / 2.0,
                            getLowerZ() + (z - getLowerZ()) / 2.0);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("world", worldName);

        data.put("x1", x1);
        data.put("y1", y1);
        data.put("z1", z1);

        data.put("x2", x2);
        data.put("y2", y2);
        data.put("z2", z2);
        return data;
    }
}
