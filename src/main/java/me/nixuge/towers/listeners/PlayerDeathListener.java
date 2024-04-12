package me.nixuge.towers.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.nixuge.towers.Towers;
import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.player.TowersPlayer;

public class PlayerDeathListener implements Listener {
    private static List<Material> noDrop = Arrays.asList(
        Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,
        Material.WOOD_SWORD, Material.WOOD_AXE, Material.STONE_PICKAXE
    );

    // private static Map<DamageCause, String> killMessages = new HashMap<>();
    // static {
    //     killMessages.put(DamageCause.BLOCK_EXPLOSION, "%s\u00A77 a \u00E9t\u00E9 tu\u00E9 par %s\u00A77.");
    //     killMessages.put(DamageCause.ENTITY_ATTACK, "%s\u00A77 a \u00E9t\u00E9 tu\u00E9 par %s\u00A77.");
    //     killMessages.put(DamageCause.POISON, "%s\u00A77 a \u00E9t\u00E9 tu\u00E9 par %s\u00A77.");
    //     killMessages.put(DamageCause.THORNS, "%s\u00A77 a \u00E9t\u00E9 tu\u00E9 par %s\u00A77.");
    //     killMessages.put(DamageCause.PROJECTILE, "%s\u00A77 a \u00E9t\u00E9 tu\u00E9 par %s\u00A77.");
    //     killMessages.put(DamageCause.BLOCK_EXPLOSION, "%s\u00A77 a \u00E9t\u00E9 tu\u00E9 par %s\u00A77.");
    // }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(item -> noDrop.contains(item.getType()));

        Player victim = (Player)event.getEntity().getPlayer();
        Player hitman = (Player)event.getEntity().getKiller();
        TowersPlayer towersVictim = PlayersManager.getPlayer(victim);
        TowersPlayer towersHitman = (hitman == null) ? null : PlayersManager.getPlayer(hitman);

        // Kinda dirty :/
        String message;
        EntityDamageEvent lastDamageEvent = event.getEntity().getLastDamageCause();
        if (lastDamageEvent == null) {
            message = "\u00A77 a \u00E9t\u00E9 tu\u00E9 par";
        } else {
            switch (lastDamageEvent.getCause()) {
                case BLOCK_EXPLOSION:
                    message = "\u00A77 a \u00E9t\u00E9 tu\u00E9 par";
                    break;
                case ENTITY_ATTACK:
                    message = "\u00A77 a \u00E9t\u00E9 tu\u00E9 par";
                    break;
                case POISON:
                    message = "\u00A77 a \u00E9t\u00E9 tu\u00E9 par";
                    break;
                case THORNS:
                    message = "\u00A77 a \u00E9t\u00E9 tu\u00E9 par";
                    break;
                case PROJECTILE:
                    message = "\u00A77 a \u00E9t\u00E9 tu\u00E9 par";
                    break;
                case FALL: {
                    if (hitman != null) {
                        message ="\u00A77 a \u00E9t\u00E9 tu\u00E9 par une chute et";
                    } else {
                        message = "\u00A77 a \u00E9t\u00E9 tu\u00E9 par une chute§7.";
                    }
                    break;
                }
                default:
                    message = "a été tué. This should not happen§7.";
                    break;
            }
        }

        String fullMessage = towersVictim.getColouredName() + message;
        if (message.charAt(message.length()-1) != '.') {
            // Add suffix only if message needs it.
            //  todo: switch to format, but kinda annoying if I do custom message for each case.
            if (hitman == null)
                fullMessage += " no one?";
            else
                fullMessage += " " + towersHitman.getColouredName() + "§7.";
        }
        event.setDeathMessage(fullMessage);

        Bukkit.getScheduler().runTaskLater(Towers.getInstance(), () -> victim.spigot().respawn(), 2L);
    }
}
