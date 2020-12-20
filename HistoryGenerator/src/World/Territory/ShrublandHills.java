package World.Territory;

public class ShrublandHills extends Territory {
    public static final String CODE = "SH";
    public ShrublandHills(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return "Shrubland Hills";
    }
}
