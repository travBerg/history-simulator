package World.Territory;

public class DeciduousForest extends Territory{
    public static final String CODE = "DF";
    public DeciduousForest(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return CODE;
    }
}
