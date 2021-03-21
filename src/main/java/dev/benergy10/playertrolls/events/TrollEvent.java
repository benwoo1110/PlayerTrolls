package dev.benergy10.playertrolls.events;

import dev.benergy10.playertrolls.Troll;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class TrollEvent extends Event {

    private final Troll troll;

    protected TrollEvent(@NotNull Troll troll) {
        this.troll = troll;
    }

    public @NotNull Troll getTroll() {
        return troll;
    }
}
