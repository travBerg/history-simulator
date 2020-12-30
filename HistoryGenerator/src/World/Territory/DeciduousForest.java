package World.Territory;

public class DeciduousForest extends Territory{
    public static final String CODE = "DF";
    public final String name;
    public DeciduousForest(String location, int seed, String hrt, int size) {
        super(location, seed, hrt, size);
        name = "Unnamed Deciduous Forest Territory";
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getName() { return name; }
}
