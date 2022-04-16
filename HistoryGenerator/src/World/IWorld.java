package World;

import World.Groups.Group;
import World.Rivers.River;
import World.Territory.Territory;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public interface IWorld {

    public ArrayList<Pair<Integer,String>> getRegionList();
    public ArrayList<Pair<String, String>> getTerritories(int idx);
    public int getSize();
    public int getSeed();
    public HashMap<String, Territory> getTerritoryMap();
    public HashMap<Integer, Region> getRegions();
    public HashMap<Integer, River> getRivers();
    public HashMap<String, Group> getGroups();
}
