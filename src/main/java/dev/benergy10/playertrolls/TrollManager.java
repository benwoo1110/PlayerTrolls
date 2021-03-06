package dev.benergy10.playertrolls;

import com.google.common.base.Function;
import dev.benergy10.minecrafttools.events.SubscribableEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
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
            .oneTimeUse(true)
            .eventTarget(PlayerEvent::getPlayer)
            .runner(event -> {
                TrollPlayer trollPlayer = this.playerMap.remove(event.getPlayer());
                if (trollPlayer != null) {
                    trollPlayer.deactivateAll();
                }
            })
            .create();

    TrollManager(@NotNull PlayerTrolls plugin) {
        this.plugin = plugin;
        this.trollMap = new HashMap<>();
        this.playerMap = new HashMap<>();
        this.quitEvent.register(this.plugin);
    }

    public @NotNull TrollManager register(@NotNull Troll troll) {
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

    public @Nullable Troll getTroll(@Nullable String name) {
        return this.trollMap.get(name.toLowerCase());
    }

    public @NotNull Collection<Troll> getTrolls() {
        return this.trollMap.values();
    }

    public @NotNull Collection<String> getTrollNames() {
        return this.trollMap.keySet();
    }

    public @NotNull TrollPlayer getTrollPlayer(@NotNull Player player) {
        return this.playerMap.computeIfAbsent(player, (Function<Player, TrollPlayer>) input -> {
            this.quitEvent.subscribe(player);
            return new TrollPlayer(this.plugin, input);
        });
    }
}
