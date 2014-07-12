package com.ikeirnez.tenjava.redstonebatteries.listeners;

import com.ikeirnez.tenjava.redstonebatteries.Language;
import com.ikeirnez.tenjava.redstonebatteries.structures.RedstoneBatteries;
import com.ikeirnez.tenjava.redstonebatteries.commands.build.BuildCommandStructure;
import com.ikeirnez.tenjava.redstonebatteries.utilities.InventoryUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

import static com.ikeirnez.tenjava.redstonebatteries.Language.getPrefix;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class StructureLocationListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if (e.getAction() == Action.LEFT_CLICK_BLOCK && player.hasMetadata("RB-StructureBuild")){
            e.setCancelled(true);
            BuildCommandStructure buildCommandStructure = (BuildCommandStructure) player.getMetadata("RB-StructureBuild").get(0).value();
            Object[] buildArgs = player.hasMetadata("RB-StructureBuild-Args") ? (Object[]) player.getMetadata("RB-StructureBuild-Args").get(0).value() : null;

            player.removeMetadata("RB-StructureBuild", RedstoneBatteries.getInstance());
            player.removeMetadata("RB-StructureBuild-Args", RedstoneBatteries.getInstance());

            if (player.getGameMode() != GameMode.CREATIVE){
                PlayerInventory inventory = player.getInventory();
                List<ItemStack> required = buildCommandStructure.getRequired(buildArgs);

                for (ItemStack itemStack : required){ // check player has required items
                    Material material = itemStack.getType();
                    short durability = itemStack.getDurability();
                    int amount = itemStack.getAmount();

                    if (!InventoryUtils.has(inventory, material, durability, amount)){
                        player.sendMessage(getPrefix("buildCmdNotEnough", InventoryUtils.getFriendlyMaterialName(material), amount));
                        return;
                    }
                }

                for (ItemStack itemStack : required){ // iterate through again, this time taking the items
                    InventoryUtils.remove(inventory, itemStack.getType(), itemStack.getDurability(), itemStack.getAmount());
                }
            }

            buildCommandStructure.build(e.getClickedBlock().getLocation(), buildArgs);
            player.sendMessage(Language.getPrefix("built", buildCommandStructure.getFriendlyName()));
        }
    }

}
