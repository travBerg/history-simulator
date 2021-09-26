package World.Territory;

import World.Rivers.River;
import World.Territory.Biome.Biome;

import java.util.HashMap;

public class AlpineTundra extends Territory {
    public static final Biome BIOME = Biome.ALPINETUNDRA;
    public final String name;

    public AlpineTundra(String location, int seed, String hrt, int size, final HashMap<Integer, River> rivers,
                        final HashMap<String, Integer> locBased) {
        super(location, seed, hrt, size);
        name = "Unnamed " + BIOME.getName() + " Territory";
    }

    @Override
    public String getName() { return name; }

    @Override
    public Biome getBiome() { return BIOME; }
}
