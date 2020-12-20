package World.Territory;

public class MountainTaiga extends Territory {
    public static final String CODE = "MT";
    public MountainTaiga(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
