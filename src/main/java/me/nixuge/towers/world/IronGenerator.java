package me.nixuge.towers.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class IronGenerator extends BukkitRunnable {
    private World world = Bukkit.getWorld("world");
    @Override
    public void run() {
        world.dropItem(new Location(world, 0.5, 198, 0.5), new ItemStack(Material.IRON_INGOT));
    }
}
