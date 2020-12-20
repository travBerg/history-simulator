package World.Territory;

public class Grassland extends Territory {
    public final static String CODE = "GL";
    public Grassland (String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
