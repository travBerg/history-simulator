package World;

import TerrainGenerator.IGenerator;
import TerrainGenerator.TerrainGen;
import World.Rivers.River;
import World.Rivers.RiverManager;
import World.Territory.*;
import World.Territory.Biome.Biome;
import javafx.util.Pair;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class World implements IWorld {
    final int size;
    final int seed;
    final HashMap<String, Territory> territoryMap;
    final HashMap<Integer, Region> regions;
    final HashMap<Integer, River> rivers;

    public World(final int seed, final int sizeCon, final int poles, final boolean debug) {
        this.seed = seed;
        this.size = (int) Math.pow(2, sizeCon) + 1;
        final IGenerator generator = new TerrainGen(this.size, seed, poles);
        /**
         * River plan
         * Create rivers as map (riverid to River) and map of locations to list of rivers
         * Save river map as a World parameter
         * Send location to list of rivers map to createTerritoryMap so that individual terr can access the info when constructing
         */
        final Pair<HashMap<Integer, River>, HashMap<String, Integer>> riverMaps = RiverManager.getRiverMaps(seed,
                generator.returnProduct(), size);

        this.territoryMap = createTerritoryMap(this.seed, generator.returnProduct(), riverMaps.getKey(), riverMaps.getValue());
        this.rivers = riverMaps.getKey();
        //this.regions = new HashMap<Integer, Region>();
        this.regions = createRegions(this.territoryMap, debug);
        if (debug) {
            System.out.println("Size: " + this.size + "x" + this.size);
            System.out.println("Territories: " + this.size * this.size);
            System.out.println("Regions: " + regions.size());
            //System.out.println(this.territoryMap.get("1|0"));
            System.out.println("Biome to hrt map:\n" + generator.render());
            System.out.println("--------------------------------------------------------");
        }
    }

    //This is fixed now (thanks Brandon)
    public ArrayList<String> biomeSearch(final HashMap<String, Territory> territoryMap,
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
    public HashMap<Integer, Region> createRegions(final HashMap<String, Territory> territoryMap, final boolean debug) {
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
            final String newTerritoryLoc = getRandomElement(frontier, this.seed);
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

    //TODO: If we start limiting sizes of regions, we're gonna need to add a seed parameter to this
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

    public String biomeDebugRender(final HashMap<String, Integer> mapTer, final HashMap<Integer, Region> biomes) {
        String out = "[WORLD DEBUG]------------------------------------------\n" +
                "Region/Biome Map: (Note that index is off by one from number that will appear in explore view)\n";
        for (int x = size - 1; x >= 0; x--) {
            //This is for hex view
            if (x % 2 != 0) {
                out = out + "   ";
            }
            for (int y = 0; y < size; y++) {
                String key = Integer.toString(x) + "|" + Integer.toString(y);
                Region b = biomes.get(mapTer.get(key));
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

    //terrain is
    //key:location value:[type,hrt]
    public HashMap<String, Territory> createTerritoryMap(final int seed, final HashMap<String, ArrayList<String>> terrain,
                                                         final HashMap<Integer, River> rivers,
                                                         final HashMap<String, Integer> locBased) {
        final HashMap<String, Territory> tMap = new HashMap<String, Territory>();
        for (String location:terrain.keySet()) {
            final ArrayList<String> terr = terrain.get(location);
            //Hacky solution to my silly coordinates problem
            final Pair<Integer, Integer> flip = TerritoryManager.parseLocation(location);
            final String nuLoc = ((0 - flip.getKey()) + this.size) - 1 + "|" + flip.getValue();
            final Territory territory = createTerritory(terr.get(0), terr.get(1), nuLoc, seed, this.size, rivers, locBased);
            tMap.put(nuLoc, territory);
        }
        return tMap;
    }

    public Territory createTerritory(final String type, final String hrt, final String location, final int seed,
                                     final int size, final HashMap<Integer, River> rivers,
                                     final HashMap<String, Integer> locBased) {
        final Territory t;
        switch(type) {
            case Biome.OCEAN_CODE:
                t = new Territory(location, seed, hrt, size, Biome.OCEAN, rivers, locBased);
                break;
            //case "CD":
            //    t = new ColdDesert(location, seed, hrt);
            //    break;
            case Biome.TUNDRA_CODE:
                t = new Territory(location, seed, hrt, size, Biome.TUNDRA, rivers, locBased);
                break;
            case Biome.GRASSLAND_CODE:
                t = new Territory(location, seed, hrt, size, Biome.GRASSLAND, rivers, locBased);
                break;
            //case "CW":
            //    t = new ConiferousWetlands(location, seed, hrt);
            //    break;
            case Biome.SHRUBLAND_CODE:
                t = new Territory(location, seed, hrt, size, Biome.SHRUBLAND, rivers, locBased);
                break;
            //case "PR":
            //    t = new Prairie(location, seed, hrt);
            //    break;
            //case "RS":
            //    t = new Marsh(location, seed, hrt);
            //    break;
            case Biome.SANDYDESERT_CODE:
                t = new Territory(location, seed, hrt, size, Biome.SANDYDESERT, rivers, locBased);
                break;
            case Biome.SAVANNA_CODE:
                t = new Territory(location, seed, hrt, size, Biome.SAVANNA, rivers, locBased);
                break;
            case Biome.SWAMP_CODE:
                t = new Territory(location, seed, hrt, size, Biome.SWAMP, rivers, locBased);
                break;
            case Biome.JUNGLE_CODE:
                t = new Territory(location, seed, hrt, size, Biome.JUNGLE, rivers, locBased);
                break;
            //case "ST":
            //    t = new Steppe(location, seed, hrt);
            //    break;
            //case "FF":
            //    t = new FrozenForest(location, seed, hrt);
            //    break;
            //case "XF":
            //    t = new DeadForest(location, seed, hrt);
            //    break;
            case Biome.TAIGA_CODE:
                t = new Territory(location, seed, hrt, size, Biome.TAIGA, rivers, locBased);
                break;
            //case "IF":
            //    t = new MixedForest(location, seed, hrt);
            //    break;
            //case "FG":
            //    t = new Fungal(location, seed, hrt);
            //    break;
            //case "RD":
            //    t = new RockyDesert(location, seed, hrt);
            //    break;
            case Biome.DECFOREST_CODE:
                t = new Territory(location, seed, hrt, size, Biome.DECIDUOUSFOREST, rivers, locBased);
                break;
            //case "DN":
            //    t = new SandDunes(location, seed, hrt);
            //    break;
            //case "PF":
            //    t = new PalmForest(location, seed, hrt);
            //    break;
            //case "RF":
            //    t = new Rainforest(location, seed, hrt);
            //    break;
            case Biome.GLACIER_CODE:
                t = new Territory(location, seed, hrt, size, Biome.GLACIER, rivers, locBased);
                break;
            case Biome.TUNDRAHILLS_CODE:
                t = new Territory(location, seed, hrt, size, Biome.TUNDRAHILLS, rivers, locBased);
                break;
            case Biome.GRASSHILLS_CODE:
                t = new Territory(location, seed, hrt, size, Biome.GRASSHILLS, rivers, locBased);
                break;
            case Biome.TAIGAHILLS_CODE:
                t = new Territory(location, seed, hrt, size, Biome.TAIGAHILLS, rivers, locBased);
                break;
            //case "XH":
            //    t = new DeadHills(location, seed, hrt);
            //    break;
            //case "IH":
            //    t = new MixedHills(location, seed, hrt);
            //    break;
            case Biome.BADLANDS_CODE:
                t = new Territory(location, seed, hrt, size, Biome.BADLANDS, rivers, locBased);
                break;
            case Biome.SHRUBLANDHILLS_CODE:
                t = new Territory(location, seed, hrt, size, Biome.SHRUBLANDHILLS, rivers, locBased);
                break;
            case Biome.DECHILLS_CODE:
                t = new Territory(location, seed, hrt, size, Biome.DECIDUOUSHILLS, rivers, locBased);
                break;
            case Biome.JUNGLEHILLS_CODE:
                t = new Territory(location, seed, hrt, size, Biome.JUNGLEHILLS, rivers, locBased);
                break;
            case Biome.MOUNTAIN_CODE:
                t = new Territory(location, seed, hrt, size, Biome.MOUNTAIN, rivers, locBased);
                break;
            case Biome.MOUNTAINTAIGA_CODE:
                t = new Territory(location, seed, hrt, size, Biome.MOUNTAINTAIGA, rivers, locBased);
                break;
            //case "MM":
            //    t = new MixedMountain(location, seed, hrt);
            //    break;
            case Biome.DECMOUNTAIN_CODE:
                t = new Territory(location, seed, hrt, size, Biome.DECIDUOUSMOUNTAIN, rivers, locBased);
                break;
            case Biome.JUNGLEMOUNTAIN_CODE:
                t = new Territory(location, seed, hrt, size, Biome.JUNGLEMOUNTAIN, rivers, locBased);
                break;
            case Biome.ALPTUNDRA_CODE:
                t = new Territory(location, seed, hrt, size, Biome.ALPINETUNDRA, rivers, locBased);
                break;
            default:
                System.out.println("WARNING: Territory tag defaulted");
                t = new Territory(location, seed, hrt, size, Biome.OCEAN, rivers, locBased);
        }

        return t;
    }

    @Override
    //Return list of pair of region idx and name
    public ArrayList<Pair<Integer, String>> getRegionList() {
        ArrayList<Pair<Integer, String>> result = new ArrayList<>();
        for (Integer i : regions.keySet()) {
            final String name = regions.get(i).getName();
            final Integer idx = regions.get(i).getIndex();
            final Pair<Integer, String> pair = new Pair<>(idx, name);
            result.add(pair);
        }
        return result;
    }

    @Override
    //Return list of location to name of each territory in indexed region
    public ArrayList<Pair<String, String>> getTerritories(int idx) {
        final ArrayList<Pair<String, String>> res = new ArrayList<>();
        final Region r = regions.get(idx);
        for (String l: r.getLocations()) {
            final Pair<String, String> pair = new Pair<>(l, territoryMap.get(l).getName());
            res.add(pair);
        }
        return res;
    }

    @Override @SuppressWarnings("unchecked")
    public JSONObject asJSON() {
        //TODO finalize function
        JSONObject worldJSON = new JSONObject();
        JSONArray regionsJSON = new JSONArray();
        JSONArray riverList = new JSONArray();

        //  final HashMap<Integer, Region> regions;
        this.regions.forEach((number, region) -> {
            JSONObject regionJSON = new JSONObject();
            JSONObject regionDetails = new JSONObject();
            JSONArray territoriesJSON = new JSONArray();
            regionJSON.put("number", number);
            regionDetails.put("type", region.getType());
            regionDetails.put("index", region.getIndex());
            regionDetails.put("name", region.getName());
            region.getLocations().forEach(loc -> {
                territoriesJSON.add(territoryMap.get(loc).asJSON());
            });
            regionDetails.put("territories", territoriesJSON);
            regionJSON.put("regionDetails", regionDetails);
            regionsJSON.add(regionJSON);

        });
        rivers.keySet().stream().forEach(k -> riverList.add(RiverManager.riverToJSON(rivers.get(k))));
        worldJSON.put("seed", this.seed);
        worldJSON.put("size", this.size);
        worldJSON.put("regions", regionsJSON);
        worldJSON.put("rivers", riverList);

        return worldJSON;
    }
}
