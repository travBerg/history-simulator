package World.Territory;

public class Badlands extends Territory {
    public static final String CODE = "BD";
    public Badlands(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
