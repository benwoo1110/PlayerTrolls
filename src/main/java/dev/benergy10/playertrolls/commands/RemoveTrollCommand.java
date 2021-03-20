package dev.benergy10.playertrolls.commands;

import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.CommandCompletion;
import dev.benergy10.minecrafttools.acf.annotation.Name;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.acf.annotation.Syntax;
import dev.benergy10.minecrafttools.acf.bukkit.contexts.OnlinePlayer;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import org.jetbrains.annotations.NotNull;

@CommandAlias("playertrolls|trolls")
public class RemoveTrollCommand extends Command {

    public RemoveTrollCommand(@NotNull PlayerTrolls plugin) {
        super(plugin);
    }

    @Subcommand("removetroll")
    @CommandCompletion("@players @trolls")
    @Syntax("<player> <troll>")
    public void onTroll(@NotNull @Name("player") OnlinePlayer player,
                        @NotNull @Name("troll") Troll troll) {

        this.plugin.getTrollManager().getTrollPlayer(player.getPlayer()).deactivateTroll(troll);
    }
}
