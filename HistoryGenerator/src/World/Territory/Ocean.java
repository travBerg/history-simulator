package World.Territory;

public class Ocean extends Territory {
    public final static String CODE = "OC";
    public Ocean (String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
    }

    @Override
    public String getCode() {
        return CODE;
    }
}
