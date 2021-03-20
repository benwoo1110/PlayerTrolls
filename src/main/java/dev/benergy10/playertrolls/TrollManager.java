package dev.benergy10.playertrolls;

import com.google.common.base.Function;
import dev.benergy10.playertrolls.utils.SubscribableEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TrollManager {

    private final PlayerTrolls plugin;
    private final Map<String, Troll> trollMap;
    private Map<Player, TrollPlayer> playerMap;

    private final SubscribableEvent<PlayerQuitEvent, Player> quitEvent = new SubscribableEvent
            .Creator<PlayerQuitEvent, Player>(PlayerQuitEvent.class)
            .autoUnsubscribe(true)
            .eventTarget(PlayerEvent::getPlayer)
            .handler(event -> {
                TrollPlayer trollPlayer = this.playerMap.remove(event.getPlayer());
                if (trollPlayer != null) {
                    trollPlayer.deactivateAll();
                }
            })
            .create();

    public TrollManager(PlayerTrolls plugin) {
        this.plugin = plugin;
        this.trollMap = new HashMap<>();
        this.playerMap = new HashMap<>();
        this.quitEvent.register(this.plugin);
    }

    public TrollManager register(Troll troll) {
        String trollName = troll.getName().toLowerCase();
        if (troll.isRegistered()) {
            throw new IllegalArgumentException("This troll is already registered: " + trollName);
        }
        if (this.trollMap.containsKey(trollName)) {
            throw new IllegalArgumentException("Duplicate troll name: " + trollName);
        }
        this.trollMap.put(trollName, troll);
        troll.register();
        return this;
    }

    public Troll getTroll(String name) {
        return this.trollMap.get(name.toLowerCase());
    }

    public Collection<Troll> getTrolls() {
        return this.trollMap.values();
    }

    public Collection<String> getTrollNames() {
        return this.trollMap.keySet();
    }

    public TrollPlayer getTrollPlayer(@Nullable Player player) {
        if (player == null) {
            return null;
        }
        return this.playerMap.computeIfAbsent(player, (Function<Player, TrollPlayer>) input -> {
            this.quitEvent.subscribe(player);
            return new TrollPlayer(this.plugin, input);
        });
    }

    public TrollPlayer getTrollPlayer(@Nullable String playerName) {
        if (playerName == null) {
            return null;
        }
        return this.getTrollPlayer(Bukkit.getPlayerExact(playerName));
    }
}
