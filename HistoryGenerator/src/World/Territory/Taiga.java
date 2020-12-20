package World.Territory;

public class Taiga extends Territory {
    public static final String CODE = "TG";
    public Taiga(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);

    }

    @Override
    public String getCode() {
        return CODE;
    }
}
