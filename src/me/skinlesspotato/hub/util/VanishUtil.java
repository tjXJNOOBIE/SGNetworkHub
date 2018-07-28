package me.skinlesspotato.hub.util;

/* Class created 07/27/2018. 
Editing of this code without permission from the author is prohibited.
The following code is licensed to who it was made for.
Contact Skinlesspotato#6044 on discord for distribution rights.
*/

import me.skinlesspotato.hub.Hub;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class VanishUtil {

    private Hub plugin;
    public VanishUtil(Hub plugin) {
        this.plugin = plugin;
    }

    public void toggleVanish(Player player) {
        for (Player online : plugin.getServer().getOnlinePlayers()) {
            if (player.canSee(online)) {
                player.hidePlayer(online);
            } else {
                player.showPlayer(online);
            }
        }
        player.sendMessage(ChatColor.YELLOW + "You have toggled vanish for all players.");
    }
}
