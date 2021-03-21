package dev.benergy10.playertrolls;

import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.minecrafttools.utils.TimeConverter;
import dev.benergy10.playertrolls.events.TrollActivateEvent;
import dev.benergy10.playertrolls.events.TrollDeactivateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TrollPlayer {

    private final PlayerTrolls plugin;
    private final Player player;
    private final Map<Troll, Troll.TrollTask> activeTrolls;
    private final Map<Troll, BukkitTask> scheduledDeactivation;

    TrollPlayer(@NotNull PlayerTrolls plugin, @NotNull Player player) {
        this.plugin = plugin;
        this.player = player;
        this.activeTrolls = new HashMap<>();
        this.scheduledDeactivation = new HashMap<>();
    }

    public boolean activateTroll(@NotNull Troll troll, @NotNull FlagValues flags) {
        if (!troll.isRegistered()) {
            throw new IllegalArgumentException("Troll is not registered: " + troll.getName());
        }
        if (!troll.getRequirement().hasRequiredClasses()) {
            Logging.warning("You did not meet the requirement of this troll: %s", troll.getRequirement());
            return false;
        }
        TrollActivateEvent enableEvent = new TrollActivateEvent(troll, this);
        Bukkit.getPluginManager().callEvent(enableEvent);
        if (enableEvent.isCancelled()) {
            Logging.debug("Troll activation was canceled by another plugin!");
            return false;
        }
        Logging.debug("Activating troll %s for %s...",troll, this.player);
        Troll.TrollTask runner = troll.start(this, flags);
        if (runner == null) {
            return false;
        }
        this.activeTrolls.put(troll, runner);
        return true;
    }

    public boolean deactivateTroll(@NotNull Troll troll) {
        if (!troll.isRegistered()) {
            throw new IllegalArgumentException("Troll is not registered: " + troll.getName());
        }
        Troll.TrollTask task = this.activeTrolls.remove(troll);
        if (task == null) {
            Logging.warning("No task to deactivate for: %s %s", this.player, troll);
            return false;
        }
        final BukkitTask stopTask = this.scheduledDeactivation.remove(troll);
        if (stopTask != null) {
            stopTask.cancel();
        }
        Logging.debug("Deactivating troll %s for %s...", troll, this.player);
        Bukkit.getPluginManager().callEvent(new TrollDeactivateEvent(troll, this));
        return task.stop();
    }

    public void scheduleDeactivation(@NotNull Troll troll, double seconds) {
        BukkitTask oldTask = this.scheduledDeactivation.put(troll, Bukkit.getScheduler().runTaskLater(
                this.plugin,
                () -> this.deactivateTroll(troll),
                TimeConverter.secondsToTicks(seconds)
        ));
        if (oldTask != null) {
            oldTask.cancel();
        }
    }

    public void deactivateAll() {
        this.activeTrolls.keySet().iterator().forEachRemaining(this::deactivateTroll);
    }

    public boolean isActiveTroll(@Nullable Troll troll) {
        return this.activeTrolls.containsKey(troll);
    }

    public @NotNull Collection<Troll> getActiveTrolls() {
        return this.activeTrolls.keySet();
    }

    public @NotNull Player getPlayer() {
        return player;
    }
}
