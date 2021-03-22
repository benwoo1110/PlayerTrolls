package dev.benergy10.playertrolls.trolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.contants.DependencyRequirement;
import dev.benergy10.playertrolls.utils.TrollFlags;
import me.libraryaddict.disguise.DisguiseConfig;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.jetbrains.annotations.NotNull;

public class Transfiguration extends Troll {

    private static final FlagGroup FLAG_GROUP = FlagGroup.of(TrollFlags.DURATION, TrollFlags.DISGUISE_TYPE);

    public Transfiguration(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Override
    protected @NotNull TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags) {
        MobDisguise mobDisguise = new MobDisguise(DisguiseType.getType(flags.get(TrollFlags.DISGUISE_TYPE)));
        mobDisguise.setEntity(trollPlayer.getPlayer())
                .setReplaceSounds(true)
                .setViewSelfDisguise(false)
                .setNotifyBar(DisguiseConfig.NotifyBar.NONE);
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
        protected void stop() {
            this.mobDisguise.removeDisguise();
        }
    }
}
