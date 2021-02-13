package World.Territory;

public class Ocean extends Territory {
    public final static String CODE = "OC";
    public final String name;
    public static final String BIOME = "Ocean";
    public Ocean (String location, int seed, String hrt, int size) {
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
