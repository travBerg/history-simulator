package World.Territory;

public class Glacier extends Territory {
    public static final String CODE = "GM";
    public final String name;
    public Glacier(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Glacier Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
