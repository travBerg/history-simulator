package World.Territory;

public class DeciduousHills extends Territory {
    public static final String CODE = "DH";
    public final String name;
    public static final String BIOME = "Deciduous Hills";
    public DeciduousHills(String location, int seed, String hrt, int size) {
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
