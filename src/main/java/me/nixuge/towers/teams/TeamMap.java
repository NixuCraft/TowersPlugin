package me.nixuge.towers.teams;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.nixuge.nixutils.maths.Area;

@RequiredArgsConstructor
@Getter
public class TeamMap {
    private static World world = Bukkit.getWorld("world");
    @Getter
    private static TeamMap blueMap = new TeamMap(
        new Location(world, 75.5, 192, 0.5),
        new Area(new Location(world, 57, 195, -2), new Location(world, 78, 150, 2)),
        new Area(new Location(world, 80, 201, 1), new Location(world, 82, 201, -1))
    );
    @Getter
    private static TeamMap redMap = new TeamMap(
        new Location(world, -74.5, 192, 0.5),
        new Area(new Location(world, -57, 195, -2), new Location(world, -78, 150, 2)),
        new Area(new Location(world, -80, 201, -1), new Location(world, -82, 201, 1))
    );

    private final Location spawn;
    private final Area spawnArea;
    private final Area goalArea;
}
