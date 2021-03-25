package dev.benergy10.playertrolls.enums;

public enum DeactivationResult {

    DEACTIVATED(Messages.REMOVE_DEACTIVATED),
    NOT_ACTIVE(Messages.REMOVE_NOT_ACTIVE),
    ERRORED(Messages.REMOVE_ERRORED);

    private final Messages message;

    DeactivationResult(Messages message) {
        this.message = message;
    }

    public Messages getMessage() {
        return message;
    }
}
