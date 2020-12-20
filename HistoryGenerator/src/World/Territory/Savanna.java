package World.Territory;

public class Savanna extends Territory{
    public static final String CODE = "SV";
    public Savanna(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
