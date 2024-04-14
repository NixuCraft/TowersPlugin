package me.nixuge.towers.teams;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import lombok.Setter;

public class TeamArmorStand {
    private static World world = Bukkit.getWorld("world");

    @Setter
    private TowersTeam team;
    private final ArmorStand armorStand;
    
    public TeamArmorStand(double x, double y, double z) {
        this(new Location(world, x, y, z));
    }

    public TeamArmorStand(Location loc) {
        loc.getChunk().load(false); // required to spawn an entity
        armorStand = (ArmorStand) world.spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setInvulnerable(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("Hi.");
    }

    public void updateName() {
        StringBuilder builder = new StringBuilder(team.getDisplayString() + " ");
        
        int maxPoints = TeamPoints.getMAX_POINTS();
        int teamPoints = team.getEnemyTeam().getTeamPoints().getPoints();

        builder.append(team.getChatPrefix());
        for (int i = 0; i < (maxPoints - teamPoints); i++) {
            builder.append("❤");
        }
        builder.append("§7");
        for (int i = 0; i < teamPoints; i++) {
            builder.append("❤");
        }

        armorStand.setCustomName(builder.toString());
    }
}
