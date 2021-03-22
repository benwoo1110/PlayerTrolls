package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.Name;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.acf.annotation.Syntax;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import org.jetbrains.annotations.NotNull;

@CommandAlias("playertrolls|trolls")
public class RemoveTrollCommand extends Command {

    public RemoveTrollCommand(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Subcommand("removetroll")
    @CommandCompletion("@players @activetrolls")
    @Syntax("<player> <activetroll>")
    public void onTroll(@NotNull @Name("player") TrollPlayer trollPlayer,
                        @NotNull @Name("activetroll") Troll troll) {

        trollPlayer.deactivateTroll(troll);
    }
}
