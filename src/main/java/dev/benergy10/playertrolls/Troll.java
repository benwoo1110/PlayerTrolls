package dev.benergy10.playertrolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.playertrolls.contants.DependencyRequirement;
import org.jetbrains.annotations.NotNull;

public abstract class Troll {

    protected final PlayerTrolls plugin;
    private boolean registered = false;

    protected Troll(@NotNull PlayerTrolls plugin) {
        this.plugin = plugin;
    }

    void register() {
        this.registered = true;
    }

    public boolean isRegistered() {
        return registered;
    }

    protected abstract @NotNull TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags);

    public abstract @NotNull String getName();

    public abstract @NotNull FlagGroup getFlagGroup();

    public abstract @NotNull DependencyRequirement getRequirement();

    protected abstract static class TrollTask {
        protected abstract void stop();
    }

    @Override
    public String toString() {
        return "Troll{" +
                "name=" + this.getName() +
                ", registered=" + registered +
                '}';
    }
}
