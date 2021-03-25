package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.CommandIssuer;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandPermission;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.enums.Messages;
import org.jetbrains.annotations.NotNull;

@CommandAlias("playertrolls|trolls")
public class ReloadCommand extends Command {

    public ReloadCommand(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Subcommand("reload")
    @CommandPermission("playertrolls.reload")
    public void onReload(@NotNull CommandIssuer issuer) {
        if (this.plugin.reload()) {
            issuer.sendInfo(Messages.RELOAD_SUCCESS);
            return;
        }
        issuer.sendInfo(Messages.RELOAD_ERRORED);
    }
}
