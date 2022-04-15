package World.PointOfInterest;

import World.Animals.Animal;
import World.Resources.Resource;
import javafx.util.Pair;

import java.awt.*;
import java.util.Map;
import java.util.Set;

public abstract class POI {
    final String name;
    //Set of resource to mean, std dev
    final Map<Resource, Pair<Integer, Integer>> resources;
    final Map<Animal, Pair<Integer,Integer>> animals;
    POI(final String name, final Map<Resource, Pair<Integer, Integer>> resources, final Map<Animal, Pair<Integer, Integer>> animals){
        this.name = name;
        this.resources = resources;
        this.animals = animals;
    }
    public String getName() { return name; }
    public Map<Resource, Pair<Integer, Integer>> getResources() { return resources; }
    public Map<Animal, Pair<Integer, Integer>> getAnimals() {return animals;}
}
