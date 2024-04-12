package me.nixuge.towers.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantItemListener implements Listener {
    @EventHandler
    public void onEchantItem(EnchantItemEvent event) {
        Player p = event.getEnchanter();
        if (event.getItem().getType().equals(Material.BOW)) {
            p.sendMessage("Can't enchant bows.");
            event.setCancelled(true);
        }
    }
}
