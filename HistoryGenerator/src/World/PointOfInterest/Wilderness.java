package World.PointOfInterest;

import World.Animals.Animal;
import World.Resources.Resource;
import World.Territory.Biome.Biome;
import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wilderness extends POI{

    //Initializer
    public Wilderness(final String name, final Biome biome) {
        this(name + " Wilderness", biome.getResourceStats(), biome.getAnimals());
    }
    //Initializer helper
    private Wilderness(final String name, final Map<Resource, Pair<Integer, Integer>> resources,
                       final Map<Animal, Pair<Integer, Integer>> animals) {
        super(name, resources, animals, Optional.empty());
    }

    //Constructor for when an existing wilderness is first discovered and named
    public Wilderness(final Wilderness wild, final String name, final Optional<String> discovered) {
        super(name, wild.getResources(), wild.getAnimals(), discovered);
    }
}
