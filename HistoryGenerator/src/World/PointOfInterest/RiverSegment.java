package World.PointOfInterest;

import World.Resources.Resource;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RiverSegment extends POI{
    final int riverId;
    //location of river source
    final String in;
    //location river flows to
    final String out;
    //merging river segments
    //Set of Pair<location, river id>
    final Set<Pair<String, Integer>> tributaries;
    private final static Pair<Integer, Integer> WATER = new Pair<>(Integer.MAX_VALUE, 0);
    private final static Map<Resource, Pair<Integer, Integer>> RES = Stream.of(
            new AbstractMap.SimpleImmutableEntry<Resource, Pair<Integer, Integer>>(Resource.WATER, WATER)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    public RiverSegment(final int riverId, final String name, final String in, final String out, final Set<Pair<String, Integer>> trib){
        this(riverId, name, in, out, trib, RES);
    }

    private RiverSegment(final int riverId, final String name, final String in, final String out,
                         final Set<Pair<String, Integer>> trib, final Map<Resource, Pair<Integer, Integer>> res){
        super(name, res);
        this.riverId = riverId;
        this.in = in;
        this.out = out;
        this.tributaries = trib;
    }

    public int getRiverId() { return riverId; }
    public String getIn() { return in; }
    public String getOut() { return out; }
    public Set<Pair<String, Integer>> getTributaries() { return tributaries; }
}
