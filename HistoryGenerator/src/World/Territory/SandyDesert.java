package World.Territory;

public class SandyDesert extends Territory{
    public static final String CODE = "SD";
    public SandyDesert(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
