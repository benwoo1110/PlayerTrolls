package dev.benergy10.playertrolls;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class TrollPlayerManager {

    private final PlayerTrolls plugin;
    private final Map<Player, TrollPlayer> playerMap;

    public TrollPlayerManager(PlayerTrolls plugin) {
        this.plugin = plugin;
        this.playerMap = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(new PLayerListener(), this.plugin);
    }

    public TrollPlayer getTrollPlayer(String playerName) {
        return this.getTrollPlayer(Bukkit.getPlayerExact(playerName));
    }

    public TrollPlayer getTrollPlayer(Player player) {
        return this.playerMap.get(player);
    }

    private class PLayerListener implements Listener {
        @EventHandler
        public void onJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            playerMap.put(player, new TrollPlayer(plugin, player));
        }

        @EventHandler
        public void onQuit(PlayerQuitEvent event) {
            TrollPlayer trollPlayer = playerMap.remove(event.getPlayer());
            if (trollPlayer != null) {
                trollPlayer.deactivateAll();
            }
        }
    }
}
