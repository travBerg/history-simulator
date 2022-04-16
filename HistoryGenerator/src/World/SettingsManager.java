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

    //Resource mods to mess with scarcity
    public static HashMap<String, Double> getResourceMods(){
        //TODO: Read settings from a settings file
        final HashMap<String, Double> mods = new HashMap<>();
        mods.put("berries_mod", 1.0);
        mods.put("cactus fruit_mod", 1.0);
        mods.put("corn_mod", 1.0);
        mods.put("fruit_mod", 1.0);
        mods.put("mushroom_mod", 1.0);
        mods.put("nuts_mod", 1.0);
        mods.put("rice_mod", 1.0);
        mods.put("water_mod", 1.0);
        mods.put("wheat_mod", 1.0);
        return mods;
    }

    public static HashMap<String, Float> getGroupMods() {
        //TODO: Read settings from a settings file
        final HashMap<String, Float> mods = new HashMap<>();
        mods.put("pop_mod", 1.0f);
        return mods;
    }
}
