package World.PointOfInterest;

import World.Rivers.River;
import World.World;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class POIManager {

    public static POI createRiverPOI(final River river, final String loc, final int seed,
                                     final HashMap<String, Integer> locBased) {
        //TODO: make source of river, make mouth of river, make river junctions
        final int idx = river.getSegments().indexOf(loc);
        if(idx == 0) {
            //River is head
            if(river.getSegments().size() > 1) {
                //Should never be tributary on first segment
                return new RiverSegment(locBased.get(loc), river.getName(), loc, river.getSegments().get(1),
                        new HashSet<Pair<String,Integer>>());
            }
            //River is also mouth
            return new RiverSegment(locBased.get(loc), river.getName(), loc, loc, new HashSet<>());
        }
        if(idx == river.getSegments().size() - 1) {
            //River is tail
            //Check for tributaries
            if(river.getTributaries().isPresent()) {
                Set<Pair<String, Integer>> t = river.getTributaries().map(a -> a.stream().filter(p->p.getKey()
                        .equals(loc)).collect(Collectors.toSet())).get();

                return new RiverSegment(locBased.get(loc), river.getName(), river.getSegments().get(idx - 1), loc, t);
            }
            return new RiverSegment(locBased.get(loc), river.getName(), river.getSegments().get(idx - 1), loc,
                    new HashSet<>());
        }
        //river is middle segment
        if(river.getTributaries().isPresent()) {
            Set<Pair<String, Integer>> t = river.getTributaries().map(a -> a.stream().filter(p->p.getKey()
                    .equals(loc)).collect(Collectors.toSet())).get();

            return new RiverSegment(locBased.get(loc), river.getName(), river.getSegments().get(idx - 1),
                    river.getSegments().get(idx + 1), t);
        }

        return new RiverSegment(locBased.get(loc), river.getName(), river.getSegments().get(idx - 1),
                river.getSegments().get(idx + 1), new HashSet<>());
    }

    public static JSONObject toJSONPOI(POI poi) {
        JSONObject out = new JSONObject();
        if(poi instanceof RiverSegment) {
            RiverSegment riverSegment = (RiverSegment) poi;
            out.put("type", "riverSegment");
            out.put("riverId", riverSegment.getRiverId());
            out.put("name", riverSegment.getName());
            out.put("in", riverSegment.getIn());
            out.put("out", riverSegment.getOut());
            JSONArray trib = new JSONArray();
            riverSegment.getTributaries().stream().forEach(t -> {
                JSONObject tr = new JSONObject();
                tr.put("loc", t.getKey());
                tr.put("riverIdx", t.getValue());
                trib.add(tr);
            });
        } else {
            System.out.println("WUH OH! LOOKS LIKE TROUBLE, BIG HOSS (toJSONPOI)");
        }
        return out;
    }
}
