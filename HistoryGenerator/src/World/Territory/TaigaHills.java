package World.Territory;

public class TaigaHills extends Territory {
    public static final String CODE = "TH";
    public TaigaHills(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
