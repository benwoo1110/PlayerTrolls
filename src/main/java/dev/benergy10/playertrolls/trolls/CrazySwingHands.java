package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.minecrafttools.utils.Alternator;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.contants.DependencyRequirement;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class CrazySwingHands extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(TrollFlags.DURATION);

    public CrazySwingHands(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @NotNull TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        trollPlayer.scheduleDeactivation(this, flags.get(TrollFlags.DURATION));
        return new Task(this.swingTask(trollPlayer.getPlayer()));
    }

    private BukkitTask swingTask(@NotNull Player player) {
        Alternator<Boolean> handType = Alternator.ofBoolean();
        return Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> {
                    if (handType.get()) {
                        player.swingMainHand();
                    } else {
                        player.swingOffHand();
                    }
                },
                0, 5
        );
    }

    @Override
    public @NotNull String getName() {
        return "crazy-swing-hands";
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

        private final BukkitTask swingTask;

        private Task(@NotNull BukkitTask swingTask) {
            this.swingTask = swingTask;
        }

        @Override
        protected void stop() {
            this.swingTask.cancel();
        }
    }
}
