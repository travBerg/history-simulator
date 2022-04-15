package World.PointOfInterest;

import World.Animals.Animal;
import World.Resources.Resource;
import World.Territory.Biome.Biome;
import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wilderness extends POI{

    public Wilderness(final String name, final Biome biome) {
        this(name + " Wilderness", biome.getResourceStats(), biome.getAnimals());
    }

    private Wilderness(final String name, final Map<Resource, Pair<Integer, Integer>> resources,
                       final Map<Animal, Pair<Integer, Integer>> animals) {
        super(name, resources, animals);
    }
}
