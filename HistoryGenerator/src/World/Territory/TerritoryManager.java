package World.Territory;

import World.PointOfInterest.POI;
import World.PointOfInterest.POIManager;
import World.PointOfInterest.RiverSegment;
import World.Resources.Resource;
import World.Rivers.River;
import World.Rivers.RiverManager;
import World.Territory.Biome.Biome;
import javafx.util.Pair;

import java.util.*;

public class TerritoryManager {

    public static Pair<Integer, Integer> parseLocation(final String location) {
        String array[] = location.split("\\|");
        final int row = Integer.parseInt(array[0]);
        final int col = Integer.parseInt(array[1]);
        Pair<Integer, Integer> nuLocation = new Pair<>(row, col);
        return nuLocation;
    }

    public static ArrayList<Integer> test(final HashMap<Integer,Integer> t) {
        final ArrayList<Pair<Integer,Integer>> list = new ArrayList<>();
        list.add(new Pair<>(1,5));
        list.add(new Pair<>(2,5));
        list.add(new Pair<>(3,5));
        final ArrayList<Integer> result = new ArrayList<>();
        list.stream().forEach(p->{
            t.replace(p.getKey(), p.getValue());
            result.add(p.getValue());
        });
        return result;
    }

    public static ArrayList<String> addNeighbors(final String location, final int size) {
        Pair<Integer, Integer> coords = parseLocation(location);
        int row = coords.getKey();
        int col = coords.getValue();
        ArrayList<String> neighbors = new ArrayList<>();

        boolean odd = (row % 2 != 0);
        if(odd) {
            //top, bottom, left, and all the rights
            for(int i = row - 1; i <= row + 1; i++) {
                for(int j = col - 1; j <= col + 1; j++){
                    if(!(i < row && j < col) && !(i > row && j < col) &&
                            j >= 0 && j < size && i >= 0 && i < size &&
                            !(i == row && j == col)) {
                        neighbors.add(i + "|" + j);
                    }
                }
            }
        } else {
            //top, bottom, right, and all the lefts
            for(int i = row - 1; i <= row + 1; i++) {
                for(int j = col - 1; j <= col + 1; j++){
                    if(!(i < row && j > col) && !(i > row && j > col) &&
                            j >= 0 && j < size && i >= 0 && i < size &&
                            !(i == row && j == col)) {
                        neighbors.add(i + "|" + j);
                    }
                }
            }
        }

        return neighbors;
    }

    //terrain is
    //key:location value:[type,hrt]
    public static HashMap<String, Territory> createTerritoryMap(final Random rand, final HashMap<String, ArrayList<String>> terrain,
                                                         final HashMap<Integer, River> rivers,
                                                         final HashMap<String, Integer> locBased, final int size) {
        final HashMap<String, Territory> tMap = new HashMap<String, Territory>();
        for (String location:terrain.keySet()) {
            final ArrayList<String> terr = terrain.get(location);
            final Territory territory = createTerritory(terr.get(0), terr.get(1), location, rand, size, rivers, locBased);
            tMap.put(location, territory);
        }
        return tMap;
    }

    public static Territory createTerritory(final String type, final String hrt, final String location, final Random rand,
                                     final int size, final HashMap<Integer, River> rivers,
                                     final HashMap<String, Integer> locBased) {
        final Territory t;
        switch(type) {
            case Biome.OCEAN_CODE:
                t = new Territory(location, rand, hrt, size, Biome.OCEAN, rivers, locBased);
                break;
            //case "CD":
            //    t = new ColdDesert(location, seed, hrt);
            //    break;
            case Biome.TUNDRA_CODE:
                t = new Territory(location, rand, hrt, size, Biome.TUNDRA, rivers, locBased);
                break;
            case Biome.GRASSLAND_CODE:
                t = new Territory(location, rand, hrt, size, Biome.GRASSLAND, rivers, locBased);
                break;
            //case "CW":
            //    t = new ConiferousWetlands(location, seed, hrt);
            //    break;
            case Biome.SHRUBLAND_CODE:
                t = new Territory(location, rand, hrt, size, Biome.SHRUBLAND, rivers, locBased);
                break;
            //case "PR":
            //    t = new Prairie(location, seed, hrt);
            //    break;
            //case "RS":
            //    t = new Marsh(location, seed, hrt);
            //    break;
            case Biome.SANDYDESERT_CODE:
                t = new Territory(location, rand, hrt, size, Biome.SANDYDESERT, rivers, locBased);
                break;
            case Biome.SAVANNA_CODE:
                t = new Territory(location, rand, hrt, size, Biome.SAVANNA, rivers, locBased);
                break;
            case Biome.SWAMP_CODE:
                t = new Territory(location, rand, hrt, size, Biome.SWAMP, rivers, locBased);
                break;
            case Biome.JUNGLE_CODE:
                t = new Territory(location, rand, hrt, size, Biome.JUNGLE, rivers, locBased);
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
                t = new Territory(location, rand, hrt, size, Biome.TAIGA, rivers, locBased);
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
                t = new Territory(location, rand, hrt, size, Biome.DECIDUOUSFOREST, rivers, locBased);
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
                t = new Territory(location, rand, hrt, size, Biome.GLACIER, rivers, locBased);
                break;
            case Biome.TUNDRAHILLS_CODE:
                t = new Territory(location, rand, hrt, size, Biome.TUNDRAHILLS, rivers, locBased);
                break;
            case Biome.GRASSHILLS_CODE:
                t = new Territory(location, rand, hrt, size, Biome.GRASSHILLS, rivers, locBased);
                break;
            case Biome.TAIGAHILLS_CODE:
                t = new Territory(location, rand, hrt, size, Biome.TAIGAHILLS, rivers, locBased);
                break;
            //case "XH":
            //    t = new DeadHills(location, seed, hrt);
            //    break;
            //case "IH":
            //    t = new MixedHills(location, seed, hrt);
            //    break;
            case Biome.BADLANDS_CODE:
                t = new Territory(location, rand, hrt, size, Biome.BADLANDS, rivers, locBased);
                break;
            case Biome.SHRUBLANDHILLS_CODE:
                t = new Territory(location, rand, hrt, size, Biome.SHRUBLANDHILLS, rivers, locBased);
                break;
            case Biome.DECHILLS_CODE:
                t = new Territory(location, rand, hrt, size, Biome.DECIDUOUSHILLS, rivers, locBased);
                break;
            case Biome.JUNGLEHILLS_CODE:
                t = new Territory(location, rand, hrt, size, Biome.JUNGLEHILLS, rivers, locBased);
                break;
            case Biome.MOUNTAIN_CODE:
                t = new Territory(location, rand, hrt, size, Biome.MOUNTAIN, rivers, locBased);
                break;
            case Biome.MOUNTAINTAIGA_CODE:
                t = new Territory(location, rand, hrt, size, Biome.MOUNTAINTAIGA, rivers, locBased);
                break;
            //case "MM":
            //    t = new MixedMountain(location, seed, hrt);
            //    break;
            case Biome.DECMOUNTAIN_CODE:
                t = new Territory(location, rand, hrt, size, Biome.DECIDUOUSMOUNTAIN, rivers, locBased);
                break;
            case Biome.JUNGLEMOUNTAIN_CODE:
                t = new Territory(location, rand, hrt, size, Biome.JUNGLEMOUNTAIN, rivers, locBased);
                break;
            case Biome.ALPTUNDRA_CODE:
                t = new Territory(location, rand, hrt, size, Biome.ALPINETUNDRA, rivers, locBased);
                break;
            default:
                System.out.println("WARNING: Territory tag defaulted");
                t = new Territory(location, rand, hrt, size, Biome.OCEAN, rivers, locBased);
        }

        return t;
    }

    /**
     * Returns true if the territory has both food and water
     * @param t the Territory we're checking
     * @return
     */
    public static boolean habitable(final Territory t) {
        return t.getResources().keySet().stream().anyMatch(Resource::isEdible) &&
                t.getResources().keySet().stream().anyMatch(Resource::isPotable);
    }

}
