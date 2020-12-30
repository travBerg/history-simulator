package View;

import World.IWorld;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public interface IView {
    //This should be a primitive type
    public void render(IWorld world);
    public void listRegions(ArrayList<Pair<Integer, String>> regionList);
    public void exploreRegion(ArrayList<Pair<String, String>> terr);
}
