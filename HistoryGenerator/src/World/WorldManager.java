package World;

import World.Rivers.RiverManager;
import World.Territory.Territory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class WorldManager {

    //This is fixed now (thanks Brandon)
    public static ArrayList<String> biomeSearch(final HashMap<String, Territory> territoryMap,
                                         final String code,
                                         final HashSet<String> frontier,
                                         final ArrayList<String> res,
                                         final Territory t) {

        //get arraylist of neighbors for t
        ArrayList<String> neighbors = t.getNeighbors();
        //search through neighbors and store any matching biome neighbors, and continues to search from them.
        for (String neighbor : neighbors) {
            //if the neighbor location is not used yet, we will continue
            if (frontier.contains(neighbor)) {
                //get new territory object from neighbor location
                final Territory neighbor_t = territoryMap.get(neighbor);
                // Check to make sure the territories have the same biome type
                if (code.equals(neighbor_t.getBiome().getCode())) {
                    //remove the new neighbor from the list
                    frontier.remove(neighbor);
                    //adds new biome
                    res.add(neighbor);
                    //continue searching
                    final ArrayList<String> bResults = biomeSearch(territoryMap, t.getBiome().getCode(), frontier, res, t);
                    //Add all elements from neighbors to res.
                    res.addAll(bResults);
                }
            }
        }
        return res;
    }

    /*
        Return a hashmap with <integer, biome> (biome matched with key)
        function that takes in a territory map and returns a map of the biomes
     */
    public static HashMap<Integer, Region> createRegions(final HashMap<String, Territory> territoryMap,
                                                         final boolean debug, final int size, final int seed) {
        //Map index to biome
        final HashMap<Integer, Region> biomes = new HashMap<Integer, Region>();
        //Map ter location to biome index
        final HashMap<String, Integer> mapTer = new HashMap<String, Integer>();
        //Set of all of the r|c coords
        final HashSet<String> frontier = new HashSet<>();
        //TODO: This isnt the functional way to do this but I just want it to work so sort it out later
        int count = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                final String loc = row + "|" + col;
                //add coord to frontier
                frontier.add(loc);
            }
        }

        while (frontier.size() > 0) {
            //get new territory to expand into a region
            final String newTerritoryLoc = getRandomElement(frontier, seed);
            //remove location from frontier
            frontier.remove(newTerritoryLoc);
            //get the territory object from territoryMap
            final Territory t = territoryMap.get(newTerritoryLoc);
            //results list for terrs that belong in biome
            final ArrayList<String> res = new ArrayList<>();
            //Get the region as list of locs
            final ArrayList<String> bResults = biomeSearch(territoryMap, t.getBiome().getCode(), frontier, res, t);
            //add this territory to list of territories to be added to mapTer
            bResults.add(newTerritoryLoc);
            //Make new region
            final Region nuBiome = new Region(t, count, bResults);
            //
            for (String r : bResults) {
                mapTer.put(r, count);
            }
            //Map index to biome
            biomes.put(count, nuBiome);
            count++;
        }
        return biomes;
    }

    private static <E>
    E getRandomElement(final Set<? extends E> set, final int seed) {

        Random random = new Random(seed);

        // Generate a random number using nextInt
        // method of the Random class.
        int randomNumber = random.nextInt(set.size());

        Iterator<? extends E> iterator = set.iterator();

        int currentIndex = 0;
        E randomElement = null;

        // iterate the HashSet
        while (iterator.hasNext()) {

            randomElement = iterator.next();

            // if current index is equal to random number
            if (currentIndex == randomNumber)
                return randomElement;

            // increase the current index
            currentIndex++;
        }

        return randomElement;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject asJSON(final IWorld w) {
        //TODO finalize function
        JSONObject worldJSON = new JSONObject();
        JSONArray regionsJSON = new JSONArray();
        JSONArray riverList = new JSONArray();

        //  final HashMap<Integer, Region> regions;
        w.getRegions().forEach((number, region) -> {
            JSONObject regionJSON = new JSONObject();
            JSONObject regionDetails = new JSONObject();
            JSONArray territoriesJSON = new JSONArray();
            regionJSON.put("number", number);
            regionDetails.put("type", region.getType());
            regionDetails.put("index", region.getIndex());
            regionDetails.put("name", region.getName());
            region.getLocations().forEach(loc -> {
                territoriesJSON.add(w.getTerritoryMap().get(loc).asJSON());
            });
            regionDetails.put("territories", territoriesJSON);
            regionJSON.put("regionDetails", regionDetails);
            regionsJSON.add(regionJSON);

        });
        w.getRivers().keySet().stream().forEach(k -> riverList.add(RiverManager.riverToJSON(w.getRivers().get(k))));
        worldJSON.put("seed", w.getSeed());
        worldJSON.put("size", w.getSize());
        worldJSON.put("regions", regionsJSON);
        worldJSON.put("rivers", riverList);

        return worldJSON;
    }

    public static String biomeDebugRender(final HashMap<String, Integer> mapTer, final HashMap<Integer, Region> regions,
                                   final int size) {
        String out = "[WORLD DEBUG]------------------------------------------\n" +
                "Region/Biome Map: (Note that index is off by one from number that will appear in explore view)\n";
        System.out.println(mapTer);
        for (int x = size - 1; x >= 0; x--) {
            //This is for hex view
            if (x % 2 != 0) {
                out = out + "   ";
            }
            for (int y = 0; y < size; y++) {
                String key = Integer.toString(x) + "|" + Integer.toString(y);
                System.out.println(key);
                Region b = regions.get(mapTer.get(key));
                String num;
                if (b.getIndex() < 10) {
                    num = "00" + b.getIndex();
                }
                else if (b.getIndex() < 100) {
                    num = "0" + b.getIndex();
                } else {
                    num = "" + b.getIndex();
                }
                out += "[" + num + "," + b.getType() + "]" + " ";
                if(mapTer.get(key) == null) {
                    System.out.println("ERROR: " + key);
                }
            }
            out += "\n";
        }
        return out;
    }
}