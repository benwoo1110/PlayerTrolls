package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.Name;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.acf.annotation.Syntax;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
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
    @Syntax("<player> <troll> [flags]")
    public void onTroll(@NotNull @Name("player") TrollPlayer trollPlayer,
                        @NotNull @Name("troll") Troll troll,
                        @NotNull @Name("flags") String[] flagsArray) {

        FlagValues flags = troll.getFlagGroup().parse(flagsArray);
        trollPlayer.activateTroll(troll, flags);
    }
}
