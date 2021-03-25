package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.minecrafttools.utils.TimeConverter;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.enums.DependencyRequirement;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

public class LightingStrike extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(
            TrollFlags.REPEAT, TrollFlags.INTERVAL,
            TrollFlags.DO_DAMAGE, TrollFlags.INTENSITY
    );

    public LightingStrike(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @NotNull TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        final StrikingTask strikingTask = new StrikingTask();
        strikingTask.trollPlayer = trollPlayer;
        strikingTask.max = flags.get(TrollFlags.REPEAT);
        strikingTask.interval = TimeConverter.secondsToTicks(flags.get(TrollFlags.INTERVAL));
        strikingTask.intensity = flags.get(TrollFlags.INTENSITY);
        strikingTask.withDamage = flags.get(TrollFlags.DO_DAMAGE);
        return new Task(strikingTask.run());
    }

    private class StrikingTask {

        TrollPlayer trollPlayer;
        int counter = 0;
        int max;
        long interval;
        int intensity;
        boolean withDamage;

        @NotNull BukkitTask run() {
            return Bukkit.getScheduler().runTaskTimer(LightingStrike.this.plugin, () -> {
                strikeLightning(trollPlayer.getPlayer().getLocation(), withDamage, intensity);
                if (counter++ > max) {
                    trollPlayer.deactivateTroll(LightingStrike.this);
                }
            }, 0, interval);
        }

        private void strikeLightning(@NotNull Location location, boolean withDamage, int intensity) {
            IntStream.range(0, intensity).forEach(i -> {
                if (withDamage) {
                    location.getWorld().strikeLightning(location);
                } else {
                    location.getWorld().strikeLightningEffect(location);
                }
            });
        }
    }

    @Override
    public @NotNull String getName() {
        return "lightning-strike";
    }

    @Override
    public @NotNull FlagGroup getFlagGroup() {
        return FLAG_GROUP;
    }

    @Override
    public @NotNull DependencyRequirement getRequirement() {
        return DependencyRequirement.NONE;
    }

    private class Task extends TrollTask {

        private final BukkitTask strikingTask;

        private Task(@NotNull BukkitTask strikingTask) {
            this.strikingTask = strikingTask;
        }

        @Override
        protected void stop() {
            this.strikingTask.cancel();
        }
    }
}
