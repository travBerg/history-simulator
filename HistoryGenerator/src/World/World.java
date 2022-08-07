package World;

import Logger.Logger;
import TerrainGenerator.IGenerator;
import TerrainGenerator.TerrainGen;
import WordGenerator.LanguageModel;
import WordGenerator.LanguageModelType;
import World.Groups.Group;
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
    public static final HashMap<String, Integer> SETTINGS = new HashMap<>();
    public static final HashMap<String, Double> RESOURCE_MODS = new HashMap<>();
    public static final HashMap<String, Float> GROUP_MODS = new HashMap<>();

    private static final Logger LOG = Logger.getLogger(World.class);

    private final int size;
    private final int seed;
    //location string to Territory
    private final HashMap<String, Territory> territoryMap;
    private final HashMap<Integer, Region> regions;
    private final HashMap<Integer, River> rivers;
    private final HashMap<String, Group> groups;
    private final Set<LanguageModel> languageModels;

    public World(final HashMap<String, Integer> settings, final HashMap<String, Double> resSettings,
                 final HashMap<String, Float> groupMods, final Set<LanguageModel> languageModels) {
        //-------------INIT-------------------
        RESOURCE_MODS.putAll(resSettings);
        GROUP_MODS.putAll(groupMods);
        SETTINGS.putAll(settings);
        this.languageModels = languageModels;
        //TODO: I think this is the only place we should save seed and once time begins to move we just add year/month
        // to it, make a random, and pass that random around
        this.seed = SETTINGS.get("seed");
        final Random random = new Random(seed);
        this.size = (int) Math.pow(2, SETTINGS.get("sizeCon")) + 1;
        final IGenerator generator = new TerrainGen(this.size, seed, SETTINGS.get("poles"));
        LOG.stats("Size: " + this.size + "x" + this.size);
        LOG.stats("Territories: " + this.size * this.size);

        //-------------RIVERS-------------------
        final Pair<HashMap<Integer, River>, HashMap<String, Integer>> riverMaps = RiverManager.getRiverMaps(random,
               generator.returnProduct(), size);
        //Get the map of river ids to River object
        this.rivers = riverMaps.getKey();

        //--------TERRITORIES/REGIONS------------
        //Create the territory map
        final HashMap<String, Territory> prelimTers = TerritoryManager.createTerritoryMap(random, generator.returnProduct(), riverMaps.getKey(),
                riverMaps.getValue(), this.size);
        //Create the regions based off the territory map
        final Pair<HashMap<Integer, Region>, HashMap<String, Territory>> terRegPair =
                WorldManager.createRegions(prelimTers, SETTINGS.get("debug") != 0, size, random);
        this.regions = terRegPair.getKey();
        this.territoryMap = terRegPair.getValue();
        LOG.stats("Regions: " + regions.size());
        //Debug count of territories by region
        final int regionTerrs = regions.values().stream().map(r->r.getLocations().size()).reduce(0, Integer::sum);
        LOG.stats("Territories: " + territoryMap.size());
        LOG.stats("Region territories: " + regionTerrs);
        LOG.stats("Prelim territories: " + prelimTers.size());

        //---------------GROUPS----------------
        /**
         * Groups
         * Input regions and territoryMap
         * Output Pair<HashMap<String, Group>, Set<Territory>> - key is map of id to Group,
         * value is set of changed territories
         */
        final Pair<HashMap<String, Group>, Set<Territory>> popPair = WorldManager.populate(territoryMap, regions,
                languageModels, random);
        this.groups = popPair.getKey();
        LOG.stats("Groups: " + groups.size());

        //---------DISCOVERY/OVERWRITES--------
        //This not only overwrites edited Terrs, but also names Rivers based off discovered
        // riverSegs and names completed Regions based off their Terr
        //Overwrite edited Terrs
        popPair.getValue().forEach(t->{
            Territory ter = territoryMap.replace(t.getLocation(),t);
            if(ter == null && SETTINGS.get("debug") != 0) {
                LOG.debug("WARNING: Territory " + t.getLocation() + " failed to overwrite!");
            }
        });
        //Overwrite named Rivers and Regions
        final Pair<Map<Integer, River>, Map<Integer,Region>> riverRegionRenames =
                WorldManager.nameRiversAndRegions(popPair.getValue(), this.rivers, this.regions);
        //Overwrite rivers
        riverRegionRenames.getKey().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(r->{
            final River riv = rivers.replace(r.getKey(), r.getValue());
            if(riv == null && SETTINGS.get("debug") != 0) {
                LOG.debug("WARNING: River " + r.getKey() + " failed to overwrite!");
            }
        });
        //Overwrite regions
        riverRegionRenames.getValue().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(r->{
            final Region reg = regions.replace(r.getKey(), r.getValue());
            if(reg == null && SETTINGS.get("debug") != 0) {
                LOG.debug("WARNING: Region " + r.getKey() + " failed to overwrite!");
            }
        });
        LOG.stats("Terrs Overwritten: " + popPair.getValue().size());
        LOG.stats("Rivers Overwritten: " + riverRegionRenames.getKey().size());
        LOG.stats("Regions Overwritten: " + riverRegionRenames.getValue().size());
        final String discoveredTerrDetails = popPair.getValue().stream().map(t->"\n" + t.getName() +
                "\nMeaning: " + t.getNameMeaning() + "\nBiome: " + t.getBiome() + "\nDiscovered by: " + t.isDiscovered()
                + "\n").collect(Collectors.joining());
        LOG.stats("\n------------------------" + "\nDiscovered Terrs: " + discoveredTerrDetails);

        //---------LEGACY DEBUG--------------
        if (SETTINGS.get("debug") != 0) {
            //System.out.println(this.territoryMap.get("1|0"));
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

    @Override
    public HashMap<String, Group> getGroups() {
        return groups;
    }

}
