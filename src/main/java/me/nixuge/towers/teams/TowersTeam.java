package me.nixuge.towers.teams;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import lombok.Getter;
import lombok.val;

public enum TowersTeam {
    RED("Red", "\u00A7c", Color.fromBGR(0, 0, 255), TeamMap.getRedMap(), new TeamArmorStand(-80.5, 202.5, 0.5), new TeamPoints()),
    BLUE("Blue", "\u00A73", Color.fromBGR(255, 0, 0), TeamMap.getBlueMap(), new TeamArmorStand(81.5, 202.5, 0.5), new TeamPoints());

    static {
        // Note: this relies on the fast statics are executed AFTER all the constructor for the enum values.
        // Not sure if this is JVM dependant or not, but I certainly hope it's not.
        // I can't use this in the constructor because updateName calls getEnemyTeam, which requires
        // all teams to be constructed first.
        TowersTeam[] teams = getAllTeams();
        for (int i = 0; i < teams.length; i++) {
            teams[i].getArmorStand().updateName();
        }
    }

    @Getter
    private final String displayName;
    @Getter
    private final String chatPrefix;
    @Getter
    private final Color color;
    @Getter
    private final TeamMap teamMap;
    @Getter
    private final TeamPoints teamPoints;
    @Getter
    private final TeamArmorStand armorStand;
    @Getter
    private ItemStack[] armor;
    TowersTeam(String displayName, String chatPrefix, Color color, TeamMap teamMap, TeamArmorStand armorStand, TeamPoints teamPoints) {
        this.displayName = displayName;
        this.chatPrefix = chatPrefix;
        this.color = color;
        this.teamMap = teamMap;
        this.armorStand = armorStand;
        this.armorStand.setTeam(this); // Same as TeamPoint. Note that this also needs an update in static{}
        this.teamPoints = teamPoints;
        this.teamPoints.setTeam(this); // Kinda dirty, can't set as TeamPoint arg.
        this.genArmor();
    }

    private void genArmor() {
        armor = new ItemStack[4];
        int i = 0;
        for (Material material : Arrays.asList(Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET)) {
            val is = new ItemStack(material);

            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) is.getItemMeta();
            leatherMeta.setColor(this.color);
            is.setItemMeta(leatherMeta);

            armor[i] = is;

            i++;
        }
    }

    public String getDisplayString() {
        return chatPrefix + displayName + "§r";
    }

    // To remove if switching to 3+ teams system.
    public TowersTeam getEnemyTeam() {
        switch (this) {
            case BLUE:
                return RED;
            case RED:
                return BLUE;
            default:
                return BLUE; // Stupid but required.
        }
    }

    public static TowersTeam[] getAllTeams() {
        return values();
    }
}
