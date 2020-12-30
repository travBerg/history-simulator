package World.Territory;

public class DeciduousMountain extends Territory {
    public static final String CODE = "DM";
    public final String name;
    public DeciduousMountain(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Deciduous Mountain Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
