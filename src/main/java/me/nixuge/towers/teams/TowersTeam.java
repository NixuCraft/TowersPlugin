package me.nixuge.towers.teams;

import lombok.Getter;

public enum TowersTeam {
    RED("Red", "\u00A7c", TeamMap.getRedMap(), new TeamPoints()),
    BLUE("Blue", "\u00A73", TeamMap.getBlueMap(), new TeamPoints());

    @Getter
    private final String displayName;
    @Getter
    private final String chatPrefix;
    @Getter
    private final TeamMap teamMap;
    @Getter
    private final TeamPoints teamPoints;
    TowersTeam(String displayName, String chatPrefix, TeamMap teamMap, TeamPoints teamPoints) {
        this.displayName = displayName;
        this.chatPrefix = chatPrefix;
        this.teamMap = teamMap;
        this.teamPoints = teamPoints;
        this.teamPoints.setTeam(this); // Kinda dirty, can't set as TeamPoint arg.
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
