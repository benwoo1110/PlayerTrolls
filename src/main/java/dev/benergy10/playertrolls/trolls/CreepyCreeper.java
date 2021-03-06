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
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

public class CreepyCreeper extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(TrollFlags.DO_DAMAGE, TrollFlags.EXPLODE_BLOCKS);

    private final SubscribableEvent<EntityExplodeEvent, Entity> preventExplodeBlock = new SubscribableEvent
            .Creator<EntityExplodeEvent, Entity>(EntityExplodeEvent.class)
            .oneTimeUse(true)
            .eventTarget(EntityEvent::getEntity)
            .runner(event -> event.setCancelled(true))
            .register(this.plugin);

    private final SubscribableEvent<EntityDamageByEntityEvent, Entity> preventPlayerDamage = new SubscribableEvent
            .Creator<EntityDamageByEntityEvent, Entity>(EntityDamageByEntityEvent.class)
            .eventTarget(EntityDamageByEntityEvent::getDamager)
            .runner(event -> event.setDamage(0))
            .register(this.plugin);

    public CreepyCreeper(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @NotNull TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        Player player = trollPlayer.getPlayer();
        Location location = player.getLocation().add(player.getLocation().getDirection().multiply(-1));
        Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER);
        creeper.setTarget(player);
        if (!flags.get(TrollFlags.EXPLODE_BLOCKS)) {
            this.preventExplodeBlock.subscribe(creeper);
        }
        if (!flags.get(TrollFlags.DO_DAMAGE)) {
            this.preventPlayerDamage.subscribe(creeper);
        }
        trollPlayer.scheduleDeactivation(this, 5);
        return new Task(creeper);
    }

    @Override
    public @NotNull String getName() {
        return "creepy-creeper";
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

        private final Creeper creeper;

        private Task(Creeper creeper) {
            this.creeper = creeper;
        }

        @Override
        protected void stop() {
            preventPlayerDamage.unsubscribe(this.creeper);
        }
    }
}
