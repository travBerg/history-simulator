package World.Territory;

public class JungleMountain extends Territory {
    public static final String CODE = "JM";
    public JungleMountain(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
