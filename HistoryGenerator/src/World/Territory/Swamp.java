package World.Territory;

public class Swamp extends Territory{
    public static final String CODE = "SW";
    public Swamp(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
