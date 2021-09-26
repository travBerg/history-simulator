package World.Territory;

import World.Rivers.River;
import World.Territory.Biome.Biome;

import java.util.HashMap;

public class TundraHills extends Territory {
    public final String name;
    public static final Biome BIOME = Biome.TUNDRAHILLS;
    public TundraHills(String location, int seed, String hrt, int size, final HashMap<Integer, River> rivers,
                       final HashMap<String, Integer> locBased) {
        super(location, seed, hrt, size);
        name = "Unnamed " + BIOME.getName() + " Territory";
    }

    @Override
    public String getName() { return name; }

    @Override
    public Biome getBiome() { return BIOME; }
}
