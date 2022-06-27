package World.PointOfInterest;

import World.Animals.Animal;
import World.Resources.Resource;
import World.Rivers.River;
import World.Territory.Biome.Biome;
import javafx.util.Pair;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lake extends POI {
    private final Optional<RiverSegment> river;
    private final String nameMeaning;
    private final static Pair<Integer, Integer> WATER = new Pair<>(Integer.MAX_VALUE, 0);
    //Map of resource mean and std dev
    private final static Map<Resource, Pair<Integer, Integer>> RES = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, WATER)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    private final static Map<Animal, Pair<Integer,Integer>> ANI = new HashMap<>();

    public Lake(final String name, final Optional<RiverSegment> river, final Biome biome) {
        this(name, river, biome, RES, ANI);
    }

    private Lake(final String name, final Optional<RiverSegment> river, final Biome biome,
                 final Map<Resource, Pair<Integer, Integer>> res, final Map<Animal, Pair<Integer, Integer>> ani) {
        super(name, res, ani, Optional.empty());
        this.river = river;
        this.nameMeaning = "";
    }

    public Lake(final Lake oldLake, final String name, final String nameMeaning, final Optional<String> discoverer) {
        super(name, oldLake.resources, oldLake.animals, discoverer);
        this.river = oldLake.river;
        this.nameMeaning = nameMeaning;
    }

    public final Optional<RiverSegment> getRiver() {
        return river;
    }
    public final String getNameMeaning() { return nameMeaning; }

}
