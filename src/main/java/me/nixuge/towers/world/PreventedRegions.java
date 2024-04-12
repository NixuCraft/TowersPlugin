package me.nixuge.towers.world;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.nixuge.configurator.maths.Area;
import me.nixuge.towers.player.TowersPlayer;
import me.nixuge.towers.teams.TeamMap;

public class PreventedRegions {
    private static final World world = Bukkit.getWorld("world");
    private static final List<Area> preventedRegions = new ArrayList<>();
    static {
        preventedRegions.add(new Area(new Location(world, 79, 205, 2), new Location(world, 83, 201, -2)));
        preventedRegions.add(new Area(new Location(world, -83, 205, -2), new Location(world, -79, 201, 2)));
        preventedRegions.add(new Area(new Location(world, -78, 195, -3), new Location(world, -72, 191, 3)));
        preventedRegions.add(new Area(new Location(world, 78, 195, 3), new Location(world, 72, 191, -3)));
        preventedRegions.add(new Area(new Location(world, -57, 195, 4), new Location(world, -52, 191, 10)));
        preventedRegions.add(new Area(new Location(world, 57, 195, -4), new Location(world, 52, 191, -10)));
        preventedRegions.add(new Area(new Location(world, 57, 195, 11), new Location(world, 52, 191, 3)));
        preventedRegions.add(new Area(new Location(world, -57, 195, -11), new Location(world, -52, 191, -3)));
    }

    // Note: this should work for canPlaceBlock.
    // However, in the original src, there's a distinction between the placed & broken blocks for a few of the first regions.
    // todo: see if there are indeed issues w that.
    public static boolean canPlayerBreakBlock(Block b, TowersPlayer towersP) {
        // Check block type
        if (b.getType() == Material.CHEST)
            return false;

        Location bLoc = b.getLocation();
        
        // Check team areas
        if (towersP.getTeam().getEnemyTeam().getTeamMap().getSpawnArea().containsBlock(bLoc))
            return false;

        // Check all other areas
        for (Area area : preventedRegions) {
            if (area.containsBlock(bLoc))
                return false;
        }

        // Otherwise all good.
        return true;
    }

    public static boolean canBreakBlock(Block b) {
        // Check block type
        if (b.getType() == Material.CHEST)
            return false;

        Location bLoc = b.getLocation();

        if (TeamMap.getBlueMap().getSpawnArea().containsBlock(bLoc)) return false;
        if (TeamMap.getRedMap().getSpawnArea().containsBlock(bLoc)) return false;

        // Check all other areas
        for (Area area : preventedRegions) {
            if (area.containsBlock(bLoc))
                return false;
        }

        // Otherwise all good.
        return true;
    }
}
