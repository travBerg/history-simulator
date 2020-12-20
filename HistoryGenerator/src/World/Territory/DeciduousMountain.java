package World.Territory;

public class DeciduousMountain extends Territory {
    public static final String CODE = "DM";
    public DeciduousMountain(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
