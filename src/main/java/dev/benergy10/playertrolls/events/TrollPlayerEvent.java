package dev.benergy10.playertrolls.events;

import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;
import org.jetbrains.annotations.NotNull;

public abstract class TrollPlayerEvent extends TrollEvent {

    private final TrollPlayer trollPlayer;

    protected TrollPlayerEvent(@NotNull Troll troll, @NotNull TrollPlayer trollPlayer) {
        super(troll);
        this.trollPlayer = trollPlayer;
    }

    public @NotNull TrollPlayer getTrollPlayer() {
        return trollPlayer;
    }
}
