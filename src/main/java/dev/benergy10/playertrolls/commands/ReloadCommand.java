package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.CommandIssuer;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.playertrolls.PlayerTrolls;

@CommandAlias("playertrolls|trolls")
public class ReloadCommand extends Command {

    public ReloadCommand(PlayerTrolls plugin) {
        super(plugin);
    }

    @Subcommand("reload")
    public void onReload(CommandIssuer issuer) {
        issuer.sendMessage("Reloading plugin...");
    }
}
