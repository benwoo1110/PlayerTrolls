package dev.benergy10.playertrolls.utils;

import dev.benergy10.minecrafttools.configs.ConfigOption;

import java.util.ArrayList;
import java.util.List;

public class TrollOptions {

    private static final List<ConfigOption<?>> OPTIONS = new ArrayList<>();

    private static void register(ConfigOption<?> option) {
        OPTIONS.add(option);
    }

    public static List<ConfigOption<?>> getOptions() {
        return OPTIONS;
    }

    public static String[] header() {
        // https://patorjk.com/software/taag/
        return new String[] {
                "  ))    W  W         wWw  wWw     ))   (o)__(o) ))       .-.    W  W   W  W    oo_    ",
                " (o0)-.(O)(O)    /)  (O)  (O)wWw (Oo)-.(__  __)(Oo)-.  c(O_O)c (O)(O) (O)(O)  /  _)-< ",
                "  | (_)) ||    (o)(O)( \\  / )(O)_ | (_)) (  )   | (_)),'.---.`,  ||     ||    \\__ `.  ",
                "  | .-'  | \\    //\\\\  \\ \\/ /.' __)|  .'   )(    |  .'/ /|_|_|\\ \\ | \\    | \\      `. | ",
                "  |(     |  `. |(__)|  \\o /(  _)  )|\\\\   (  )   )|\\\\ | \\_____/ | |  `.  |  `.    _| | ",
                "   \\)   (.-.__)/,-. | _/ /  `.__)(/  \\)   )/   (/  \\)'. `---' .`(.-.__)(.-.__),-'   | ",
                "   (     `-'  -'   ''(_.'         )      (      )      `-...-'   `-'    `-'  (_..--'  "
        };
    }

    public static final ConfigOption<Boolean> ALLOW_DAMAGE_OPTION = new ConfigOption.Builder<Boolean>()
            .path("disabled-trolls")
            .comment("Some trolls have the option to inflict real damage to players, such as with the")
            .comment("'--do-damage' flag. When set to false, PlayerTrolls will completely disable")
            .comment("such an option.")
            .defaultValue(true)
            .register(TrollOptions::register);

    public static final ConfigOption<List<String>> DISABLED_TROLLS = new ConfigOption.Builder<List<String>>()
            .path("disabled-trolls")
            .comment("Disable trolls that you do not want to use. Note: This will only apply after a")
            .comment("server restart.")
            .defaultValue(new ArrayList<>())
            .register(TrollOptions::register);

    public static final ConfigOption<Boolean> DEBUG_MODE = new ConfigOption.Builder<Boolean>()
            .path("enable-debug-mode")
            .comment("Extra logging for more debug info. Used for testing and issue reporting.")
            .defaultValue(false)
            .register(TrollOptions::register);
}
