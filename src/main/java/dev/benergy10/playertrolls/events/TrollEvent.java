package dev.benergy10.playertrolls.events;

import dev.benergy10.playertrolls.Troll;
import org.bukkit.event.Event;

public abstract class TrollEvent extends Event {

    private final Troll troll;

    protected TrollEvent(Troll troll) {
        this.troll = troll;
    }

    public Troll getTroll() {
        return troll;
    }
}
