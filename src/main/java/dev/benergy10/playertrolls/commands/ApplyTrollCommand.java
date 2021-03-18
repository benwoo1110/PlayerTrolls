package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.commands.flags.FlagResult;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import org.jetbrains.annotations.NotNull;

@CommandAlias("playertrolls|trolls")
public class ApplyTrollCommand extends Command {

    public ApplyTrollCommand(PlayerTrolls plugin) {
        super(plugin);
    }

    @Subcommand("applytroll")
    @CommandCompletion("@players @trolls @trollflags")
    public void onTroll(@NotNull TrollPlayer trollPlayer,
                        @NotNull Troll troll,
                        @NotNull String[] flagsArray) {

        FlagResult flags = troll.getFlagGroup().calculateResult(flagsArray);
        trollPlayer.activateTroll(troll, flags);
    }
}
