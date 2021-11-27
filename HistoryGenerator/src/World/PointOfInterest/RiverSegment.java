package World.PointOfInterest;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class RiverSegment extends POI{
    final int riverId;
    //location of river source
    final String in;
    //location river flows to
    final String out;
    //merging river segments
    //Set of Pair<location, river id>
    final Set<Pair<String, Integer>> tributaries;

    public RiverSegment(final int riverId, final String name, final String in, final String out, final Set<Pair<String, Integer>> trib){
        super(name);
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
