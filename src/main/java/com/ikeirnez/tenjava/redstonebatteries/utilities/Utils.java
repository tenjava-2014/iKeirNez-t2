package com.ikeirnez.tenjava.redstonebatteries.utilities;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.List;

import static com.ikeirnez.tenjava.redstonebatteries.Language.getPrefix;

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
            commandSender.sendMessage(getPrefix("notPlayer"));
        }

        return player;
    }

    public static boolean hasPermission(CommandSender commandSender, Permission permission){
        return hasPermission(commandSender, permission, true);
    }

    public static boolean hasPermission(CommandSender commandSender, Permission permission, boolean warn){
        boolean has = !(commandSender instanceof Player) && commandSender.hasPermission(permission);

        if (warn && !has){
            commandSender.sendMessage(getPrefix("noPermission", permission.getName()));
        }

        return has;
    }

    public static String join(List<Object> strings, String separator, String lastSeparator){
        return join(strings.toArray(), separator, lastSeparator);
    }

    public static String join(Object[] strings, String separator, String lastSeparator){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strings.length; i++){
            stringBuilder.append(strings[i]);

            if (strings.length > 1 && i == strings.length - 2){
                stringBuilder.append(lastSeparator);
            } else {
                stringBuilder.append(separator);
            }
        }

        return stringBuilder.toString();
    }

}
