package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagResult;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.data.DataContainer;
import dev.benergy10.playertrolls.utils.SubscribableEvent;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Freeze extends Troll {

    private final PlayerTrolls plugin;
    private final FlagGroup flagGroup = FlagGroup.of(TrollFlags.DURATION);

    private final SubscribableEvent<PlayerMoveEvent, Player> freezeMovement = new SubscribableEvent
            .Creator<PlayerMoveEvent, Player>(PlayerMoveEvent.class)
            .ignoreCancelled(true)
            .eventTarget(PlayerEvent::getPlayer)
            .handler(event -> {
                Location from = event.getFrom();
                Location to = event.getTo();
                if (from.getWorld() != to.getWorld()) {
                    event.setCancelled(true);
                    return;
                }
                if (from.getX() == to.getX()
                        && from.getY() == to.getY()
                        && from.getZ() == to.getZ()) {
                    return;
                }
                to.set(from.getX(), from.getY(), from.getZ());
                event.setTo(to);
                event.getPlayer().setVelocity(new Vector(0, 0, 0));
            })
            .create();

    public Freeze(PlayerTrolls plugin) {
        this.plugin = plugin;
        this.freezeMovement.register(plugin);
    }

    @Override
    public @NotNull String getName() {
        return "freeze";
    }

    @Override
    protected @Nullable DataContainer start(TrollPlayer trollPlayer, FlagResult flags) {
        trollPlayer.getPlayer().setWalkSpeed(0F);
        this.freezeMovement.subscribe(trollPlayer.getPlayer());
        return new DataContainer()
                .set(TrollPlayer.STOP_TASK, trollPlayer.scheduleDeactivation(this, flags.getValue(TrollFlags.DURATION)));
    }

    @Override
    protected boolean end(TrollPlayer trollPlayer, DataContainer data) {
        this.freezeMovement.unsubscribe(trollPlayer.getPlayer());
        trollPlayer.getPlayer().setWalkSpeed(0.2F);
        return true;
    }

    @Override
    public FlagGroup getFlagGroup() {
        return this.flagGroup;
    }
}
