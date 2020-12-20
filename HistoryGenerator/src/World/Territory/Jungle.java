package World.Territory;

public class Jungle extends Territory{
    public static final String CODE = "JG";
    public Jungle(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
