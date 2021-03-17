package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagResult;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.data.DataContainer;
import dev.benergy10.playertrolls.utils.SubscribableEvent;
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

    private PlayerTrolls plugin;
    private final FlagGroup flagGroup = FlagGroup.of(TrollFlags.DURATION);

    private final SubscribableEvent<PlayerMoveEvent, Player> fireMovement = new SubscribableEvent
            .Creator<PlayerMoveEvent, Player>(PlayerMoveEvent.class)
            .ignoreCancelled(true)
            .eventTarget(PlayerEvent::getPlayer)
            .handler(event -> {
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
            .create();

    public FireTrail(PlayerTrolls plugin) {
        this.plugin = plugin;
        this.fireMovement.register(plugin);
    }

    @Override
    public @NotNull String getName() {
        return "fire-trail";
    }

    @Override
    protected @Nullable DataContainer start(TrollPlayer trollPlayer, FlagResult flags) {
        this.fireMovement.subscribe(trollPlayer.getPlayer());
        return new DataContainer()
                .set(TrollPlayer.STOP_TASK, trollPlayer.scheduleDeactivation(this, flags.getValue(TrollFlags.DURATION)));
    }

    @Override
    protected boolean end(TrollPlayer trollPlayer, DataContainer data) {
        this.fireMovement.unsubscribe(trollPlayer.getPlayer());
        return true;
    }

    @Override
    public FlagGroup getFlagGroup() {
        return flagGroup;
    }
}
