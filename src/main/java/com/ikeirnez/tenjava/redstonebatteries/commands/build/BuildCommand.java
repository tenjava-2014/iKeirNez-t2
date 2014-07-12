package com.ikeirnez.tenjava.redstonebatteries.commands.build;

import com.ikeirnez.tenjava.redstonebatteries.RedstoneBatteries;
import com.ikeirnez.tenjava.redstonebatteries.utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

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

        BuildCommandStructure buildCommandStructure;

        try {
            buildCommandStructure = BuildCommandStructure.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e){ // todo temp fix
            player.sendMessage(getPrefix("buildCmdInvalidStructure", args[0], Utils.join(BuildCommandStructure.values(), ChatColor.RED + ", " + ChatColor.GOLD, ChatColor.RED + " and " + ChatColor.GOLD)));
            return true;
        }

        Object[] buildArguments = null;

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

                buildArguments = new Object[]{ size };
                break;
        }

        player.setMetadata("RB-StructureBuild", new FixedMetadataValue(RedstoneBatteries.getInstance(), buildCommandStructure));

        if (buildArguments != null){
            player.setMetadata("RB-StructureBuild-Args", new FixedMetadataValue(RedstoneBatteries.getInstance(), buildArguments));
        }

        player.sendMessage(getPrefix("buildCmdReadyToBuild", buildCommandStructure.getFriendlyName()));
        return true;
    }
}
