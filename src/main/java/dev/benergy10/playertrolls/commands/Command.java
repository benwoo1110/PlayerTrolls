package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.BaseCommand;
import dev.benergy10.playertrolls.PlayerTrolls;
import org.jetbrains.annotations.NotNull;

public abstract class Command extends BaseCommand {

    protected final PlayerTrolls plugin;

    protected Command(@NotNull PlayerTrolls plugin) {
        this.plugin = plugin;
    }
}
