package dev.benergy10.playertrolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Troll {

    protected final PlayerTrolls plugin;
    private boolean registered = false;

    protected Troll(PlayerTrolls plugin) {
        this.plugin = plugin;
    }

    void register() {
        this.registered = true;
    }

    public boolean isRegistered() {
        return registered;
    }

    protected abstract @Nullable TrollTask start(TrollPlayer trollPlayer, FlagResult flags);

    public abstract @NotNull String getName();

    public abstract @NotNull FlagGroup getFlagGroup();

    public abstract boolean requiresProtocolLib();

    protected abstract static class TrollTask {
        protected abstract boolean stop();
    }
}
