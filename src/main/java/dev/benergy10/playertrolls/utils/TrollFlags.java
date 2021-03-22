package dev.benergy10.playertrolls.utils;

import dev.benergy10.minecrafttools.commands.flags.Flag;
import dev.benergy10.minecrafttools.commands.flags.FlagCreatorTool;
import dev.benergy10.minecrafttools.commands.flags.FlagParseFailedException;
import dev.benergy10.minecrafttools.commands.flags.RequiredValueFlag;
import dev.benergy10.minecrafttools.utils.ReflectHelper;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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

    public static final Flag<EntityType> DISGUISE_TYPE = new RequiredValueFlag<EntityType>("MobType", "--mod-type", EntityType.class) {
        @Override
        public Collection<String> suggestValue() {
            if (!ReflectHelper.hasClass("me.libraryaddict.disguise.disguisetypes.DisguiseType")) {
                return Collections.emptyList();
            }
            return Arrays.stream(DisguiseType.values())
                    .filter(DisguiseType::isMob)
                    .map(type -> type.getEntityType().name().toLowerCase())
                    .collect(Collectors.toList());
        }

        @Override
        public EntityType getValue(@NotNull String input) {
            try {
                return EntityType.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new FlagParseFailedException("%s is not a valid mob.", input);
            }
        }

        @Override
        public EntityType getDefaultValue() {
            return EntityType.CREEPER;
        }
    };
}
