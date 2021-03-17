package dev.benergy10.playertrolls.events;

import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TrollDeactivateEvent extends TrollPlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public TrollDeactivateEvent(Troll troll, TrollPlayer trollPlayer) {
        super(troll, trollPlayer);
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
