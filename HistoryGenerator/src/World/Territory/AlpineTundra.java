package World.Territory;

public class AlpineTundra extends Territory {
    public static final String CODE = "AT";
    public final String name;
    public AlpineTundra(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Alpine Tundra Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
