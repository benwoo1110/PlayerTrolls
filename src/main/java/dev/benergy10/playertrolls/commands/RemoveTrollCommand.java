package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.CommandIssuer;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.CommandPermission;
import dev.benergy10.minecrafttools.acf.annotation.Name;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.acf.annotation.Syntax;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.contants.DeactivationResult;
import org.jetbrains.annotations.NotNull;

@CommandAlias("playertrolls|trolls")
public class RemoveTrollCommand extends Command {

    public RemoveTrollCommand(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Subcommand("removetroll")
    @CommandPermission("playertrolls.removetroll")
    @CommandCompletion("@players @activetrolls")
    @Syntax("<player> <activetroll>")
    public void onTroll(@NotNull CommandIssuer issuer, @NotNull @Name("player") TrollPlayer trollPlayer,
                        @NotNull @Name("activetroll") Troll troll) {

        DeactivationResult result = trollPlayer.deactivateTroll(troll);

        issuer.sendInfo(result.getMessage(), "{troll}", troll.getName());
    }
}
