package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.BaseCommand;
import dev.benergy10.playertrolls.PlayerTrolls;

public abstract class Command extends BaseCommand {

    protected final PlayerTrolls plugin;

    protected Command(PlayerTrolls plugin) {
        this.plugin = plugin;
    }
}
