package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagResult;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.data.DataContainer;
import dev.benergy10.playertrolls.data.DataKey;
import dev.benergy10.playertrolls.utils.PacketHelper;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public class InvisibleEnemy extends Troll {

    private static final DataKey<BukkitTask> DAMAGE_TASK = DataKey.create(BukkitTask.class, "damage");

    private final PlayerTrolls plugin;
    private final FlagGroup flagGroup = FlagGroup.of(TrollFlags.DO_DAMAGE, TrollFlags.DURATION);

    public InvisibleEnemy(PlayerTrolls plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getName() {
        return "invisible-enemy";
    }

    @Override
    protected @Nullable DataContainer start(TrollPlayer trollPlayer, FlagResult flags) {
        return new DataContainer()
                .set(DAMAGE_TASK, this.damageTask(trollPlayer.getPlayer(), flags.getValue(TrollFlags.DO_DAMAGE)))
                .set(TrollPlayer.STOP_TASK, trollPlayer.scheduleDeactivation(this, flags.getValue(TrollFlags.DURATION)));
    }

    private BukkitTask damageTask(Player player, boolean doDamage) {
        return Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> {
                    try {
                        this.plugin.getProtocolManager().sendServerPacket(
                                player,
                                PacketHelper.createAnimationPacket(player, 1)
                        );
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 5, 1);
                    if (doDamage) {
                        player.damage(0.5);
                    }
                },
                0,
                18
        );
    }

    @Override
    protected boolean end(TrollPlayer trollPlayer, DataContainer data) {
        data.get(DAMAGE_TASK).cancel();
        return true;
    }

    @Override
    public FlagGroup getFlagGroup() {
        return this.flagGroup;
    }
}
