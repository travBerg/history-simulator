package World.PointOfInterest;

import World.Resources.Resource;
import javafx.util.Pair;

import java.awt.*;
import java.util.Map;
import java.util.Set;

public abstract class POI {
    final String name;
    //Set of resource to mean, std dev
    final Map<Resource, Pair<Integer, Integer>> resources;
    POI(final String name, final Map<Resource, Pair<Integer, Integer>> resources){
        this.name = name;
        this.resources = resources;
    }
    public String getName() { return name; }
    public Map<Resource, Pair<Integer, Integer>> getResources() { return resources; }
}
