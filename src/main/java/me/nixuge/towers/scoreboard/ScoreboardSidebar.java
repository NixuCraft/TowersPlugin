package me.nixuge.towers.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import lombok.val;
import me.nixuge.towers.Towers;
import me.nixuge.towers.teams.TeamPoints;
import me.nixuge.towers.teams.TowersTeam;
import me.nixuge.towers.utils.Pair;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import net.megavex.scoreboardlibrary.api.sidebar.component.ComponentSidebarLayout;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;

public class ScoreboardSidebar {
    private static Map<TowersTeam, Pair<Sidebar, ComponentSidebarLayout>> sidebars = new HashMap<>();
    static {
        TowersTeam[] allTeams = TowersTeam.getAllTeams();
        for (int i = 0; i < allTeams.length; i++) {
            TowersTeam team = allTeams[i];
            sidebars.put(team, createSidebar(team));
        }
    }
    private static Sidebar getSidebar(TowersTeam team) {
        return sidebars.get(team).getKey();
    }

    private static Pair<Sidebar, ComponentSidebarLayout> createSidebar(TowersTeam team) {
        val title = Component.text("§6§lFun§f§lCraft§r§o.net");

        Sidebar sidebar = Towers.getScoreboardLibrary().createSidebar();
        sidebar.title(title);

        SidebarComponent.Builder lines = SidebarComponent.builder();
        // Note: technically doing 1 component/team even tho i only need 1/game for most
        // (eg chrono) but meh losing like ~0.5mb of ram prolly.
        lines.addBlankLine();
        lines.addStaticLine(Component.text("Tu es dans"));
        lines.addStaticLine(Component.text("l'équipe " + team.getDisplayString()));
        lines.addBlankLine();
        lines.addComponent(new ChronoComponent());
        lines.addBlankLine();
        TowersTeam[] allTeams = TowersTeam.getAllTeams();
        for (int i = 0; i < allTeams.length; i++) {
            lines.addComponent(new ScoreComponent(allTeams[i]));
        }
        lines.addStaticLine(Component.text("§7Objectif: " + TeamPoints.getMAX_POINTS() + " points"));
        lines.addBlankLine();
        lines.addStaticLine(Component.text("§7Alliances entre"));
        lines.addStaticLine(Component.text("§7équipes interdites"));

        val layout = new ComponentSidebarLayout(SidebarComponent.staticLine(title), lines.build());

        return new Pair<>(sidebar, layout);
    }
    
    // To get called when an elements is updated, meaning:
    // - when the timer goes up
    // - when a team puts a point.
    public static void updateSidebars() {
        for (Pair<Sidebar, ComponentSidebarLayout> sidebarPair : sidebars.values()) {
            sidebarPair.getValue().apply(sidebarPair.getKey());
        }
    }

    public static void addSidebarToPlayer(Player p, TowersTeam team) {
        getSidebar(team).addPlayer(p);
    }
}
