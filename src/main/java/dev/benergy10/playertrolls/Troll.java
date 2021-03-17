package dev.benergy10.playertrolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagResult;
import dev.benergy10.playertrolls.data.DataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Troll {

    public abstract @NotNull String getName();

    protected abstract @Nullable DataContainer start(TrollPlayer trollPlayer, FlagResult flags);

    protected abstract boolean end(TrollPlayer trollPlayer, DataContainer data);

    public abstract FlagGroup getFlagGroup();
}
