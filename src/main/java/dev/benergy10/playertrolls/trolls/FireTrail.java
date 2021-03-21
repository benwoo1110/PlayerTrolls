package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.minecrafttools.events.SubscribableEvent;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.utils.DependencyRequirement;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireTrail extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(TrollFlags.DURATION);

    private final SubscribableEvent<PlayerMoveEvent, Player> fireMovement = new SubscribableEvent
            .Creator<PlayerMoveEvent, Player>(PlayerMoveEvent.class)
            .ignoreCancelled(true)
            .eventTarget(PlayerEvent::getPlayer)
            .runner(event -> {
                Location from = event.getFrom();
                Location to = event.getTo();
                if (to.getBlockX() == from.getBlockX() && to.getBlockZ() == from.getBlockZ()) {
                    return;
                }
                Block block = from.getBlock();
                if (block.getType() != Material.FIRE) {
                    Bukkit.getScheduler().runTaskLater(this.plugin, () -> block.setType(Material.FIRE), 4);
                }
            })
            .register(this.plugin);

    public FireTrail(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @Nullable TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        Player player = trollPlayer.getPlayer();
        trollPlayer.scheduleDeactivation(this, flags.get(TrollFlags.DURATION));
        this.fireMovement.subscribe(player);
        return new Task(player);
    }

    @Override
    public @NotNull String getName() {
        return "fire-trail";
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
        protected boolean stop() {
            fireMovement.unsubscribe(this.player);
            return true;
        }
    }
}
