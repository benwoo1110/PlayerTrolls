package dev.benergy10.playertrolls;

import dev.benergy10.minecrafttools.commands.flags.FlagResult;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.minecrafttools.utils.TimeConverter;
import dev.benergy10.playertrolls.data.DataContainer;
import dev.benergy10.playertrolls.data.DataKey;
import dev.benergy10.playertrolls.events.TrollDeactivateEvent;
import dev.benergy10.playertrolls.events.TrollActivateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TrollPlayer {

    public static final DataKey<BukkitTask> STOP_TASK = DataKey.create(BukkitTask.class, "stop");

    private final PlayerTrolls plugin;
    private final Player player;
    private final Map<Troll, DataContainer> activeTrolls;

    public TrollPlayer(PlayerTrolls plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        activeTrolls = new HashMap<>();
    }

    public boolean activateTroll(Troll troll, FlagResult flags) {
        TrollActivateEvent enableEvent = new TrollActivateEvent(troll, this);
        Bukkit.getPluginManager().callEvent(enableEvent);
        if (enableEvent.isCancelled()) {
            Logging.debug("Troll activation was canceled by another plugin!");
            return false;
        }
        Logging.debug("Activating troll '%s' for %s...",troll.getName(), this.player.getName());
        DataContainer data = troll.start(this, flags);
        if (data == null) {
            return false;
        }
        this.activeTrolls.put(troll, data);
        return true;
    }

    public boolean deactivateTroll(Troll troll) {
        Logging.debug("Deactivating troll '%s' for %s...", troll.getName(), this.player.getName());
        DataContainer data = this.activeTrolls.remove(troll);
        if (data == null) {
            return false;
        }
        BukkitTask stopTask = data.get(STOP_TASK);
        if (stopTask != null && !stopTask.isCancelled()) {
            stopTask.cancel();
        }
        Bukkit.getPluginManager().callEvent(new TrollDeactivateEvent(troll, this));
        return troll.end(this, data);
    }

    public BukkitTask scheduleDeactivation(Troll troll, int seconds) {
        return Bukkit.getScheduler().runTaskLater(
                this.plugin,
                () -> this.deactivateTroll(troll),
                TimeConverter.secondsToTicks(seconds)
        );
    }

    public void deactivateAll() {
        this.activeTrolls.keySet().forEach(this::deactivateTroll);
    }

    public boolean isActiveTroll(Troll troll) {
        return this.activeTrolls.containsKey(troll);
    }

    public Set<Troll> getActiveTrolls() {
        return this.activeTrolls.keySet();
    }

    public Player getPlayer() {
        return player;
    }
}
