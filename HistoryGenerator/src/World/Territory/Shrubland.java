package World.Territory;

public class Shrubland extends Territory {
    public final static String CODE = "SB";
    public final String name;
    public final static String BIOME = "Shrubland";
    public Shrubland (String location, int seed, String hrt, int size) {
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
