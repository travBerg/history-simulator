package World.Territory;

public class GrassyHills extends Territory {
    public static final String CODE = "GH";
    public final String name;
    public GrassyHills(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Grassy Hills Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
