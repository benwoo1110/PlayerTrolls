package dev.benergy10.playertrolls.enums;

import dev.benergy10.playertrolls.utils.Messageable;

public enum DeactivationResult implements Messageable {

    DEACTIVATED(Messages.REMOVE_DEACTIVATED),
    NOT_ACTIVE(Messages.REMOVE_NOT_ACTIVE),
    ERRORED(Messages.REMOVE_ERRORED);

    private final Messages message;

    DeactivationResult(Messages message) {
        this.message = message;
    }

    @Override
    public Messages getMessage() {
        return message;
    }
}
