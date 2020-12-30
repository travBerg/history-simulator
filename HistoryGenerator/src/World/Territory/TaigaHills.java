package World.Territory;

public class TaigaHills extends Territory {
    public static final String CODE = "TH";
    public final String name;
    public TaigaHills(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Taiga Hill Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
