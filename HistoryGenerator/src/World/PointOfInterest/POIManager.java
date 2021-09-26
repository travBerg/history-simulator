package World.PointOfInterest;

import World.Rivers.River;
import World.World;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class POIManager {

    public static POI createRiverPOI(final River river, final ArrayList<String> neighbors, final int riverId, final int seed) {
        //TODO: make source of river, make mouth of river, make river junctions
        List<String> inOut = river.getSegments().stream().filter(neighbors::contains).collect(Collectors.toList());
        if(inOut.size() > 2) {
            System.out.println("createRiverPOI: Too many river segments, something is wrong");
        }
        return new RiverSegment(riverId, river.getName(), inOut.get(0), inOut.get(1));
    }
}
