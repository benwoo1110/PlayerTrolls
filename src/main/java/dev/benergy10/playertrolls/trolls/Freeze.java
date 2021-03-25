package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.minecrafttools.events.SubscribableEvent;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.enums.DependencyRequirement;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class Freeze extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(TrollFlags.DURATION);

    private final SubscribableEvent<PlayerMoveEvent, Player> freezeMovement = new SubscribableEvent
            .Creator<PlayerMoveEvent, Player>(PlayerMoveEvent.class)
            .ignoreCancelled(true)
            .eventTarget(PlayerEvent::getPlayer)
            .runner(event -> {
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
            .register(this.plugin);

    public Freeze(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @NotNull TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        Player player = trollPlayer.getPlayer();
        player.setWalkSpeed(0F);
        this.freezeMovement.subscribe(player);
        trollPlayer.scheduleDeactivation(this, flags.get(TrollFlags.DURATION));
        return new Task(player);
    }

    @Override
    public @NotNull String getName() {
        return "freeze";
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

        private final Player player;

        private Task(Player player) {
            this.player = player;
        }

        @Override
        protected void stop() {
            freezeMovement.unsubscribe(this.player);
            this.player.setWalkSpeed(0.2F);
        }
    }
}
