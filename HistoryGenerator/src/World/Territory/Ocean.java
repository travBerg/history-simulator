package World.Territory;

public class Ocean extends Territory {
    public final static String CODE = "OC";
    public final String name;
    public Ocean (String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Ocean Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
