package dev.benergy10.playertrolls.utils;

import dev.benergy10.minecrafttools.commands.flags.Flag;
import dev.benergy10.minecrafttools.commands.flags.FlagParseFailedException;
import dev.benergy10.minecrafttools.commands.flags.NoValueFlag;
import dev.benergy10.minecrafttools.commands.flags.RequiredValueFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrollFlags {

    public static final Flag<Integer> DURATION = new RequiredValueFlag<Integer>("Duration", "--duration", Integer.class) {
        @Override
        public Collection<String> suggestValue() {
            return IntStream.range(1, 21).boxed().map(Object::toString).collect(Collectors.toList());
        }

        @Override
        public Integer getValue(@NotNull String input) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new FlagParseFailedException("%s is not a number!", input);
            }
        }

        @Override
        public Integer getDefaultValue() {
            return 5;
        }
    };

    public static final Flag<Boolean> DO_DAMAGE = new NoValueFlag<Boolean>("DoDamage", "--do-damage", Boolean.class) {
        @Override
        public Boolean getValue() {
            return true;
        }

        @Override
        public Boolean getDefaultValue() {
            return false;
        }
    };

    public static final Flag<Integer> INTERVAL = new RequiredValueFlag<Integer>("Interval", "--interval", Integer.class) {
        @Override
        public Collection<String> suggestValue() {
            return IntStream.range(1, 21).boxed().map(Object::toString).collect(Collectors.toList());
        }

        @Override
        public Integer getValue(@NotNull String input) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new FlagParseFailedException("%s is not a number!", input);
            }
        }

        @Override
        public Integer getDefaultValue() {
            return 2;
        }
    };

    public static final Flag<Integer> REPEAT = new RequiredValueFlag<Integer>("Repeat", "--repeat", Integer.class) {
        @Override
        public Collection<String> suggestValue() {
            return IntStream.range(1, 21).boxed().map(Object::toString).collect(Collectors.toList());
        }

        @Override
        public Integer getValue(@NotNull String input) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new FlagParseFailedException("%s is not a number!", input);
            }
        }

        @Override
        public Integer getDefaultValue() {
            return 0;
        }
    };

    public static final Flag<Integer> INTENSITY = new RequiredValueFlag<Integer>("Intensity", "--intensity", Integer.class) {
        @Override
        public Collection<String> suggestValue() {
            return IntStream.range(1, 21).boxed().map(Object::toString).collect(Collectors.toList());
        }

        @Override
        public Integer getValue(@NotNull String input) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                throw new FlagParseFailedException("%s is not a number!", input);
            }
        }

        @Override
        public Integer getDefaultValue() {
            return 2;
        }
    };
}
