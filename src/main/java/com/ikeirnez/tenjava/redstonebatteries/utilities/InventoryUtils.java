package com.ikeirnez.tenjava.redstonebatteries.utilities;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class InventoryUtils {

    private InventoryUtils(){}

    /**
     * This method resolves an issue when taking an amount of items from a players inventory
     * and they have those items in multiple stacks
     *
     * @param inventory The inventory to remove the items from
     * @param material The material to remove from the players inventory
     * @param amount The amount of items to take
     * @return The amount of items missing from the players inventory, 0 would mean the player had the correct amount of items
     */
    public static int remove(Inventory inventory, Material material, int amount){
        return remove(inventory, material, (short) -1, amount);
    }

    /**
     * This method resolves an issue when taking an amount of items from a players inventory
     * and they have those items in multiple stacks
     *
     * @param inventory The inventory to remove the items from
     * @param material The material to remove from the players inventory
     * @param damage The damage value the material should have, default 0, -1 ignores
     * @param amount The amount of items to take
     * @return The amount of items missing from the players inventory, 0 would mean the player had the correct amount of items
     */
    public static int remove(Inventory inventory, Material material, short damage, int amount){
        int removed = 0;
        boolean ignoreDamage = damage == -1;

        for (int slot : inventory.all(material).keySet()){
            ItemStack itemStack = inventory.getItem(slot);

            if (ignoreDamage || itemStack.getDurability() == damage){
                int iAmount = itemStack.getAmount();

                if (iAmount >= amount){
                    itemStack.setAmount(iAmount - amount);
                    removed += amount;
                } else {
                    itemStack.setAmount(0);
                    removed += iAmount;
                }

                inventory.setItem(slot, itemStack.getAmount() == 0 ? null : itemStack);

                if (removed >= amount){
                    break;
                }
            }
        }

        return amount - removed;
    }

    /**
     * This method resolves an issue when counting an amount of items from a players inventory
     * and they have those items in multiple stacks
     *
     * @param inventory The inventory to count the items from
     * @param material The material to count from the players inventory
     * @param amount The amount of that item to count
     * @return Whether the player has enough items
     */
    public static boolean has(Inventory inventory, Material material, int amount){
        return has(inventory, material, (short) -1, amount);
    }

    /**
     * This method resolves an issue when counting an amount of items from a players inventory
     * and they have those items in multiple stacks
     *
     * @param inventory The inventory to count the items from
     * @param material The material to count from the players inventory
     * @param damage The damage value the material should have, default 0, -1 ignores
     * @param amount The amount of that item to count
     * @return Whether the player has enough items
     */
    public static boolean has(Inventory inventory, Material material, short damage, int amount){
        int amountHas = 0;
        boolean ignoreDamage = damage == -1;

        for (int slot : inventory.all(material).keySet()){
            ItemStack itemStack = inventory.getItem(slot);

            if (ignoreDamage || itemStack.getDurability() == damage){
                amountHas += itemStack.getAmount();

                if (amountHas >= amount){
                    break;
                }
            }
        }

        return amountHas >= amount;
    }

}
