package World.Territory;

public class SandyDesert extends Territory{
    public static final String CODE = "SD";
    public final String name;
    public SandyDesert(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Sandy Desert Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
