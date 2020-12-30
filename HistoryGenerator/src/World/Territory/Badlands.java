package World.Territory;

public class Badlands extends Territory {
    public static final String CODE = "BD";
    public final String name;
    //Default constructor
    public Badlands(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Badlands Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
