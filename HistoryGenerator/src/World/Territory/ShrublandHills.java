package World.Territory;

public class ShrublandHills extends Territory {
    public static final String CODE = "SH";
    public final String name;
    public ShrublandHills(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Shrubland Hill Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
