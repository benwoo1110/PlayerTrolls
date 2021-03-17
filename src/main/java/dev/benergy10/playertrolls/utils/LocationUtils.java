package dev.benergy10.playertrolls.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class LocationUtils {

    public static Block getBackBlock(Location location) {
        return location.getBlock().getRelative(getFaceDirection(location).getOppositeFace());
    }

    public static BlockFace getFaceDirection(Location location) {
        double yaw = (location.getYaw() % 360) + 180;
        if (yaw < 22.5)
            return BlockFace.NORTH;
        else if (yaw < 67.5)
            return BlockFace.NORTH_EAST;
        else if (yaw < 112.5)
            return BlockFace.EAST;
        else if (yaw < 157.5)
            return BlockFace.SOUTH_EAST;
        else if (yaw < 202.5)
            return BlockFace.SOUTH;
        else if (yaw < 247.5)
            return BlockFace.SOUTH_WEST;
        else if (yaw < 292.5)
            return BlockFace.WEST;
        else if (yaw < 337.5)
            return BlockFace.NORTH_WEST;

        return BlockFace.NORTH;
    }
}
