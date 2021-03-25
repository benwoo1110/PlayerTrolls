package dev.benergy10.playertrolls.enums;

public enum ActivationResult {

    ACTIVATED(Messages.APPLY_ACTIVATED),
    CANCELLED(Messages.APPLY_CANCELLED),
    ALREADY_ACTIVE(Messages.APPLY_ALREADY_ACTIVATED),
    MISSING_DEPENDENCY(Messages.APPLY_MISSING_DEPENDENCY),
    ERRORED(Messages.APPLY_ERRORED);

    private final Messages message;

    ActivationResult(Messages message) {
        this.message = message;
    }

    public Messages getMessage() {
        return message;
    }
}
