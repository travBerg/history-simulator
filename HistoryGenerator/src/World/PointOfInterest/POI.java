package World.PointOfInterest;

import World.Animals.Animal;
import World.Resources.Resource;
import javafx.util.Pair;

import java.awt.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class POI {
    final String name;
    //Set of resource to mean, std dev
    final Map<Resource, Pair<Integer, Integer>> resources;
    final Map<Animal, Pair<Integer,Integer>> animals;
    //Optional id of the first group to discover it (if there is one)
    final Optional<String> discovered;
    POI(final String name, final Map<Resource, Pair<Integer, Integer>> resources,
        final Map<Animal, Pair<Integer, Integer>> animals, final Optional<String> discovered){
        this.name = name;
        this.resources = resources;
        this.animals = animals;
        this.discovered = discovered;
    }
    public String getName() { return name; }
    public Map<Resource, Pair<Integer, Integer>> getResources() { return resources; }
    public Map<Animal, Pair<Integer, Integer>> getAnimals() {return animals;}
    public String getDiscovered() { return discovered.orElse(""); }
}
