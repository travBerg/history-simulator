package World.Rivers;

import World.Territory.Territory;
import javafx.util.Pair;

import java.util.*;

public class River {
    private final List<String> segments;
    private final String name;
    private final Optional<Integer> merge;
    //Set of <location, tributary river id>
    private final Optional<Set<Pair<String, Integer>>> tributaries;

    public River(final int seed, final HashMap<String, ArrayList<String>> terrain, final String start, final int size) {
        this.name = "Unnamed River";
        this.segments = RiverManager.constructRiver(seed, terrain, start, size);
        this.merge = Optional.empty();
        this.tributaries = Optional.empty();
    }

    public River(final String name, final List<String> segments) {
        this.name = name;
        this.segments = segments;
        this.merge = Optional.empty();
        this.tributaries = Optional.empty();
    }

    public River(final String name, final List<String> segments, final Optional<Integer> merge, final Optional<Set<Pair<String, Integer>>> tributaries) {
        this.name = name;
        this.segments = segments;
        this.merge = merge;
        this.tributaries = tributaries;
    }

    public final List<String> getSegments(){
        return segments;
    }

    public final String getName() {
        return name;
    }

    public final Optional<Integer> getMerge() {return merge;}

    public final Optional<Set<Pair<String, Integer>>> getTributaries() {return tributaries;}
}
