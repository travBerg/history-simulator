package World.PointOfInterest;

import World.Resources.Resource;
import javafx.util.Pair;

import java.util.Map;

public class Cave extends POI {

    public Cave(final String name, Map<Resource, Pair<Integer, Integer>> res) {
        super(name, res);
    }
}
