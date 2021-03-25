package dev.benergy10.playertrolls.contants;

import dev.benergy10.minecrafttools.locales.MessageKey;
import dev.benergy10.minecrafttools.locales.MessageKeyProvider;

import java.util.Locale;

public enum Messages implements MessageKeyProvider {
    APPLY_ACTIVATED,
    APPLY_ALREADY_ACTIVATED,
    APPLY_MISSING_DEPENDENCY,
    APPLY_CANCELLED,
    APPLY_ERRORED,

    REMOVE_DEACTIVATED,
    REMOVE_NOT_ACTIVE,
    REMOVE_ERRORED,

    RELOAD_SUCCESS,
    RELOAD_ERRORED

    ;

    private final MessageKey key;

    Messages() {
        this.key = MessageKey.of("playertrolls." + this.name().toLowerCase(Locale.ENGLISH));
    }

    public MessageKey getMessageKey() {
        return this.key;
    }
}
