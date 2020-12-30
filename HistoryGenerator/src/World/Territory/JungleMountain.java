package World.Territory;

public class JungleMountain extends Territory {
    public static final String CODE = "JM";
    public final String name;
    public JungleMountain(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Jungle Mountain Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
