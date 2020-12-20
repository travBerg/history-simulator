package World.Territory;

public class DeciduousHills extends Territory {
    public static final String CODE = "DH";
    public DeciduousHills(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}

    @Override
    public String getCode() {
        return "Deciduous Hills";
    }
}
