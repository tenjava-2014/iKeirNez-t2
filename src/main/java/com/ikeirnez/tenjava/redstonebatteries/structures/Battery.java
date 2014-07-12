package com.ikeirnez.tenjava.redstonebatteries.structures;

import com.ikeirnez.tenjava.redstonebatteries.configuration.Serialization;
import com.ikeirnez.tenjava.redstonebatteries.utilities.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class Battery implements ConfigurationSerializable {

    private static final int BATTERY_SIZE_X = 3, BATTERY_SIZE_Z = 4, BATTERY_MIN_SIZE_Y = 5;
    private static final Material INPUT_BLOCK = Material.ENDER_STONE, OUTPUT_BLOCK = Material.LAPIS_BLOCK, CHARGED_NOTIFIER_BLOCK = Material.PISTON_STICKY_BASE;

    private final Cuboid cuboid;
    private int size;
    private int maxCharge = 0;
    private int charge = 0;

    private final List<Location> glassBlocksLocations = new ArrayList<>(), snowBlocksLocations = new ArrayList<>();
    private Location inputBlockLocation, outputBlockLocation, chargedNotifierBlock;

    // 8 snow pile = 1 block

    public Battery(Cuboid cuboid){
        this.cuboid = cuboid;

        if (getCuboid().getLengthX() != BATTERY_SIZE_X || getCuboid().getLengthZ() != BATTERY_SIZE_Z || getCuboid().getLengthY() < BATTERY_MIN_SIZE_Y){
            throw new IllegalArgumentException("Cuboid does not match dimensions of a Battery.");
        }

        size = getCuboid().getLengthY() - 4;

        Location centre = getCuboid().getCentre();

        // clean this up sometime
        for (Block block : cuboid.getBlocks()){
            Location location = block.getLocation();
            Material type = block.getType();

            if (type == Material.STAINED_GLASS){
                if ((location.getX() == centre.getX() + 1 || location.getX() == centre.getX() - 1) && (location.getZ() == centre.getX() + 1 || location.getZ() == centre.getZ() - 1)){
                    glassBlocksLocations.add(location);
                }
            } else if (type == Material.SNOW || type == Material.SNOW_BLOCK){
                if (location.getX() == centre.getX() && location.getZ() == centre.getZ()){
                    snowBlocksLocations.add(location);
                }
            } else if (type == INPUT_BLOCK){
                if (location.getX() == centre.getX() && location.getZ() == centre.getZ()){
                    inputBlockLocation = location;
                }
            } else if (type == OUTPUT_BLOCK){
                if (location.getX() == centre.getX() && location.getZ() == centre.getZ()){
                    outputBlockLocation = location;
                }
            } else if (type == CHARGED_NOTIFIER_BLOCK){
                if ((location.getX() == centre.getX() + 2 || location.getX() == centre.getX() - 2) && (location.getZ() == centre.getZ() + 2 || location.getZ() == centre.getZ() - 2)){
                    chargedNotifierBlock = location;
                }
            }
        }
    }

    public Battery(Map<String, Object> data){
        this.cuboid = new Cuboid((Map<String, Object>) data.get("cuboid"));

        Map<String, Object> locations = (Map<String, Object>) data.get("locations");
        this.inputBlockLocation = Serialization.deserializeLocation((Map<String, Object>) locations.get("inputBlock"));
        this.outputBlockLocation = Serialization.deserializeLocation((Map<String, Object>) locations.get("outputBlock"));
        this.chargedNotifierBlock = Serialization.deserializeLocation((Map<String, Object>) locations.get("chargedNotificationBlock"));

        Map<String, Object> glassBlocksMap = (Map<String, Object>) locations.get("glassBlocks");
        for (String key : glassBlocksMap.keySet()){
            glassBlocksLocations.add(Serialization.deserializeLocation((Map<String, Object>) glassBlocksMap.get(key)));
        }

        Map<String, Object> snowBlocksMap = (Map<String, Object>) locations.get("snowBlocks");
        for (String key : snowBlocksMap.keySet()){
            snowBlocksLocations.add(Serialization.deserializeLocation((Map<String, Object>) snowBlocksMap.get(key)));
        }

        this.maxCharge = (int) data.get("maxCharge");
        this.charge = (int) data.get("charge");
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(int maxCharge) {
        this.maxCharge = maxCharge;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("cuboid", getCuboid().serialize());
        data.put("maxCharge", getMaxCharge());
        data.put("charge", getCharge());

        Map<String, Object> locations = new HashMap<>();
        Map<String, Object> glassBlocks = new HashMap<>();
        Map<String, Object> snowBlocks = new HashMap<>();

        int i = 0;
        for (Location glassBlockLocation : glassBlocksLocations){
            glassBlocks.put(String.valueOf(i), Serialization.serializeLocation(glassBlockLocation));
        }

        i = 0;

        for (Location snowBlocksLocation : snowBlocksLocations){
            snowBlocks.put(String.valueOf(i), Serialization.serializeLocation(snowBlocksLocation));
        }

        locations.put("glassBlocks", glassBlocks);
        locations.put("snowBlocks", snowBlocks);
        data.put("locations", locations);
        return data;
    }
}
