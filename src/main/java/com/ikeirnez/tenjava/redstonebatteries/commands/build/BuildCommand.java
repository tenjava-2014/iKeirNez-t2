package com.ikeirnez.tenjava.redstonebatteries.commands.build;

import com.ikeirnez.tenjava.redstonebatteries.utilities.InventoryUtils;
import com.ikeirnez.tenjava.redstonebatteries.utilities.Utils;
import net.milkbowl.vault.item.Items;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

import static com.ikeirnez.tenjava.redstonebatteries.Language.getPrefix;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class BuildCommand implements CommandExecutor {

    private Permission buildPermission = new Permission("RedstoneBatteries.build", "Grants access to /build", PermissionDefault.TRUE);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!Utils.isPlayer(sender) || !Utils.hasPermission(sender, buildPermission)){
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2){
            return false;
        }

        BuildCommandStructure buildCommandStructure = BuildCommandStructure.valueOf(args[0].toUpperCase());

        if (buildCommandStructure == null){
            player.sendMessage(getPrefix("buildCmdInvalidStructure", args[0], Utils.join(BuildCommandStructure.values(), ChatColor.RED + ", " + ChatColor.GOLD, ChatColor.RED + " and " + ChatColor.GOLD)));
        } else {
            switch (buildCommandStructure){ // we may have other structures in the future
                default:
                    throw new RuntimeException("Developer error, unhandled BuildCommandStructure \"" + buildCommandStructure.name() + "\""); // need a better exception type
                case BATTERY:
                    int size;

                    try {
                        size = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e){
                        player.sendMessage(getPrefix("notInteger", args[0]));
                        return true;
                    }

                    if (player.getGameMode() != GameMode.CREATIVE){
                        PlayerInventory inventory = player.getInventory();
                        List<ItemStack> required = BuildCommandStructure.BATTERY.getRequired(size);

                        for (ItemStack itemStack : required){
                            Material material = itemStack.getType();
                            short durability = itemStack.getDurability();
                            int amount = itemStack.getAmount();

                            if (!InventoryUtils.has(inventory, material, durability, amount)){
                                player.sendMessage(getPrefix("buildCmdNotEnough", durability == -1 ? Items.itemByType(material).getName() : Items.itemByType(material, durability).getName(), amount));
                                return true;
                            }
                        }
                    }

                    break;
            }
        }

        return true;
    }
}
