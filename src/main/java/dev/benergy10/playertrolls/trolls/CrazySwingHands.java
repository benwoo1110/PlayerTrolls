package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagResult;
import dev.benergy10.playertrolls.data.DataContainer;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.data.DataKey;
import dev.benergy10.playertrolls.utils.PacketHelper;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class CrazySwingHands extends Troll {

    private static final DataKey<BukkitTask> SWING_TASK = DataKey.create(BukkitTask.class, "swing");

    private final PlayerTrolls plugin;
    private final FlagGroup flagGroup = FlagGroup.of(TrollFlags.DURATION);

    public CrazySwingHands(PlayerTrolls plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getName() {
        return "crazy-swing-hands";
    }

    @Override
    protected @Nullable DataContainer start(TrollPlayer trollPlayer, FlagResult flags) {
        return new DataContainer()
                .set(SWING_TASK, this.swingTask(trollPlayer.getPlayer()))
                .set(TrollPlayer.STOP_TASK, trollPlayer.scheduleDeactivation(this, flags.getValue(TrollFlags.DURATION)));
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
                0,
                5
        );
    }

    /**
     * https://wiki.vg/Protocol#Entity_Animation_.28clientbound.29
     *
     * @param player
     * @param type
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
    protected boolean end(TrollPlayer trollPlayer, DataContainer data) {
        data.get(SWING_TASK).cancel();
        return true;
    }

    @Override
    public FlagGroup getFlagGroup() {
        return this.flagGroup;
    }
}
