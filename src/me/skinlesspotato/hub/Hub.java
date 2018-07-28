package me.skinlesspotato.hub;

/* Class created 07/25/2018. 
Editing of this code without permission from the author is prohibited.
The following code is licensed to who it was made for.
Contact Skinlesspotato#6044 on discord for distribution rights.
*/

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.skinlesspotato.core.Core;
import me.skinlesspotato.hub.gui.GadgetsGUI;
import me.skinlesspotato.hub.listener.PlayerListener;
import me.skinlesspotato.hub.scoreboard.LobbyScoreboard;
import me.skinlesspotato.hub.util.ItemUtil;
import me.skinlesspotato.hub.util.VanishUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Hub extends JavaPlugin implements PluginMessageListener {

    private ItemUtil itemUtil;
    private VanishUtil vanishUtil;
    private LobbyScoreboard lobbyScoreboard;
    private GadgetsGUI gadgetsGUI;

    public Core core = (Core) getServer().getPluginManager().getPlugin("hCore");

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        registerClasses();
        registerEvents();
        registerBungee();
    }

    private void registerClasses() {
        vanishUtil = new VanishUtil(this);
        gadgetsGUI = new GadgetsGUI(this);
        lobbyScoreboard = new LobbyScoreboard(this);
        itemUtil = new ItemUtil(this);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener( this), this);
        pm.registerEvents(new GadgetsGUI(this), this);
    }

    private void registerBungee() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        requestPlayerCount("ALL");
        requestServerList();

        new BukkitRunnable() {
            public void run() {
                for (String server : serverList) {
                    requestPlayerCount(server);
                }
            }
        }.runTaskTimer(this, 0, 20 * 2); // Every 2 seconds, reload server player counts
    }

    private Map<String, Integer> serverPlayerCountMap = new HashMap<>();
    private String[] serverList = new String[]{};

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            String subChannel = in.readUTF();

            if (subChannel.equalsIgnoreCase("PlayerCount")) {
                String server = in.readUTF();
                int count = in.readInt();

                serverPlayerCountMap.put(server, count);
            } else if (subChannel.equalsIgnoreCase("GetServers")) {
                this.serverList = in.readUTF().split(", ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectPlayer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public void requestPlayerCount(String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        getServer().sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public void requestServerList() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServers");
        getServer().sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public int getPlayerCount(String server) {
        if (serverPlayerCountMap.get(server) == null) {
            requestPlayerCount(server);
            return 0;
        }

        return serverPlayerCountMap.get(server);
    }

    public String[] getServerList() {
        return serverList;
    }
}
