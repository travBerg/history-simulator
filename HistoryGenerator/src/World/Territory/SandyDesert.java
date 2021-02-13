package World.Territory;

public class SandyDesert extends Territory{
    public static final String CODE = "SD";
    private final String name;
    private static final String BIOME = "Sandy Desert";
    public SandyDesert(String location, int seed, String hrt, int size) {
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
