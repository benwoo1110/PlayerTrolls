package dev.benergy10.playertrolls.utils;

import dev.benergy10.minecrafttools.commands.flags.Flag;
import dev.benergy10.minecrafttools.commands.flags.FlagCreatorTool;
import dev.benergy10.minecrafttools.commands.flags.FlagParseFailedException;
import dev.benergy10.minecrafttools.commands.flags.RequiredValueFlag;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class TrollFlags {

    public static final Flag<Integer> DURATION = new FlagCreatorTool
            .NumberCreator<>("Duration", "--duration", Integer.class)
            .parser(Integer::parseInt)
            .defaultValue(5)
            .range(-1, Integer.MAX_VALUE)
            .create();

    public static final Flag<Boolean> DO_DAMAGE = new FlagCreatorTool
            .BooleanCreator("DoDamage", "--do-damage")
            .presentValue(true)
            .create();

    public static final Flag<Integer> INTERVAL = new FlagCreatorTool
            .NumberCreator<>("Interval", "--interval", Integer.class)
            .parser(Integer::parseInt)
            .defaultValue(5)
            .range(1, 1000)
            .create();

    public static final Flag<Integer> REPEAT = new FlagCreatorTool
            .NumberCreator<>("Repeat", "--repeat", Integer.class)
            .parser(Integer::parseInt)
            .defaultValue(0)
            .range(0, Integer.MAX_VALUE)
            .create();

    public static final Flag<Integer> INTENSITY = new FlagCreatorTool
            .NumberCreator<>("Intensity", "--intensity", Integer.class)
            .parser(Integer::parseInt)
            .defaultValue(2)
            .range(1, 1000)
            .create();

    public static final Flag<Boolean> EXPLODE_BLOCKS = new FlagCreatorTool
            .BooleanCreator("ExplodeBlocks", "--explode-blocks")
            .presentValue(true)
            .create();

    public static final Flag<DisguiseType> MOB_TYPE = new RequiredValueFlag<DisguiseType>("MobType", "--mod-type", DisguiseType.class) {
        @Override
        public Collection<String> suggestValue() {
            return Arrays.stream(DisguiseType.values())
                    .filter(DisguiseType::isMob)
                    .map(DisguiseType::name)
                    .collect(Collectors.toList());
        }

        @Override
        public DisguiseType getValue(@NotNull String input) {
            try {
                return DisguiseType.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new FlagParseFailedException("%s is not a valid mob.", input);
            }
        }

        @Override
        public DisguiseType getDefaultValue() {
            return DisguiseType.CREEPER;
        }
    };
}
