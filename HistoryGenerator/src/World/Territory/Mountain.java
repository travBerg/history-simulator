package World.Territory;

public class Mountain extends Territory {
    public static final String CODE = "MN";
    public final String name;
    public Mountain(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Mountain Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
