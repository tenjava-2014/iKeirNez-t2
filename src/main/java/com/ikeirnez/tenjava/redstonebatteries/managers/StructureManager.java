package com.ikeirnez.tenjava.redstonebatteries.managers;

import com.ikeirnez.tenjava.redstonebatteries.configuration.CustomConfigWrapper;
import com.ikeirnez.tenjava.redstonebatteries.structures.Battery;
import com.ikeirnez.tenjava.redstonebatteries.utilities.Utils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class StructureManager {

    private CustomConfigWrapper structureConfigWrapper;
    private Set<Battery> batteries = new HashSet<>();

    public StructureManager(File structureConfig){
        this.structureConfigWrapper = new CustomConfigWrapper(structureConfig);

        Map<String, Object> structuresMap = structureConfigWrapper.getConfig().getValues(true);
        for (String structureKey : structuresMap.keySet()){
            Map<String, Object> structureData = Utils.getSubMap(structuresMap, structureKey);
            registerStructure(new Battery(structureData));
        }
    }

    public Set<Battery> getStructures() {
        return batteries;
    }

    public void registerStructure(Battery battery){
        batteries.add(battery);
    }

    public void unregisterStructure(Battery battery){
        batteries.remove(battery);
    }

    public void saveAll(){
        FileConfiguration structuresConfig = structureConfigWrapper.getConfig();
        int i = 0;

        for (Battery battery : getStructures()){
            structuresConfig.set(String.valueOf(i++), battery.serialize());
        }

        structureConfigWrapper.saveConfig();
    }

}
