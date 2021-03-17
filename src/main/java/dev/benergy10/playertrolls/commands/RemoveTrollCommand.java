package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import org.jetbrains.annotations.NotNull;

@CommandAlias("playertrolls|trolls")
public class RemoveTrollCommand extends Command {

    public RemoveTrollCommand(PlayerTrolls plugin) {
        super(plugin);
    }

    @Subcommand("removetroll")
    @CommandCompletion("@players @trolls")
    public void onTroll(@NotNull TrollPlayer trollPlayer,
                        @NotNull Troll troll) {

        trollPlayer.deactivateTroll(troll);
    }
}
