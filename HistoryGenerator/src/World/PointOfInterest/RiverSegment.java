package World.PointOfInterest;

import World.Animals.Animal;
import World.Resources.Resource;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RiverSegment extends POI{
    final int riverId;
    final String nameMeaning;
    //location of river source
    final String in;
    //location river flows to
    final String out;
    //merging river segments
    //Set of Pair<location, river id>
    final Set<Pair<String, Integer>> tributaries;
    private final static Pair<Integer, Integer> WATER = new Pair<>(Integer.MAX_VALUE, 0);
    private final static Map<Resource, Pair<Integer, Integer>> RES = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>(Resource.WATER, WATER)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    private final static Map<Animal, Pair<Integer,Integer>> ANI = new HashMap<>();


    public RiverSegment(final int riverId, final String name, final String in, final String out, final Set<Pair<String, Integer>> trib){
        this(riverId, name, in, out, trib, RES, ANI);
    }

    private RiverSegment(final int riverId, final String name, final String in, final String out,
                         final Set<Pair<String, Integer>> trib, final Map<Resource, Pair<Integer, Integer>> res,
                         final Map<Animal, Pair<Integer, Integer>> ani){
        super(name, res, ani, Optional.empty());
        this.riverId = riverId;
        this.in = in;
        this.out = out;
        this.tributaries = trib;
        this.nameMeaning = "";
    }

    public RiverSegment(final RiverSegment oldSeg, final String name, final String nameMeaning,
                        final Optional<String> discoverer) {
        super(name, oldSeg.resources, oldSeg.animals, discoverer);
        this.riverId = oldSeg.riverId;
        this.in = oldSeg.in;
        this.out = oldSeg.out;
        this.tributaries = oldSeg.tributaries;
        this.nameMeaning = nameMeaning;
    }

    public final int getRiverId() { return riverId; }
    public final String getIn() { return in; }
    public final String getOut() { return out; }
    public final Set<Pair<String, Integer>> getTributaries() { return tributaries; }
    public final String getNameMeaning() { return nameMeaning; }
}
