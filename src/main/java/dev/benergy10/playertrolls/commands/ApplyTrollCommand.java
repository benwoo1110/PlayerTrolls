package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.Name;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.acf.annotation.Syntax;
import dev.benergy10.minecrafttools.acf.bukkit.contexts.OnlinePlayer;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import org.jetbrains.annotations.NotNull;

@CommandAlias("playertrolls|trolls")
public class ApplyTrollCommand extends Command {

    public ApplyTrollCommand(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Subcommand("applytroll")
    @CommandCompletion("@players @trolls @trollflags")
    @Syntax("<player> <troll> [flags]")
    public void onTroll(@NotNull @Name("player") OnlinePlayer player,
                        @NotNull @Name("troll") Troll troll,
                        @NotNull @Name("flags") String[] flagsArray) {

        FlagValues flags = troll.getFlagGroup().parse(flagsArray);
        this.plugin.getTrollManager().getTrollPlayer(player.getPlayer()).activateTroll(troll, flags);
    }
}
