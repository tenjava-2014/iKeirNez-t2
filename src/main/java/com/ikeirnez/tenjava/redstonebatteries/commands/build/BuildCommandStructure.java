package com.ikeirnez.tenjava.redstonebatteries.commands.build;

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
            return required;
        }

        @Override
        public void build(Location l, Object... args) {
            int layers = (int) args[0]; // onions
            int totalLayers = layers + 2; // layers + top/bottom + input/output

            for (int x = 0; x < 3; x++){
                for (int y = 0; y < totalLayers; y++){
                    for (int z = 0; z < 3; z++){
                        Location location = l.clone().add(x, y, z);
                        Block block = location.getBlock();

                        if (y == 0 || y  == totalLayers - 1){
                            block.setType(Material.WOOL);
                            block.setData((byte) 15); // black
                        } else {
                            if (x == 1 && z == 1){
                                if (y != 1){
                                    block.setType(Material.AIR);
                                }
                            } else {
                                if (x == 1 && z == 0){
                                    block.setType(Material.STAINED_GLASS);
                                    block.setData((byte) 14); // red
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
            Block pistonBlock = l.clone().add(1, totalLayers - 2, 3).getBlock();
            pistonBlock.setType(Material.PISTON_STICKY_BASE);
            pistonBlock.setData((byte) 3);

            l.clone().add(1, -1, 1).getBlock().setType(Material.SPONGE);
            l.clone().add(1, totalLayers, 1).getBlock().setType(Material.LAPIS_BLOCK);
        }
    };

    public abstract List<ItemStack> getRequired(Object... args);
    public abstract void build(Location location, Object... args);

}
