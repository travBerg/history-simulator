package World.Territory;

public class GrassyHills extends Territory {
    public static final String CODE = "GH";
    public GrassyHills(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
