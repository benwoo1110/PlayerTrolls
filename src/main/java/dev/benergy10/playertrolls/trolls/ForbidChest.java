package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.utils.SubscribableEvent;
import dev.benergy10.playertrolls.utils.TrollFlags;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class ForbidChest extends Troll {

    private static final Collection<Material> chestMaterials = Collections.unmodifiableSet(
            new HashSet<Material>() {{
                add(Material.CHEST);
                add(Material.ENDER_CHEST);
                add(Material.TRAPPED_CHEST);
                add(Material.CHEST_MINECART);
                add(Material.LEGACY_CHEST);
            }}
    );

    private final FlagGroup flagGroup = FlagGroup.of(TrollFlags.DURATION);

    private final SubscribableEvent<PlayerInteractEvent, Player> blockChestInteract = new SubscribableEvent
            .Creator<PlayerInteractEvent, Player>(PlayerInteractEvent.class)
            .eventTarget(PlayerEvent::getPlayer)
            .handler(event -> {
                if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                    return;
                }
                final Block clickedBlock = event.getClickedBlock();
                if (clickedBlock == null) {
                    return;
                }
                if (chestMaterials.contains(event.getClickedBlock().getType())) {
                    event.setCancelled(true);
                }
            })
            .create();

    public ForbidChest(PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @Nullable TrollTask start(TrollPlayer trollPlayer, FlagValues flags) {
        this.blockChestInteract.subscribe(trollPlayer.getPlayer());
        trollPlayer.scheduleDeactivation(this, flags.get(TrollFlags.DURATION));
        return new Task(trollPlayer.getPlayer());
    }

    @Override
    public @NotNull String getName() {
        return "forbid-chest";
    }

    @Override
    public @NotNull FlagGroup getFlagGroup() {
        return this.flagGroup;
    }

    @Override
    public boolean requiresProtocolLib() {
        return false;
    }

    private class Task extends TrollTask {

        private final Player player;

        private Task(Player player) {
            this.player = player;
        }

        @Override
        protected boolean stop() {
            blockChestInteract.unsubscribe(this.player);
            return false;
        }
    }
}