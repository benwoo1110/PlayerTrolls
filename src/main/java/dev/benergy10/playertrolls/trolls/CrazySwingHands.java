package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.utils.PacketHelper;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class CrazySwingHands extends Troll {

    private final FlagGroup flagGroup = FlagGroup.of(TrollFlags.DURATION);

    public CrazySwingHands(PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @Nullable TrollTask start(TrollPlayer trollPlayer, FlagValues flags) {
        trollPlayer.scheduleDeactivation(this, flags.get(TrollFlags.DURATION));
        return new Task(this.swingTask(trollPlayer.getPlayer()));
    }

    private BukkitTask swingTask(Player player) {
        return Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                new Runnable() {
                    private int type = 0;
                    @Override
                    public void run() {
                        sendHandSwingPacket(player, type);
                        type = (type == 0) ? 3 : 0;
                    }
                },
                0, 5
        );
    }

    /**
     * https://wiki.vg/Protocol#Entity_Animation_.28clientbound.29
     */
    private void sendHandSwingPacket(Player player, int type) {
        try {
            this.plugin.getProtocolManager().sendServerPacket(
                    player,
                    PacketHelper.createAnimationPacket(player, type)
            );
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotNull String getName() {
        return "crazy-swing-hands";
    }

    @Override
    public @NotNull FlagGroup getFlagGroup() {
        return this.flagGroup;
    }

    @Override
    public boolean requiresProtocolLib() {
        return true;
    }

    private class Task extends TrollTask {

        private final BukkitTask swingTask;

        private Task(BukkitTask swingTask) {
            this.swingTask = swingTask;
        }

        @Override
        protected boolean stop() {
            this.swingTask.cancel();
            return false;
        }
    }
}
