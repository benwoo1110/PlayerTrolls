package dev.benergy10.playertrolls.events;

import dev.benergy10.playertrolls.Troll;
import dev.benergy10.playertrolls.TrollPlayer;

public abstract class TrollPlayerEvent extends TrollEvent {

    private final TrollPlayer trollPlayer;

    protected TrollPlayerEvent(Troll troll, TrollPlayer trollPlayer) {
        super(troll);
        this.trollPlayer = trollPlayer;
    }

    public TrollPlayer getTrollPlayer() {
        return trollPlayer;
    }
}
