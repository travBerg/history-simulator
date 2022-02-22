package World.PointOfInterest;

import World.Rivers.River;
import World.Territory.Biome.Biome;
import World.World;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class POIManager {

    public static Set<POI> createWatersourcePOI(final Set<POI> pOI, final Biome biome, final Random rand, final String loc) {
        final HashSet<POI> watersources = new HashSet<>();
        //If this territory has a river segment
        pOI.stream().filter(p->p instanceof RiverSegment).findFirst().map(r->{
            //Generate lakes
            final RiverSegment river = (RiverSegment) r;
            if(river.getOut().equals(loc)) {
                //This is for when the river is the end
                watersources.add(new Lake("Unnamed Lake", Optional.ofNullable(river), biome));
            } else {
                final int n = rand.nextInt(100); //0-99
                //TODO: Change to constant
                if(n < World.SETTINGS.get("lakePercChanceRiver")) {
                    watersources.add(new Lake("Unnamed Lake", Optional.ofNullable(river), biome));
                }
            }
            return new Object();
        } );
        return watersources;
    }

    public static Optional<RiverSegment> createRiverPOI(final River river, final String loc, final Random rand,
                                     final HashMap<String, Integer> locBased) {
        //TODO: make source of river, make mouth of river, make river junctions
        final int idx = river.getSegments().indexOf(loc);
        if(idx == 0) {
            //River is head
            if(river.getSegments().size() > 1) {
                //Should never be tributary on first segment
                return Optional.ofNullable(new RiverSegment(locBased.get(loc), river.getName(), loc, river.getSegments().get(1),
                        new HashSet<Pair<String,Integer>>()));
            }
            //River is also mouth
            return Optional.ofNullable(new RiverSegment(locBased.get(loc), river.getName(), loc, loc, new HashSet<>()));
        }
        if(idx == river.getSegments().size() - 1) {
            //River is mouth
            //Check for tributaries so they can be added
            if(river.getTributaries().isPresent()) {
                Set<Pair<String, Integer>> t = river.getTributaries().map(a -> a.stream().filter(p->p.getKey()
                        .equals(loc)).collect(Collectors.toSet())).get();

                return Optional.ofNullable(new RiverSegment(locBased.get(loc), river.getName(), river.getSegments().get(idx - 1), loc, t));
            }
            return Optional.ofNullable(new RiverSegment(locBased.get(loc), river.getName(), river.getSegments().get(idx - 1), loc,
                    new HashSet<>()));
        }
        //river is middle segment
        //river segment has a tributary
        if(river.getTributaries().isPresent()) {
            Set<Pair<String, Integer>> t = river.getTributaries().map(a -> a.stream().filter(p->p.getKey()
                    .equals(loc)).collect(Collectors.toSet())).get();

            return Optional.ofNullable(new RiverSegment(locBased.get(loc), river.getName(), river.getSegments().get(idx - 1),
                    river.getSegments().get(idx + 1), t));
        }

        return Optional.ofNullable(new RiverSegment(locBased.get(loc), river.getName(), river.getSegments().get(idx - 1),
                river.getSegments().get(idx + 1), new HashSet<>()));
    }

    public static JSONObject toJSONPOI(POI poi) {
        JSONObject out = new JSONObject();
        if(poi instanceof RiverSegment) {
            final RiverSegment riverSegment = (RiverSegment) poi;
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
            out.put("tributaries", trib);
            return out;
        }
        if(poi instanceof Lake) {
            final Lake lake = (Lake) poi;
            out.put("type", "lake");
            out.put("name", lake.getName());
            out.put("riverId", lake.getRiver().map(r->r.riverId).orElse(-1));
            return out;
        }
        if(poi instanceof Wilderness) {
            final Wilderness wild = (Wilderness) poi;
            out.put("type", "wilderness");
            out.put("name", wild.getName());
        }
        else {
            System.out.println("WUH OH! LOOKS LIKE TROUBLE, BIG HOSS (toJSONPOI)");
        }
        return out;
    }
}
