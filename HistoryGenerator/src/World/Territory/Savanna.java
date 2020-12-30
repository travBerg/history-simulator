package World.Territory;

public class Savanna extends Territory{
    public static final String CODE = "SV";
    public final String name;
    public Savanna(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Savanna Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
