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

    public Battery(Cuboid cuboid, List<Location> glassBlocksLocations, List<Location> snowBlocksLocations, Location inputBlockLocation, Location outputBlockLocation, Location chargedNotifierBlock){
        this.cuboid = cuboid;
        this.glassBlocksLocations.addAll(glassBlocksLocations);
        this.snowBlocksLocations.addAll(snowBlocksLocations);
        this.inputBlockLocation = inputBlockLocation;
        this.outputBlockLocation = outputBlockLocation;
        this.chargedNotifierBlock = chargedNotifierBlock;
        this.size = glassBlocksLocations.size();

        update();
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

        update();
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(int maxCharge) {
        this.maxCharge = maxCharge;

        if (getMaxCharge() > getCharge()){
            setCharge(getMaxCharge());
        }
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

    private void update(){

    }
}
