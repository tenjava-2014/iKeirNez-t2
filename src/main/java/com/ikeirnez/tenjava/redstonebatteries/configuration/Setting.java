package com.ikeirnez.tenjava.redstonebatteries.configuration;

import org.apache.commons.lang.WordUtils;

/**
 * An enum containing all Settings
 */
public enum Setting {

    BATTERY__POWER_PER_BLOCK(100);

    private final String configPath;

    private final Object def;
    private Object value;

    private Setting(Object def){
        this.def = def;
        setValue(def);

        this.configPath = WordUtils.capitalize(name().toLowerCase().replaceAll("__", ".").replaceAll("_", " ")).replaceAll(" ", "");
    }

    public String getConfigPath() {
        return configPath;
    }

    public Object getDef() {
        return def;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public int intValue(){
        return (int) getValue();
    }

}
