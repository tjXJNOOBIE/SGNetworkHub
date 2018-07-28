package me.skinlesspotato.hub.gui;

/* Class created 07/26/2018. 
Editing of this code without permission from the author is prohibited.
The following code is licensed to who it was made for.
Contact Skinlesspotato#6044 on discord for distribution rights.
*/

import me.skinlesspotato.hub.Hub;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GadgetsGUI implements Listener {

    private Hub plugin;
    public GadgetsGUI(Hub plugin) {
        this.plugin = plugin;
    }

    private Inventory inventory;

    public void openGadgetsGUI(Player player) {
        if (inventory == null) {
            inventory = plugin.getServer().createInventory(null, 27, ChatColor.DARK_GRAY + "Gadgets menu");

            for (int i = 0; i < 27; i++) {
                inventory.setItem(i, plugin.getItemUtil().createItemNoLore(Material.STAINED_GLASS_PANE, " "));
            }

            inventory.setItem(10, plugin.getItemUtil().createItem(Material.DRAGON_EGG, ChatColor.GOLD + "Pets", ChatColor.GRAY + "(Click to Open)"));
            inventory.setItem(13, plugin.getItemUtil().createItem(Material.ENDER_CHEST, ChatColor.GOLD + "Hats", ChatColor.GRAY + "(Click to Open)"));
            inventory.setItem(16, plugin.getItemUtil().createItem(Material.FIREWORK, ChatColor.GOLD + "Particles", ChatColor.GRAY + "(Click to Open)"));

            player.openInventory(inventory);
        } else {
            player.openInventory(inventory);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) {
            return;
        }

        if (event.getClickedInventory().getName().equalsIgnoreCase(ChatColor.DARK_GRAY + "Gadgets Menu")) {
            if (event.getCurrentItem().getType().equals(Material.DRAGON_EGG) || event.getCurrentItem().getType().equals(Material.FIREWORK) || event.getCurrentItem().getType().equals(Material.ENDER_CHEST)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "This feature is coming soon!");
            } else {
                event.setCancelled(true);
            }
        }
    }
}
