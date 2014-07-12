package com.ikeirnez.tenjava.redstonebatteries;

import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class Language {

    private Language(){}

    private static String PREFIX_KEY = "prefix";
    private static Properties properties = new Properties();

    static {
        Language.class.getClassLoader().getResourceAsStream("language_en.properties");
    }

    public static void load(InputStream inputStream){
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPrefix(String key, Object... args){
        return get(PREFIX_KEY) + get(key, args);
    }

    public static String get(String key, Object... args){
        if (getProperties().containsKey(key)){
            return ChatColor.translateAlternateColorCodes('&', String.format(getRaw(key), args));
        } else {
            return key;
        }
    }

    public static String getRaw(String key){
        return getProperties().getProperty(key);
    }

    public static Properties getProperties() {
        return properties;
    }
}
