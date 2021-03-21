package dev.benergy10.playertrolls;

import com.google.common.base.Preconditions;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.minecrafttools.utils.TimeConverter;
import dev.benergy10.playertrolls.contants.ActivationResult;
import dev.benergy10.playertrolls.contants.DeactivationResult;
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

    public ActivationResult activateTroll(@NotNull Troll troll, @NotNull FlagValues flags) {
        if (!troll.isRegistered()) {
            throw new IllegalArgumentException("Troll is not registered: " + troll.getName());
        }
        if (!troll.getRequirement().hasRequiredClasses()) {
            return ActivationResult.MISSING_DEPENDENCY;
        }
        if (this.isActiveTroll(troll)) {
            return ActivationResult.ALREADY_ACTIVE;
        }
        TrollActivateEvent enableEvent = new TrollActivateEvent(troll, this);
        Bukkit.getPluginManager().callEvent(enableEvent);
        if (enableEvent.isCancelled()) {
            Logging.debug("Troll activation was canceled by another plugin!");
            return ActivationResult.CANCELLED;
        }
        Logging.debug("Activating troll %s for %s...",troll, this.player);
        Troll.TrollTask runner;
        try {
            runner = troll.start(this, flags);
        } catch (Exception e) {
            e.printStackTrace();
            return ActivationResult.ERRORED;
        }
        this.activeTrolls.put(troll, runner);
        return ActivationResult.ACTIVATED;
    }

    public DeactivationResult deactivateTroll(@NotNull Troll troll) {
        if (!troll.isRegistered()) {
            throw new IllegalArgumentException("Troll is not registered: " + troll.getName());
        }
        Troll.TrollTask task = this.activeTrolls.remove(troll);
        if (task == null) {
            return DeactivationResult.NOT_ACTIVE;
        }
        Logging.debug("Deactivating troll %s for %s...", troll, this.player);
        Bukkit.getPluginManager().callEvent(new TrollDeactivateEvent(troll, this));
        final BukkitTask stopTask = this.scheduledDeactivation.remove(troll);
        if (stopTask != null && !stopTask.isCancelled()) {
            stopTask.cancel();
        }
        try {
            task.stop();
        } catch (Exception e) {
            e.printStackTrace();
            return DeactivationResult.ERRORED;
        }
        return DeactivationResult.DEACTIVATED;
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
