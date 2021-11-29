package World;

import java.util.HashMap;

public class SettingsManager {
    public static HashMap<String, Integer> getSettings(final int seed, final int sizeCon, final int poles, final boolean debug) {
        //TODO: Read settings from a settings file
        //The constructor arguments are for dev ease of use
        final HashMap<String, Integer> settings = new HashMap<>();
        settings.put("seed", seed);
        settings.put("sizeCon", sizeCon);
        settings.put("poles", poles);
        settings.put("debug", Boolean.compare(debug, false));
        settings.put("lakePercChanceRiver", 20);
        return settings;
    }
}
