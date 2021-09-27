package World.Territory;

import World.PointOfInterest.POI;
import World.PointOfInterest.POIManager;
import World.Resources.Resource;
import World.Rivers.River;
import World.Rivers.RiverManager;
import World.Territory.Biome.Biome;
import javafx.util.Pair;

import java.util.*;

public class TerritoryManager {
    /**River generation must happen before this
     *
     * Pseudocode for populating a territory with POI and Resources (maybe people eventually?)
     * @param
     * @return
     * 1. Create water sources (Aquifers, springs, etc.)
     * 2. Create material and water resources (based off biome and water POI)
     * 3. Create food and animals (based off water sources and biomes)
     * 4. Create shelter POI and populate with relevant animals and materials
     * public static Pair<List<POI>, List<Resources>> landscape(final Biome biome, final int seed,
     *                                                          final Optional<River> river, ArrayList<String> neighbors,
     *                                                          final int height, final int rain, final int temp){}
     */

    public static Pair<List<POI>, List<Resource>> landscape(final Biome biome, final int seed, final int riverId,
                                                            final Optional<River> river, final ArrayList<String> neighbors,
                                                            final int height, final int rain, final int temp) {
        //TODO: Don't include rivers in this, dingus
        final Random random = new Random(seed);
        //final Optional<POI> riverPOI = river.map(x-> POIManager.createRiverPOI(x, neighbors, riverId, seed));
        //ArrayList<River> test = new ArrayList<>();
        //river.map(test::add);
        return new Pair<>(new ArrayList<>(), new ArrayList<>());
    }


    public static Pair<Integer, Integer> parseLocation(final String location) {
        String array[] = location.split("\\|");
        final int row = Integer.parseInt(array[0]);
        final int col = Integer.parseInt(array[1]);
        Pair<Integer, Integer> nuLocation = new Pair<>(row, col);
        return nuLocation;
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
}
