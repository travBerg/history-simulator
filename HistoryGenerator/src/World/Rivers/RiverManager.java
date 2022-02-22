package World.Rivers;

import World.PointOfInterest.RiverSegment;
import World.Territory.Territory;
import World.Territory.TerritoryManager;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RiverManager {
    //Multiplier to percentage chance for river based on rain (r) value
    private final static double R_RIVER_PERC_MOD = 10;

    /**
     *
     * @param terrain Map of location to a [type, hrt]
     * @param start location of where river will start
     * @return a list of location of river segment POI
     */
    public static List<String> constructRiver(final Random random, final HashMap<String, ArrayList<String>> terrain, final String start, final int size) {
        final ArrayList<String> segments = new ArrayList<>();
        segments.add(start);
        return constructRiverHelp(random,segments,start,size,terrain);
    }

    private static List<String> constructRiverHelp(final Random random, final ArrayList<String> segments,
                                                   final String start, final int size,
                                                   final HashMap<String, ArrayList<String>> terrain){
        //Get neighbor territories
        final ArrayList<String> neighbors = TerritoryManager.addNeighbors(start, size);
        //Get candidates neighbors that are lower than the current hex
        final Optional<List<String>> candidates = Optional.of(neighbors.stream()
                .filter(n->!segments.contains(n) &&
                        (Integer.parseInt(terrain.get(n).get(1).substring(0,1)) <=
                        Integer.parseInt(terrain.get(start).get(1).substring(0,1))))
                .collect(Collectors.toList()));
        //pick a random candidate
        final String next = candidates.map(c->{
            if(c.size() < 1){
                return "STOP";
            }
            final int randIdx = random.nextInt(c.size());
            return c.get(randIdx);
        }).orElseThrow();

        if(next.equals("STOP")){
            return segments;
        }

        if(Integer.parseInt(terrain.get(next).get(1).substring(0,1)) == 0) {
            segments.add(next);
            return segments;
        }
        segments.add(next);
        return constructRiverHelp(random,segments,next,size,terrain);
    }

    //TODO: This is the method called by world
    //Create rivers as map (riverid to River) and map of locations to list of rivers
    /**
     *
     * @param random random from seed
     * @param terrain Map of location to a [type, hrt]
     * @param size Number of hexes to a side
     * 1. Iterate through locations finding river chance based on r value
     * 2. If river, call construct river.
     * 3. Check for intersections with other rivers
     * 4. If intersection, merge into larger river. Update river map if changes
     * 5. Update rivers and locBased map
     * @return (Map of riverid to river, Map of location to list of riverids)
     */
    public static Pair<HashMap<Integer, River>, HashMap<String, Integer>>
    getRiverMaps(final Random random, final HashMap<String, ArrayList<String>> terrain, final int size) {
        final Set<String> locKeys = terrain.keySet();
        final HashMap<Integer,River> rivers = new HashMap<>();
        final HashMap<String, Integer> locBased = new HashMap<>();
        //Get river starts
        final List<String> riverLocs = locKeys.stream().filter(loc -> {
            final int h = Integer.parseInt(terrain.get(loc).get(1).substring(0,1));
            if(h == 0) {
                return false;
            }
            final int r = Integer.parseInt(terrain.get(loc).get(1).substring(1,2));
            final int rand = random.nextInt(100) + 1;
            final boolean res = (rand < r * R_RIVER_PERC_MOD);
            return res;
        }).collect(Collectors.toList());
        System.out.println("Riverlocs: " + riverLocs);
        //TODO: Maybe shuffle the riverLocs?
        System.out.println("Rivers: ");

        //Start creating rivers/merging
        riverLocs.stream().forEach(loc -> {
            final River river = new River(random, terrain, loc, size);
            //Check if river needs to merge and merge it if so
            final List<String> segments = river.getSegments();
            final int idx = riverLocs.indexOf(loc);
            System.out.println(idx + ": " + river.getSegments());
            //if need to merge
            final String mergeLog = "MERGE LOG:\n"+ segments.stream().filter(locBased::containsKey).findFirst().map(s->{

                final int oldId = locBased.get(s);
                final River oldRiver = rivers.get(oldId);

                //Find if one intersect head of other
                if(oldRiver.getSegments().get(0).equals(s) || river.getSegments().get(0).equals(s)){
                    //      River where first segment is intersection (sub) is merged into other river (dom)
                    final River sub = ((oldRiver.getSegments().get(0).equals(s)) ? oldRiver : river);
                    final River dom = (!(oldRiver.getSegments().get(0).equals(s)) ? oldRiver: river);
                    //      Take whichever tail after the intersection is longer
                    final List<String> domTail = dom.getSegments()
                            .subList(dom.getSegments().indexOf(s),dom.getSegments().size());
                    final List<String> tail = ((domTail.size() >= sub.getSegments().size()) ? domTail : sub.getSegments());
                    final River result = new River(dom.getName(),
                            Stream.of(dom.getSegments().subList(0,dom.getSegments().indexOf(s)), tail)
                                    .flatMap(Collection::stream).collect(Collectors.toList()));

                    //erase the sub river from the maps if its in there
                    rivers.remove(locBased.get(s));
                    rivers.put(idx, result);
                    //remove from locBased
                    sub.getSegments().forEach(seg->locBased.remove(seg));
                    domTail.forEach(seg->locBased.remove(seg));
                    result.getSegments().forEach(seg->locBased.put(seg, idx));
                    return "Merge - head intersect\n";
                }

                //Non-head merge
                //Find which river bigger
                final River longer = ((oldRiver.getSegments().size() >= river.getSegments().size()) ? oldRiver : river);
                final River shorter = ((oldRiver.getSegments().size() < river.getSegments().size()) ? oldRiver : river);

                //Merge into bigger tail under name of bigger river
                final List<String> lTail = longer.getSegments().subList(longer.getSegments().indexOf(s),
                        longer.getSegments().size());
                final List<String> sTail = shorter.getSegments().subList(shorter.getSegments().indexOf(s),
                        shorter.getSegments().size());
                final List<String> tail = ((lTail.size() >= sTail.size()) ? lTail : sTail);

                //Check if long river came from old river
                if(locBased.containsKey(longer.getSegments().get(0))) {
                    System.out.println("OLD RIVER MERGE SHIT");
                    //Post-merge long river
                    final River lResult = new River(longer.getName(),
                            Stream.of(longer.getSegments().subList(0, longer.getSegments().indexOf(s)), tail)
                                    .flatMap(Collection::stream).collect(Collectors.toList()), Optional.empty(),
                            addTributary(longer.getTributaries(), new Pair(s, idx)));

                    //update old river with new tail in both maps
                    rivers.replace(oldId, lResult);
                    longer.getSegments().stream().forEach(locBased::remove);
                    lResult.getSegments().stream().forEach(seg->locBased.put(seg, oldId));
                    //Add short river as new river
                    rivers.put(idx, new River(shorter.getName(),
                            shorter.getSegments().subList(0, shorter.getSegments().indexOf(s)), Optional.ofNullable(oldId), Optional.empty()));
                    rivers.get(idx).getSegments().stream().forEach(seg->locBased.put(seg, idx));
                    return "Merge - new trimmed\n";
                }

                //update old (short) river by cutting off tail in both maps and noting merge
                rivers.replace(oldId, new River(shorter.getName(),
                        shorter.getSegments().subList(0, shorter.getSegments().indexOf(s)), Optional.ofNullable(idx), Optional.empty()));
                sTail.stream().forEach(locBased::remove);
                //Post-merge long river
                System.out.println("NEW RIVER MERGE SHIT");
                final River lResult = new River(longer.getName(),
                        Stream.of(longer.getSegments().subList(0, longer.getSegments().indexOf(s)), tail)
                                .flatMap(Collection::stream).collect(Collectors.toList()), Optional.empty(),
                        addTributary(longer.getTributaries(), new Pair<>(s,oldId)));
                //Add new (long) river to both maps
                rivers.put(idx, lResult);
                lResult.getSegments().stream().forEach(seg->locBased.put(seg,idx));
                return "Merge - old trimmed\n";
            }).orElseGet(()->{
                rivers.put(idx, river);
                segments.stream().forEach(s->locBased.put(s,idx));
                return "No merge\n";
            });
            //System.out.println(mergeLog);
        });
        //TODO: Maybe convert locBased to be <String, RiverSegment>?
        System.out.println("Final rivers\n-----------------------------------------------------------------------------------------");
        System.out.println("Rivers set: " + rivers.keySet());
        rivers.keySet().stream().forEach(x->System.out.println(x + ": " + rivers.get(x).getSegments() + " merge: " +
                rivers.get(x).getMerge().orElse(-1) + " Tributaries: " + rivers.get(x).getTributaries()));
        return new Pair<>(rivers, locBased);
    }

    public static Optional<Set<Pair<String,Integer>>> addTributary(Optional<Set<Pair<String, Integer>>> tributaries,
                                                                   Pair<String,Integer> newTrib) {
        tributaries.map(t->{
            t.add(newTrib);
            return t;
        });
        if(tributaries.isEmpty()) {
            final HashSet<Pair<String, Integer>> set = new HashSet<>();
            set.add(newTrib);
            return Optional.of(set);
        }
        return tributaries;
    }

    public static JSONObject riverToJSON(final River r) {
        JSONObject o = new JSONObject();
        o.put("name", r.getName());
        JSONArray segs = new JSONArray();
        r.getSegments().stream().forEach(s -> segs.add(s));
        o.put("segments", segs);
        o.put("merge", r.getMerge().orElse(null));
        //List of riverids
        JSONArray tribs = new JSONArray();
        r.getTributaries().ifPresent(t -> t.stream().forEach(p->tribs.add(p.getValue())));
        o.put("tributaries", tribs);
        return o;
    }
}
