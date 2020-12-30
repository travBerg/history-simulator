package World.Territory;

public class JungleHills extends Territory {
    public static final String CODE = "JH";
    public final String name;
    public JungleHills(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Jungle Hills Territory";
    }


    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
