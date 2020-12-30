package World;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public interface IWorld {

    public ArrayList<Pair<Integer,String>> getRegionList();
    public ArrayList<Pair<String, String>> getTerritories(int idx);
}
