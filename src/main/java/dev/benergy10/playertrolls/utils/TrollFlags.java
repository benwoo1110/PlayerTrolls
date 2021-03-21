package dev.benergy10.playertrolls.utils;

import dev.benergy10.minecrafttools.commands.flags.Flag;
import dev.benergy10.minecrafttools.commands.flags.FlagCreatorTool;

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
}
