package World.Territory;

public class Swamp extends Territory{
    public static final String CODE = "SW";
    public final String name;
    public Swamp(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Swamp Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
