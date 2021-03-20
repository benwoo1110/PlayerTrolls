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

    public static final ConfigOption<Boolean> DEBUG_MODE = new ConfigOption.Builder<Boolean>()
            .path("enable-debug-log")
            .comment("Do debugging stuff")
            .defaultValue(false)
            .register(TrollOptions::register);
}
