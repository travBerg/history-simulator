package World.Territory;

public class Glacier extends Territory {
    public static final String CODE = "GM";
    public Glacier(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
