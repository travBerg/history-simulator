package World.Territory;

public class Jungle extends Territory{
    public static final String CODE = "JG";
    public final String name;
    public Jungle(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Jungle Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
