package dev.benergy10.playertrolls.contants;

import dev.benergy10.minecrafttools.utils.ReflectHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public enum DependencyRequirement {

    NONE,
    PROTOCOL_LIB("com.comphenix.protocol.ProtocolLibrary"),
    LIB_DISGUISES("com.comphenix.protocol.ProtocolLibrary", "me.libraryaddict.disguise.LibsDisguises");

    private final String[] classes;

    DependencyRequirement(String... classes) {
        this.classes = classes;
    }

    public boolean hasRequiredClasses() {
        return Arrays.stream(classes).allMatch(ReflectHelper::hasClass);
    }

    public @NotNull String[] getClasses() {
        return classes;
    }
}
