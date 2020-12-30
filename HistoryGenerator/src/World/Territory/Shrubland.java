package World.Territory;

public class Shrubland extends Territory {
    public final static String CODE = "SB";
    public final String name;
    public Shrubland (String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Shrubland Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
