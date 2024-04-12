package me.nixuge.towers.scoreboard;

import org.jetbrains.annotations.NotNull;

import me.nixuge.towers.Towers;
import net.kyori.adventure.text.Component;
import net.megavex.scoreboardlibrary.api.sidebar.component.LineDrawable;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;

public class ChronoComponent implements SidebarComponent {
    @Override
    public void draw(@NotNull LineDrawable drawable) {
        drawable.drawLine(Component.text("Chrono §6" + Towers.getGameManager().getFormattedGameTime() + "s"));
    }
}
