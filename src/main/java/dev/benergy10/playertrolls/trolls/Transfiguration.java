package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.utils.DependencyRequirement;
import dev.benergy10.playertrolls.utils.TrollFlags;
import me.libraryaddict.disguise.DisguiseConfig;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Transfiguration extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(TrollFlags.DURATION);

    public Transfiguration(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @Nullable TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        MobDisguise mobDisguise = new MobDisguise(DisguiseType.CREEPER);
        mobDisguise.setEntity(trollPlayer.getPlayer());
        mobDisguise.setReplaceSounds(true);
        mobDisguise.setViewSelfDisguise(false);
        mobDisguise.setNotifyBar(DisguiseConfig.NotifyBar.NONE);
        mobDisguise.startDisguise();
        trollPlayer.scheduleDeactivation(this, flags.get(TrollFlags.DURATION));
        return new Task(mobDisguise);
    }

    @Override
    public @NotNull String getName() {
        return "transfiguration";
    }

    @Override
    public @NotNull FlagGroup getFlagGroup() {
        return FLAG_GROUP;
    }

    @Override
    public @NotNull DependencyRequirement getRequirement() {
        return DependencyRequirement.LIB_DISGUISES;
    }

    private class Task extends TrollTask {

        private final MobDisguise mobDisguise;

        private Task(@NotNull MobDisguise mobDisguise) {
            this.mobDisguise = mobDisguise;
        }

        @Override
        protected boolean stop() {
            return this.mobDisguise.removeDisguise();
        }
    }
}
