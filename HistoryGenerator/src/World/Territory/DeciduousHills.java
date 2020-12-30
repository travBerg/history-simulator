package World.Territory;

public class DeciduousHills extends Territory {
    public static final String CODE = "DH";
    public final String name;
    public DeciduousHills(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Deciduous Hills Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
