package World.Territory;

public class JungleHills extends Territory {
    public static final String CODE = "JH";
    public JungleHills(String location, int seed, String hrt, int size) {super(location, seed, hrt, size);}


    @Override
    public String getCode() {
        return CODE;
    }
}