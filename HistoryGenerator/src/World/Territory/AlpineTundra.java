package World.Territory;

public class AlpineTundra extends Territory {
    public static final String CODE = "AT";
    public AlpineTundra(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
