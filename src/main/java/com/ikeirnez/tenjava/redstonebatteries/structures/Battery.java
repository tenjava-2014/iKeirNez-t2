package com.ikeirnez.tenjava.redstonebatteries.structures;

import com.ikeirnez.tenjava.redstonebatteries.utilities.Cuboid;

/**
 * Created by iKeirNez on 12/07/2014.
 */
public class Battery {

    private static final int BATTERY_SIZE_X = 3, BATTERY_SIZE_Z = 4, BATTERY_MIN_SIZE_Y = 5;

    private final Cuboid cuboid;

    // 8 snow pile = 1 block

    public Battery(Cuboid cuboid){
        this.cuboid = cuboid;

        if (getCuboid().getLengthX() != BATTERY_SIZE_X || getCuboid().getLengthZ() != BATTERY_SIZE_Z || getCuboid().getLengthY() < BATTERY_MIN_SIZE_Y){
            throw new IllegalArgumentException("Cuboid does not match dimensions of a Battery.");
        }
    }

    public Cuboid getCuboid() {
        return cuboid;
    }
}
