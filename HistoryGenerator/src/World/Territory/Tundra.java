package World.Territory;

public class Tundra extends Territory {
    public final static String CODE = "TU";
    public final String name;
    public static final String BIOME = "Tundra";
    public Tundra(String location, int seed, String hrt, int size) {
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
