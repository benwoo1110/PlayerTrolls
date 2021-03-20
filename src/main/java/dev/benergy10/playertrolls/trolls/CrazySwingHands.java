package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.utils.Alternator;
import dev.benergy10.playertrolls.utils.PacketManager;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrazySwingHands extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(TrollFlags.DURATION);

    public CrazySwingHands(PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @Nullable TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        PacketManager packetManager = this.plugin.getPacketManager();
        if (packetManager == null) {
            Logging.warning("This troll does not work without ProtocolLib.");
            return null;
        }
        trollPlayer.scheduleDeactivation(this, flags.get(TrollFlags.DURATION));
        return new Task(this.swingTask(packetManager, trollPlayer.getPlayer()));
    }

    private BukkitTask swingTask(@NotNull PacketManager packetManager, @NotNull Player player) {
        Alternator<Integer> handType = Alternator.of(0, 3);
        return Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> packetManager.sendPacket(player, packetManager.createAnimationPacket(player, handType.get())),
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
    public boolean requiresProtocolLib() {
        return true;
    }

    private class Task extends TrollTask {

        private final BukkitTask swingTask;

        private Task(@NotNull BukkitTask swingTask) {
            this.swingTask = swingTask;
        }

        @Override
        protected boolean stop() {
            this.swingTask.cancel();
            return false;
        }
    }
}
