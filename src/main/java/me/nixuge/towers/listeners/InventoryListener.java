package me.nixuge.towers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    // @EventHandler
    // public void onInventoryClick(InventoryClickEvent event) {
    //     if (event.getClick() == ClickType.DOUBLE_CLICK) {
    //         event.setCancelled(true);
    //         return;
    //     }
    //     Inventory inv = event.getClickedInventory();
    //     InventoryType type = inv.getType();
    //     if (type != InventoryType.CHEST)
    //         return;


    //     Player p = (Player)event.getWhoClicked();

    //     // Note: TO TEST, not sure if I should use getCursor or getCurrentItem
    //     Bukkit.broadcastMessage("cursor: " + event.getCursor());
    //     Bukkit.broadcastMessage("currentItem: " + event.getCurrentItem());
    //     ItemStack cursor = event.getCursor();
    //     ItemStack currentItem = event.getCurrentItem();
    //     // NOTE: THIS MUST BREAK W THE HOTBAR, TODO: SHITTY CHECKS LIKE VALETE.
    //     // If nothing in cursor, picking up item, so allow
    //     if (cursor == null)
    //         return;
    //     // If nothing in chest slot tho, since we already made sure there's smth in the cursor, so placing in chest, disallow
    //     if (currentItem == null) {
    //         p.sendMessage("Can't place items in chests.");
    //         event.setCancelled(true);
    //         return;
    //     }
    //     // However, if both slots of same type, allow to not be annoying.
    //     if (currentItem.getType() == cursor.getType()) {
    //         return;
    //     }
    //     // Otherwise, if both non null but not of same type, obv disallow.
    //     p.sendMessage("Can't place items in chests.");
    //     event.setCancelled(true);
    //     TODO: WEIRD COOLDOWN THING
    // }
    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        // TODO: See if this would work (w already existing stacks for ex.)
        // event.get
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().getType() == InventoryType.CHEST) {
            Player p = (Player)event.getWhoClicked();
            p.sendMessage("Can't place items in chests.");
            event.setCancelled(true);
        }
    }
}
