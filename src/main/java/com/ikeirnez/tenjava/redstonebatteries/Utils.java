package com.ikeirnez.tenjava.redstonebatteries;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class Utils {

    private Utils(){}

    public static boolean isPlayer(CommandSender commandSender){
        return isPlayer(commandSender, true);
    }

    public static boolean isPlayer(CommandSender commandSender, boolean warn){
        boolean player = commandSender instanceof Player;

        if (warn && !player){
            commandSender.sendMessage(Language.get("notPlayer"));
        }

        return player;
    }

    public static boolean hasPermission(CommandSender commandSender, Permission permission){
        return hasPermission(commandSender, permission, true);
    }

    public static boolean hasPermission(CommandSender commandSender, Permission permission, boolean warn){
        boolean has = !(commandSender instanceof Player) && commandSender.hasPermission(permission);

        if (warn && !has){
            commandSender.sendMessage(Language.get("noPermission", permission.getName()));
        }

        return has;
    }

}
