package World.Territory;

public class Tundra extends Territory {
    public final static String CODE = "TU";
    public Tundra(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
