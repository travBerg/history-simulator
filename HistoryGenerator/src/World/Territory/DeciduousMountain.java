package World.Territory;

public class DeciduousMountain extends Territory {
    public static final String CODE = "DM";
    public final String name;
    public static final String BIOME = "Deciduous Mountain";
    public DeciduousMountain(String location, int seed, String hrt, int size) {
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
