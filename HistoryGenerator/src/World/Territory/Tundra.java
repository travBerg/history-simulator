package World.Territory;

public class Tundra extends Territory {
    public final static String CODE = "TU";
    public final String name;
    public Tundra(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Tundra Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
