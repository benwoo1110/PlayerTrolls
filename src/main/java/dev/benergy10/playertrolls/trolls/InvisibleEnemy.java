package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.utils.PacketManager;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InvisibleEnemy extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(TrollFlags.DO_DAMAGE, TrollFlags.DURATION);

    public InvisibleEnemy(PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @Nullable TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        PacketManager packetManager = this.plugin.getPacketManager();
        if (packetManager == null) {
            Logging.warning("ProtocolLib not detected! Troll will not work.");
            return null;
        }
        trollPlayer.scheduleDeactivation(this, flags.get(TrollFlags.DURATION));
        return new Task(this.damageTask(packetManager, trollPlayer.getPlayer(), flags.get(TrollFlags.DO_DAMAGE)));
    }

    private BukkitTask damageTask(PacketManager packetManager, Player player, boolean doDamage) {
        return Bukkit.getScheduler().runTaskTimer(
                this.plugin,
                () -> {
                    packetManager.sendPacket(player, packetManager.createAnimationPacket(player, 1));
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
    public @NotNull String getName() {
        return "invisible-enemy";
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

        private final BukkitTask damageTask;

        private Task(BukkitTask damageTask) {
            this.damageTask = damageTask;
        }

        @Override
        protected boolean stop() {
            this.damageTask.cancel();
            return false;
        }
    }
}
