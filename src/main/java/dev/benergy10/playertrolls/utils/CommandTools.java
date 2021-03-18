package dev.benergy10.playertrolls.utils;

import dev.benergy10.minecrafttools.acf.BukkitCommandCompletionContext;
import dev.benergy10.minecrafttools.acf.BukkitCommandExecutionContext;
import dev.benergy10.minecrafttools.acf.CommandCompletions;
import dev.benergy10.minecrafttools.acf.CommandContexts;
import dev.benergy10.minecrafttools.commands.CommandManager;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import dev.benergy10.playertrolls.commands.ReloadCommand;
import dev.benergy10.playertrolls.commands.ApplyTrollCommand;
import dev.benergy10.playertrolls.commands.RemoveTrollCommand;

import java.util.Collection;
import java.util.Collections;

public class CommandTools {

    public static void setUp(PlayerTrolls plugin){
        new CommandTools(plugin);
    }

    private final PlayerTrolls plugin;

    public CommandTools(PlayerTrolls plugin) {
        this.plugin = plugin;

        CommandManager manager = this.plugin.getCommandManager();
        CommandCompletions<BukkitCommandCompletionContext> completions = manager.getCommandCompletions();
        CommandContexts<BukkitCommandExecutionContext> context = manager.getCommandContexts();

        completions.registerAsyncCompletion("trolls", this::suggestTrolls);
        completions.registerAsyncCompletion("trollflags", this::suggestTrollFlags);

        context.registerContext(TrollPlayer.class, this::parseTrollPlayer);
        context.registerContext(Troll.class, this::parseTroll);

        manager.registerCommand(new ReloadCommand(this.plugin));
        manager.registerCommand(new ApplyTrollCommand(this.plugin));
        manager.registerCommand(new RemoveTrollCommand(this.plugin));
    }

    private Collection<String> suggestTrolls(BukkitCommandCompletionContext context) {
        return this.plugin.getTrollManager().getTrollNames();
    }

    private Collection<String> suggestTrollFlags(BukkitCommandCompletionContext context) {
        final Troll troll = context.getContextValue(Troll.class);
        if (troll == null) {
            return Collections.emptyList();
        }
        final String[] args = context.getContextValue(String[].class);
        return troll.getFlagGroup().suggestNextArgument(args);
    }

    private TrollPlayer parseTrollPlayer(BukkitCommandExecutionContext context) {
        String playerName = context.popFirstArg();
        return this.plugin.getTrollManager().getTrollPlayer(playerName);
    }

    private Troll parseTroll(BukkitCommandExecutionContext context) {
        String trollName = context.popFirstArg();
        return this.plugin.getTrollManager().getTroll(trollName);
    }
}
