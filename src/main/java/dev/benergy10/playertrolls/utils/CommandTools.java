package dev.benergy10.playertrolls.utils;

import dev.benergy10.minecrafttools.acf.BukkitCommandCompletionContext;
import dev.benergy10.minecrafttools.acf.BukkitCommandExecutionContext;
import dev.benergy10.minecrafttools.acf.CommandCompletions;
import dev.benergy10.minecrafttools.acf.CommandContexts;
import dev.benergy10.minecrafttools.acf.InvalidCommandArgument;
import dev.benergy10.minecrafttools.commands.CommandManager;
import dev.benergy10.playertrolls.PlayerTrolls;
import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.commands.ReloadCommand;
import dev.benergy10.playertrolls.commands.ApplyTrollCommand;
import dev.benergy10.playertrolls.commands.RemoveTrollCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class CommandTools {

    public static void setUp(@NotNull PlayerTrolls plugin){
        new CommandTools(plugin);
    }

    private final PlayerTrolls plugin;

    public CommandTools(@NotNull PlayerTrolls plugin) {
        this.plugin = plugin;

        CommandManager manager = this.plugin.getCommandManager();
        CommandCompletions<BukkitCommandCompletionContext> completions = manager.getCommandCompletions();
        CommandContexts<BukkitCommandExecutionContext> context = manager.getCommandContexts();

        completions.registerAsyncCompletion("trolls", this::suggestTrolls);
        completions.registerAsyncCompletion("trollflags", this::suggestTrollFlags);

        context.registerContext(Troll.class, this::parseTroll);

        manager.registerCommand(new ReloadCommand(this.plugin));
        manager.registerCommand(new ApplyTrollCommand(this.plugin));
        manager.registerCommand(new RemoveTrollCommand(this.plugin));
    }

    private @NotNull Collection<String> suggestTrolls(@NotNull BukkitCommandCompletionContext context) {
        return this.plugin.getTrollManager().getTrollNames();
    }

    private @NotNull Collection<String> suggestTrollFlags(@NotNull BukkitCommandCompletionContext context) {
        final Troll troll = context.getContextValue(Troll.class);
        if (troll == null) {
            return Collections.emptyList();
        }
        final String[] args = context.getContextValue(String[].class);
        return troll.getFlagGroup().suggestNextArgument(args);
    }

    private @NotNull Troll parseTroll(@NotNull BukkitCommandExecutionContext context) {
        String trollName = context.popFirstArg();
        Troll troll = this.plugin.getTrollManager().getTroll(trollName);
        if (troll == null) {
            throw new InvalidCommandArgument("No such troll named: " + trollName);
        }
        return troll;
    }
}
