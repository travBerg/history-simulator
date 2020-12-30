package World.Territory;

public class MountainTaiga extends Territory {
    public static final String CODE = "MT";
    public final String name;
    public MountainTaiga(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Mountain Taiga Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
