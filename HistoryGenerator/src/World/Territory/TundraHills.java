package World.Territory;

public class TundraHills extends Territory {
    public static final String CODE = "UH";
    public TundraHills(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
