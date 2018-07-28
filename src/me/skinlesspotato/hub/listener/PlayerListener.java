package me.skinlesspotato.hub.listener;

/* Class created 07/25/2018. 
Editing of this code without permission from the author is prohibited.
The following code is licensed to who it was made for.
Contact Skinlesspotato#6044 on discord for distribution rights.
*/

import me.skinlesspotato.hub.Hub;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

    private Hub plugin;
    public PlayerListener(Hub plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage("");

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);

        Location location = player.getLocation();
        location.setX(plugin.getConfig().getInt("spawnx"));
        location.setY(plugin.getConfig().getInt("spawny"));
        location.setZ(plugin.getConfig().getInt("spawnz"));
        player.teleport(location);

        player.getInventory().clear();

        player.getInventory().setItem(0, plugin.getItemUtil().createItem(Material.SLIME_BALL, "&6&lGadgets", "&eClick to vanish all players!"));
        player.getInventory().setItem(4, plugin.getItemUtil().createItem(Material.WATCH, "&3&lServers", "&eClick to open the servers menu!"));
        player.getInventory().setItem(8, plugin.getItemUtil().createItem(Material.GOLD_INGOT, "&a&lHide players", "&eClick to open the shop menu!"));

        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Welcome to the SGNetwork!");

        plugin.getLobbyScoreboard().setScoreboard(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo().getBlock().getType() == Material.STONE_PLATE) {
            event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(3));
            event.getPlayer().setVelocity(new Vector(event.getPlayer().getVelocity().getX(), 1.0D, event.getPlayer().getVelocity().getZ()));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null) return;
        if (event.getItem().getType().equals(Material.AIR)) return;

        if (event.getItem().getType().equals(Material.SLIME_BALL)) {
            plugin.getGadgetsGUI().openGadgetsGUI(player);
        } else if (event.getItem().getType().equals(Material.GOLD_INGOT)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                plugin.getVanishUtil().toggleVanish(player);
            }
        }
    }
}
