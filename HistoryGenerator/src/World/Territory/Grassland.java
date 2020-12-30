package World.Territory;

public class Grassland extends Territory {
    public final static String CODE = "GL";
    public final String name;
    public Grassland (String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Grassland Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
