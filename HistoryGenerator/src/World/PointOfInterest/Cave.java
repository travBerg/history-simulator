package World.PointOfInterest;

import World.Animals.Animal;
import World.Resources.Resource;
import javafx.util.Pair;

import java.util.Map;
import java.util.Optional;

public class Cave extends POI {

    public Cave(final String name, Map<Resource, Pair<Integer, Integer>> res, final Map<Animal, Pair<Integer, Integer>> ani) {
        super(name, res, ani, Optional.empty());
    }
}
