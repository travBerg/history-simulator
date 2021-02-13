package World.Territory;

import java.util.ArrayList;

public class Grassland extends Territory {
    public final static String CODE = "GL";
    public final String name;
    public final static String BIOME = "Grassland";
    public Grassland (String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed " + BIOME + " Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getBiome() { return BIOME; }
}
