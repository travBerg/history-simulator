package World.Territory;

public class Shrubland extends Territory {
    public final static String CODE = "SB";
    public Shrubland (String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
