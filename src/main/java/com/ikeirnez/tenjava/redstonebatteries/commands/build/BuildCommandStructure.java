package com.ikeirnez.tenjava.redstonebatteries.commands.build;

import com.ikeirnez.tenjava.redstonebatteries.RedstoneBatteries;
import com.ikeirnez.tenjava.redstonebatteries.structures.Battery;
import com.ikeirnez.tenjava.redstonebatteries.utilities.Cuboid;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * An enum containing all possible structures that can be erected (hehe) with the build command
 */
public enum BuildCommandStructure {

    BATTERY {
        @Override
        public List<ItemStack> getRequired(Object... args) {
            int layers = (int) args[0]; // shrek has layers too
            List<ItemStack> required = new ArrayList<>();
            required.add(new ItemStack(Material.WOOL, 9 * 2 + (layers * 8), (short) -1));
            required.add(new ItemStack(Material.GLASS, layers, (short) 0));
            required.add(new ItemStack(Material.ENDER_STONE, 1));
            required.add(new ItemStack(Material.LAPIS_BLOCK, 1));
            required.add(new ItemStack(Material.PISTON_STICKY_BASE, 1));
            return required;
        }

        @Override
        public void build(Location l, Object... args) {
            List<Location> glassBlockLocations = new ArrayList<>();
            List<Location> snowBlockLocations = new ArrayList<>();

            int layers = (int) args[0]; // onions
            int totalLayers = layers + 2; // layers + top/bottom + input/output

            l.add(0, 2, 0);

            // this needs rewritten at some point, not enough time atm
            for (int y = 0; y < totalLayers; y++){
                for (int x = 0; x < 3; x++){
                    for (int z = 0; z < 3; z++){
                        Location location = l.clone().add(x, y, z);
                        Block block = location.getBlock();

                        if (y == 0 || y  == totalLayers - 1){
                            block.setType(Material.WOOL);
                            block.setData((byte) 15); // black
                        } else {
                            if (x == 1 && z == 1){
                                if (y != 0){
                                    snowBlockLocations.add(location);
                                }
                            } else {
                                if (x == 1 && z == 0){
                                    block.setType(Material.STAINED_GLASS);
                                    block.setData((byte) 14); // red
                                    glassBlockLocations.add(location);
                                } else {
                                    block.setType(Material.WOOL);
                                    block.setData((byte) 15); // black
                                }
                            }
                        }
                    }
                }
            }

            // this is really bad, should be done in the loop above, but will fix when I have more time
            Location chargedNotifierBlockLocation = l.clone().add(1, totalLayers - 2, 3);
            Block pistonBlock = chargedNotifierBlockLocation.getBlock();
            pistonBlock.setType(Material.PISTON_STICKY_BASE);
            pistonBlock.setData((byte) 3);

            Location inputBlockLocation = l.clone().add(1, -1, 1);
            inputBlockLocation.getBlock().setType(Material.ENDER_STONE);

            Location outputBlockLocation = l.clone().add(1, totalLayers, 1);
            outputBlockLocation.getBlock().setType(Material.LAPIS_BLOCK);

            // the below code is pretty bad, in a rush
            RedstoneBatteries.getInstance().getStructureManager().registerStructure(new Battery(new Cuboid(l, l.clone().add(3, totalLayers + 2, 4)), glassBlockLocations, snowBlockLocations, inputBlockLocation, outputBlockLocation, chargedNotifierBlockLocation));
        }
    };

    public String getFriendlyName(){
        return WordUtils.capitalize(name().toLowerCase().replaceAll("_", " "));
    }

    public abstract List<ItemStack> getRequired(Object... args);
    public abstract void build(Location location, Object... args);

}
