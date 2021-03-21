package dev.benergy10.playertrolls.events;

import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TrollDeactivateEvent extends TrollPlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public TrollDeactivateEvent(@NotNull Troll troll, @NotNull TrollPlayer trollPlayer) {
        super(troll, trollPlayer);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
