package World.Territory;

public class Mountain extends Territory {
    public static final String CODE = "MN";
    public Mountain(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
