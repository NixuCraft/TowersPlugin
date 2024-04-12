package me.nixuge.towers.scoreboard;

import org.jetbrains.annotations.NotNull;

import lombok.RequiredArgsConstructor;
import me.nixuge.towers.teams.TowersTeam;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.component.LineDrawable;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;

@RequiredArgsConstructor
public class ScoreComponent implements SidebarComponent {
    private final TowersTeam team;

    @Override
    public void draw(@NotNull LineDrawable drawable) {
        drawable.drawLine(Component.text(team.getDisplayString() + ": " + team.getTeamPoints().getPoints() + " points"));
    }
}
