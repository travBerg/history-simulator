package World.Rivers;

import World.Territory.Territory;
import javafx.util.Pair;

import java.util.*;

public class River {
    private final List<String> segments;
    //List of Pair<Pair<name, nameMeaning>, GroupId>
    private final List<Pair<Pair<String, String>, String>> names;
    private final Optional<Integer> merge;
    //Set of <location, tributary river id>
    private final Optional<Set<Pair<String, Integer>>> tributaries;

    public River(final Random random, final HashMap<String, ArrayList<String>> terrain, final String start, final int size) {
        this.names = new ArrayList<>();
        this.segments = RiverManager.constructRiver(random, terrain, start, size);
        this.merge = Optional.empty();
        this.tributaries = Optional.empty();
    }

    public River(final List<Pair<Pair<String, String>, String>> name, final List<String> segments) {
        this.names = name;
        this.segments = segments;
        this.merge = Optional.empty();
        this.tributaries = Optional.empty();
    }

    public River(final List<Pair<Pair<String, String>, String>> name, final List<String> segments,
                 final Optional<Integer> merge, final Optional<Set<Pair<String, Integer>>> tributaries) {
        this.names = name;
        this.segments = segments;
        this.merge = merge;
        this.tributaries = tributaries;
    }

    public River(final River r, final Pair<Pair<String, String>, String> name) {
        this.names = r.names;
        this.names.add(name);
        this.segments = r.segments;
        this.merge = r.merge;
        this.tributaries = r.tributaries;
    }

    public final List<String> getSegments(){
        return segments;
    }

    public final List<Pair<Pair<String, String>, String>> getName() {
        return names;
    }

    /**
     * Get most common name by influence
     * @return a pair of name and name meaning
     */
    public final Pair<String, String> getCommonName() {
        if(names.size() == 0) {
            return new Pair<>("", "");
        } else {
            //TODO: Have this dispatch to a function that gets the most common name by group influence
            return names.get(0).getKey();
        }
    }

    public final Optional<Integer> getMerge() {return merge;}

    public final Optional<Set<Pair<String, Integer>>> getTributaries() {return tributaries;}
}
