package com.ikeirnez.tenjava.redstonebatteries.commands.build;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * An enum containing all possible structures that can be erected (hehe) with the build command
 */
public enum BuildCommandStructure {

    BATTERY {
        public List<ItemStack> getRequired(Object... args) {
            int layers = (int) args[0]; // shrek has layers too
            List<ItemStack> required = new ArrayList<>();
            required.add(new ItemStack(Material.WOOL, 9 * 2 + (layers * 8), (short) -1));
            required.add(new ItemStack(Material.GLASS, layers, (short) 0));
            return required;
        }
    };

    public abstract List<ItemStack> getRequired(Object... args);

}
