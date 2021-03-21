package dev.benergy10.playertrolls.events;

import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TrollActivateEvent extends TrollPlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    private boolean cancel;

    public TrollActivateEvent(@NotNull Troll troll, @NotNull TrollPlayer trollPlayer) {
        super(troll, trollPlayer);
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
