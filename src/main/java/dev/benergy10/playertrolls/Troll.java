package dev.benergy10.playertrolls;

import dev.benergy10.minecrafttools.commands.flags.FlagGroup;
import dev.benergy10.minecrafttools.commands.flags.FlagValues;
import dev.benergy10.playertrolls.contants.DependencyRequirement;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;

public abstract class Troll {

    protected final PlayerTrolls plugin;

    private Permission permission;
    private boolean registered = false;

    protected Troll(@NotNull PlayerTrolls plugin) {
        this.plugin = plugin;
    }

    void register() {
        this.permissionSetup();
        this.registered = true;
    }

    private void permissionSetup() {
        this.permission = new Permission("playertrolls.use." + this.getName().toLowerCase());
        Bukkit.getPluginManager().addPermission(this.permission);
    }

    public boolean isRegistered() {
        return registered;
    }

    protected abstract @NotNull TrollTask start(@NotNull TrollPlayer trollPlayer, @NotNull FlagValues flags);

    public abstract @NotNull String getName();

    public abstract @NotNull FlagGroup getFlagGroup();

    public abstract @NotNull DependencyRequirement getRequirement();

    public Permission getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return "Troll{" +
                "name=" + this.getName() +
                ", registered=" + registered +
                '}';
    }

    protected abstract static class TrollTask {
        protected abstract void stop();
    }
}
