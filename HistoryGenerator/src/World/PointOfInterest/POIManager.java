package World.PointOfInterest;

import Logger.Logger;
import WordGenerator.LanguageModel;
import WordGenerator.WordGenManager;
import World.Groups.Group;
import World.Groups.Language.Language;
import World.Groups.Language.LanguageManager;
import World.Rivers.River;
import World.Territory.Biome.Biome;
import World.Territory.Territory;
import World.World;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class POIManager {

    private static final Logger LOG = Logger.getLogger(POIManager.class);

    public static Set<POI> createWatersourcePOI(final Set<POI> pOI, final Biome biome, final Random rand, final String loc) {
        final HashSet<POI> watersources = new HashSet<>();
        //If this territory has a river segment
        pOI.stream().filter(p->p instanceof RiverSegment).findFirst().map(r->{
            //Generate lakes
            final RiverSegment river = (RiverSegment) r;
            if(river.getOut().equals(loc)) {
                //This is for when the river is the end
                watersources.add(new Lake("Unnamed Lake", Optional.of(river), biome));
            } else {
                final int n = rand.nextInt(100); //0-99
                //Compare to constant
                if(n < World.SETTINGS.get("lakePercChanceRiver")) {
                    watersources.add(new Lake("Unnamed Lake", Optional.of(river), biome));
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
                return Optional.of(new RiverSegment(locBased.get(loc), river.getCommonName().getKey(), loc, river.getSegments().get(1),
                        new HashSet<Pair<String,Integer>>()));
            }
            //River is also mouth
            return Optional.of(new RiverSegment(locBased.get(loc), river.getCommonName().getKey(), loc, loc, new HashSet<>()));
        }
        if(idx == river.getSegments().size() - 1) {
            //River is mouth
            //Check for tributaries so they can be added
            if(river.getTributaries().isPresent()) {
                Set<Pair<String, Integer>> t = river.getTributaries().map(a -> a.stream().filter(p->p.getKey()
                        .equals(loc)).collect(Collectors.toSet())).get();

                return Optional.of(new RiverSegment(locBased.get(loc), river.getCommonName().getKey(), river.getSegments().get(idx - 1), loc, t));
            }
            return Optional.of(new RiverSegment(locBased.get(loc), river.getCommonName().getKey(), river.getSegments().get(idx - 1), loc,
                    new HashSet<>()));
        }
        //river is middle segment
        //river segment has a tributary
        if(river.getTributaries().isPresent()) {
            Set<Pair<String, Integer>> t = river.getTributaries().map(a -> a.stream().filter(p->p.getKey()
                    .equals(loc)).collect(Collectors.toSet())).get();

            return Optional.of(new RiverSegment(locBased.get(loc), river.getCommonName().getKey(), river.getSegments().get(idx - 1),
                    river.getSegments().get(idx + 1), t));
        }

        return Optional.of(new RiverSegment(locBased.get(loc), river.getCommonName().getKey(), river.getSegments().get(idx - 1),
                river.getSegments().get(idx + 1), new HashSet<>()));
    }

    /**
     * Method to discover all POI in a territory
     * @param oldPOI set of undiscovered POI
     * @param discoverers the discovering group
     * @param rand random
     * @param terr the territory being discovered
     * @return a set of named POI
     */
    public static Set<POI> discoverAllPOI(final Set<POI> oldPOI, final Group discoverers, final Random rand,
                                          final Territory terr) {
        //TODO: Caves
        //Make the set ordered so that its reproducible
        final LinkedHashSet<POI> oldPOISorted = new LinkedHashSet(oldPOI);
        return oldPOISorted.stream().map(p->{
            //System.out.println(p);
            if(p instanceof Wilderness) {
                return discoverWilderness((Wilderness) p, terr.getName(), discoverers.getId());
            } else if(p instanceof RiverSegment) {
                return discoverRiverSeg((RiverSegment) p, terr.getName(), discoverers, rand, terr.getNameMeaning(),
                        terr.getBiome());
            } else if(p instanceof Lake) {
                return discoverLake((Lake) p, terr.getName(), discoverers, rand, terr.getNameMeaning(), terr.getBiome());
            } else {
                return p;
            }
        }).collect(Collectors.toSet());
    }

    /**
     * Method for when a wilderness is first discovered and named
     * @param wild pre-named wilderness state
     * @param terName name of overall territory
     * @param discId ID of discovering group
     * @return new named and discovered wilderness
     */
    private static POI discoverWilderness(final Wilderness wild, final String terName, final String discId) {
        final String wildName = terName + " Wilderness";
        return new Wilderness(wild, wildName, Optional.of(discId));
    }

    /**
     * Method for naming/discovering river seg. Will either name after terr or give new related name
     * @param seg the unnamed riverSeg
     * @param terName the territory name
     * @param disc the discovering group
     * @param rand random
     * @return a new, named riverSeg
     */
    private static POI discoverRiverSeg(final RiverSegment seg, final String terName, final Group disc,
                                        final Random rand, final String terNameMeaning, final Biome biome) {
        final Pair<String, String> names = LanguageManager.genRiverName(biome, disc.getLanguage(), rand, terName, terNameMeaning);
        return new RiverSegment(seg, names.getKey(), names.getValue(), Optional.of(disc.getId()));
    }

    /**
     * Method for naming/discovering lakes. Will either name after terr or give new related name
     * @param lake unnamed lake
     * @param terName territory name
     * @param disc discovering group
     * @param rand random
     * @param terNameMeaning territory name meaning
     * @param biome biome
     * @return new named Lake
     */
    private static POI discoverLake(final Lake lake, final String terName, final Group disc, final Random rand,
                                    final String terNameMeaning, final Biome biome) {
        final Pair<String, String> names = LanguageManager.genLakeName(biome, disc.getLanguage(), rand, terName, terNameMeaning);
        return new Lake(lake, names.getKey(), names.getValue(), Optional.of(disc.getId()));
    }

    public static JSONObject toJSONPOI(POI poi) {
        JSONObject out = new JSONObject();
        out.put("discoverer", poi.getDiscovered());
        out.put("name", poi.getName());
        if(poi instanceof RiverSegment) {
            final RiverSegment riverSegment = (RiverSegment) poi;
            out.put("type", "riverSegment");
            out.put("riverId", riverSegment.getRiverId());
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
            out.put("nameMeaning", riverSegment.getNameMeaning());
            return out;
        }
        if(poi instanceof Lake) {
            final Lake lake = (Lake) poi;
            out.put("type", "lake");
            out.put("riverId", lake.getRiver().map(r->r.riverId).orElse(-1));
            out.put("nameMeaning", lake.getNameMeaning());
            return out;
        }
        if(poi instanceof Wilderness) {
            final Wilderness wild = (Wilderness) poi;
            out.put("type", "wilderness");
        }
        else {
            LOG.debug("WUH OH! LOOKS LIKE TROUBLE, BIG HOSS (toJSONPOI)");
        }
        return out;
    }
}
