package World.Territory;

public class Taiga extends Territory {
    public static final String CODE = "TG";
    public final String name;
    public Taiga(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Taiga Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
