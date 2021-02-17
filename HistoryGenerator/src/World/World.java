package World;

import TerrainGenerator.IGenerator;
import TerrainGenerator.TerrainGen;
import World.Territory.*;
import com.sun.security.jgss.InquireSecContextPermission;
import javafx.util.Pair;

import java.beans.PropertyEditorSupport;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class World implements IWorld{
    final int size;
    final int seed;
    final HashMap<String, Territory> territoryMap;
    final HashMap<Integer, Region> regions;

    public World(final int seed, final int sizeCon, final int poles, final boolean debug) {
        this.seed = seed;
        this.size = (int) Math.pow(2, sizeCon) + 1;
        final IGenerator generator = new TerrainGen(this.size, seed, poles);
        this.territoryMap = createTerritoryMap(this.seed, generator.returnProduct());
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

    public ArrayList<String> biomeSearch(final HashMap<String, Territory> territoryMap, final String code,
                                         final HashMap<String, Boolean> discovered, final Queue<String> q,
                                         final ArrayList<String> res) {
        if (q.isEmpty()) {
            //System.out.println("Results: " + res);
            return res;
        }
        //pop front node from queue and print it
        final String loc = q.poll();
        if (territoryMap.get(loc).getCode() == code) {
            res.add(loc);
        }

        final ArrayList<String> locations = territoryMap.get(loc).getNeighbors();

        for (String l:locations) {
            if (territoryMap.containsKey(l)) {
                if (!discovered.containsKey(l) || !discovered.get(l)) {
                    discovered.put(l, Boolean.TRUE);
                    if (territoryMap.get(l).getCode() == code) {
                        q.add(l);
                    }
                }
            }
        }

        return biomeSearch(territoryMap, code, discovered, q, res);
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
        //TODO: This isnt the functional way to do this but I just want it to work so sort it out later
        int count = 0;
        for (int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++){
                final String loc = row + "|" + col;
                if(!mapTer.containsKey(loc)) {
                    final Territory t = territoryMap.get(loc);
                    //Map location to discovered or nah
                    final HashMap<String, Boolean> discovered = new HashMap<>();
                    discovered.put(loc, Boolean.TRUE);
                    //Queue of places to search
                    final Queue<String> q = new ArrayDeque<>();
                    q.add(loc);
                    //results list for terrs that belong in biome
                    final ArrayList<String> res = new ArrayList<>();
                    //Get the region as list of locs
                    final ArrayList<String> bResults = biomeSearch(territoryMap, t.getCode(), discovered, q, res);
                    //System.out.println("Test: " + bResults);
                    //Make new region
                    final Region nuBiome = new Region(t, count, bResults);
                    //Map each location to region
                    for (String r:bResults) {
                        mapTer.put(r, count);
                    }
                    //Map index to biome
                    biomes.put(count, nuBiome);
                    count++;
                }

            }
        }
        if (debug) {
            System.out.println(biomeDebugRender(mapTer, biomes));
        }
        return biomes;
    }

    public String biomeDebugRender(final HashMap<String, Integer> mapTer, final HashMap<Integer, Region> biomes) {
        String out = "[WORLD DEBUG]------------------------------------------\n" +
                "Region/Biome Map: (Note that index is off by one from number that will appear in explore view)\n";
        for (int x = 0; x < size; x++) {
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

    public HashMap<String, Territory> createTerritoryMap(final int seed, final HashMap<String, ArrayList<String>> terrain) {
        final HashMap<String, Territory> tMap = new HashMap<String, Territory>();
        for (String location:terrain.keySet()) {
            final ArrayList<String> terr = terrain.get(location);
            final Territory territory = createTerritory(terr.get(0), terr.get(1), location, seed, this.size);
            tMap.put(location, territory);
        }
        return tMap;
    }

    public Territory createTerritory(final String type, final String hrt, final String location, final int seed, final int size) {
        final Territory t;
        switch(type) {
            case Ocean.CODE:
                t = new Ocean(location, seed, hrt, size);
                break;
            //case "CD":
            //    t = new ColdDesert(location, seed, hrt);
            //    break;
            case Tundra.CODE:
                t = new Tundra(location, seed, hrt, size);
                break;
            case Grassland.CODE:
                t = new Grassland(location, seed, hrt, size);
                break;
            //case "CW":
            //    t = new ConiferousWetlands(location, seed, hrt);
            //    break;
            case Shrubland.CODE:
                t = new Shrubland(location, seed, hrt, size);
                break;
            //case "PR":
            //    t = new Prairie(location, seed, hrt);
            //    break;
            //case "RS":
            //    t = new Marsh(location, seed, hrt);
            //    break;
            case SandyDesert.CODE:
                t = new SandyDesert(location, seed, hrt, size);
                break;
            case Savanna.CODE:
                t = new Savanna(location, seed, hrt, size);
                break;
            case Swamp.CODE:
                t = new Swamp(location, seed, hrt, size);
                break;
            case Jungle.CODE:
                t = new Jungle(location, seed, hrt, size);
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
            case Taiga.CODE:
                t = new Taiga(location, seed, hrt, size);
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
            case DeciduousForest.CODE:
                t = new DeciduousForest(location, seed, hrt, size);
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
            case Glacier.CODE:
                t = new Glacier(location, seed, hrt, size);
                break;
            case TundraHills.CODE:
                t = new TundraHills(location, seed, hrt, size);
                break;
            case GrassyHills.CODE:
                t = new GrassyHills(location, seed, hrt, size);
                break;
            case TaigaHills.CODE:
                t = new TaigaHills(location, seed, hrt, size);
                break;
            //case "XH":
            //    t = new DeadHills(location, seed, hrt);
            //    break;
            //case "IH":
            //    t = new MixedHills(location, seed, hrt);
            //    break;
            case Badlands.CODE:
                t = new Badlands(location, seed, hrt, size);
                break;
            case ShrublandHills.CODE:
                t = new ShrublandHills(location, seed, hrt, size);
                break;
            case DeciduousHills.CODE:
                t = new DeciduousHills(location, seed, hrt, size);
                break;
            case JungleHills.CODE:
                t = new JungleHills(location, seed, hrt, size);
                break;
            case Mountain.CODE:
                t = new Mountain(location, seed, hrt, size);
                break;
            case MountainTaiga.CODE:
                t = new MountainTaiga(location, seed, hrt, size);
                break;
            //case "MM":
            //    t = new MixedMountain(location, seed, hrt);
            //    break;
            case DeciduousMountain.CODE:
                t = new DeciduousMountain(location, seed, hrt, size);
                break;
            case JungleMountain.CODE:
                t = new JungleMountain(location, seed, hrt, size);
                break;
            case "AT":
                t = new AlpineTundra(location, seed, hrt, size);
                break;
            default:
                System.out.println("WARNING: Territory tag defaulted");
                t = new Ocean(location, seed, hrt, size);
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
}
