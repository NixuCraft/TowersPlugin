package me.nixuge.towers.teams;

import java.util.Arrays;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import lombok.Getter;
import lombok.val;

public enum TowersTeam {
    RED("Red", "\u00A7c", Color.fromBGR(0, 0, 255), TeamMap.getRedMap(), new TeamPoints()),
    BLUE("Blue", "\u00A73", Color.fromBGR(255, 0, 0), TeamMap.getBlueMap(), new TeamPoints());

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
    private ItemStack[] armor;
    TowersTeam(String displayName, String chatPrefix, Color color, TeamMap teamMap, TeamPoints teamPoints) {
        this.displayName = displayName;
        this.chatPrefix = chatPrefix;
        this.color = color;
        this.teamMap = teamMap;
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
