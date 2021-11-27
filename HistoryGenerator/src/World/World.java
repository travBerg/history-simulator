package World;

import TerrainGenerator.IGenerator;
import TerrainGenerator.TerrainGen;
import World.Rivers.River;
import World.Rivers.RiverManager;
import World.Territory.*;
import World.Territory.Biome.Biome;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class World implements IWorld {
    private final int size;
    private final int seed;
    //location string to Territory
    private final HashMap<String, Territory> territoryMap;
    private final HashMap<Integer, Region> regions;
    private final HashMap<Integer, River> rivers;

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

        this.territoryMap = TerritoryManager.createTerritoryMap(this.seed, generator.returnProduct(), riverMaps.getKey(),
                riverMaps.getValue(), this.size);
        this.rivers = riverMaps.getKey();
        //this.regions = new HashMap<Integer, Region>();
        this.regions = WorldManager.createRegions(this.territoryMap, debug, size, this.seed);
        if (debug) {
            System.out.println("Size: " + this.size + "x" + this.size);
            System.out.println("Territories: " + this.size * this.size);
            System.out.println("Terrirories by map: " + territoryMap.keySet().size());
            System.out.println("Territories by region: " + regions.values().stream().map(r->r.getLocations().size())
                    .collect(Collectors.summingInt(Integer::intValue)));
            System.out.println("Regions: " + regions.size());
            //System.out.println(this.territoryMap.get("1|0"));
            //TODO: Reimplement
            //System.out.println("Biome to Region map: \n" + WorldManager.biomeDebugRender(generator.returnProduct(), this.regions, this.size));
            System.out.println("Biome to hrt map:\n" + generator.render());
            System.out.println("--------------------------------------------------------");
        }
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

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getSeed() {
        return seed;
    }

    @Override
    public HashMap<String, Territory> getTerritoryMap() {
        return territoryMap;
    }

    @Override
    public HashMap<Integer, Region> getRegions() {
        return regions;
    }

    @Override
    public HashMap<Integer, River> getRivers() {
        return rivers;
    }

}
