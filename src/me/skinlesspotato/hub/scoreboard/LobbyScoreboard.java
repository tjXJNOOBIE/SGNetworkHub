package me.skinlesspotato.hub.scoreboard;

/* Class created 07/25/2018. 
Editing of this code without permission from the author is prohibited.
The following code is licensed to who it was made for.
Contact Skinlesspotato#6044 on discord for distribution rights.
*/

import me.skinlesspotato.hub.Hub;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LobbyScoreboard {

    private Hub plugin;
    public LobbyScoreboard(Hub plugin) {
        this.plugin = plugin;
    }

    private Date date = new Date();
    private SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY");
    private String formattedDate = df.format(date);

    public Scoreboard setScoreboard(Player player) {
        Scoreboard board = plugin.getServer().getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("noflicker", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "SGNetwork");

        Score filler1 = obj.getScore(ChatColor.DARK_AQUA + "");
        filler1.setScore(11);

        Score youFiller = obj.getScore(ChatColor.GRAY + "» " + ChatColor.YELLOW + "You");
        youFiller.setScore(10);

        Team playerNameTeam = board.registerNewTeam("playerNameTeam");
        playerNameTeam.addEntry(ChatColor.BLUE.toString());
        playerNameTeam.setPrefix(player.getName());
        obj.getScore(ChatColor.BLUE.toString()).setScore(9);

        Score filler2 = obj.getScore(ChatColor.STRIKETHROUGH + "");
        filler2.setScore(8);

        Score playersFiller = obj.getScore(ChatColor.GRAY + "» " + ChatColor.YELLOW + "Players");
        playersFiller.setScore(7);

        Team playersTeam = board.registerNewTeam("playersTeam");
        playersTeam.addEntry(ChatColor.DARK_RED.toString());
        playersTeam.setPrefix(ChatColor.WHITE + "" + plugin.getPlayerCount("ALL"));
        obj.getScore(ChatColor.DARK_RED.toString()).setScore(6);

        Score filler3 = obj.getScore(ChatColor.BLACK + "");
        filler3.setScore(5);

        Score serverFiller = obj.getScore(ChatColor.GRAY + "» " + ChatColor.YELLOW + "Server");
        serverFiller.setScore(4);

        Score serverName = obj.getScore(plugin.getServer().getServerName());
        serverName.setScore(3);

        Score filler4 = obj.getScore(ChatColor.AQUA + "");
        filler4.setScore(2);

        Score serverDate = obj.getScore(ChatColor.GRAY + formattedDate);
        serverDate.setScore(1);

        player.setScoreboard(board);

        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.requestPlayerCount("ALL");
                board.getTeam("playersTeam").setPrefix(ChatColor.WHITE + "" + plugin.getPlayerCount("ALL"));
            }
        }.runTaskTimer(plugin, 0, 20);

        return board;
    }
}
