package World.Territory;

public class TundraHills extends Territory {
    public static final String CODE = "UH";
    public final String name;
    public TundraHills(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Tundra Hill Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
