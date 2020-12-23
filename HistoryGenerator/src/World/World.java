package World;

import TerrainGenerator.IGenerator;
import TerrainGenerator.TerrainGen;
import World.Territory.*;
import com.sun.security.jgss.InquireSecContextPermission;

import java.util.ArrayList;
import java.util.HashMap;

public class World implements IWorld{
    final int size;
    final int seed;
    final HashMap<String, Territory> territoryMap;
    final HashMap<Integer, Biome> biomes;

    public World(final int seed, final int sizeCon, final boolean debug) {
        this.seed = seed;
        this.size = (int) Math.pow(2, sizeCon) + 1;
        final IGenerator generator = new TerrainGen(this.size, seed);
        this.territoryMap = createTerritoryMap(this.seed, generator.returnProduct());
        this.biomes = createBiomes(this.territoryMap, debug);
        if (debug) {
            System.out.println("Size: " + this.size);
            System.out.println("Territories: " + this.size * this.size);
            System.out.println(this.territoryMap.get("1|0"));
            System.out.println(generator.render());
        }
    }

    /*
        Return a hashmap with <integer, biome> (biome matched with key)
        function that takes in a territory map and returns a map of the biomes
     */
    public HashMap<Integer, Biome> createBiomes(final HashMap<String, Territory> territoryMap, final boolean debug) {
        //Map index to biome
        final HashMap<Integer, Biome> biomes = new HashMap<Integer, Biome>();
        //Map ter location to biome index
        final HashMap<String, Integer> mapTer = new HashMap<String, Integer>();
        //TODO: This isnt the functional way to do this but I just want it to work so sort it out later
        int count = 0;
        for (int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++){
                final String loc = row + "|" + col;
                final Territory t = territoryMap.get(loc);
                final Biome nuBiome;
                //Existing biome
                if(mapTer.containsKey(loc)) {
                    nuBiome = biomes.get(mapTer.get(loc));
                //New biome
                } else {
                    nuBiome = new Biome(t, count);
                    biomes.put(count, nuBiome);
                    mapTer.put(loc, count);
                    count++;
                }
                final int next = col + 1;
                final int below = row + 1;
                //check next
                if(next < size) {
                    final String nextLoc = row + "|" + next;
                    if(territoryMap.get(nextLoc).getCode() == t.getCode()) {
                        if(mapTer.containsKey(nextLoc)) {
                            //Join next's biome in map
                            mapTer.put(loc, mapTer.get(nextLoc));
                            //Add it to next's Biome object
                            biomes.get(mapTer.get(nextLoc)).addLocation(loc);
                        } else {
                            //Add to next to our biome in map
                            mapTer.put(nextLoc, nuBiome.getIndex());
                            //Add it to Biome object
                            nuBiome.addLocation(nextLoc);
                        }

                    }
                }
                //check below
                if(below < size) {
                    final String belowLoc = below + "|" + col;
                    if(territoryMap.get(belowLoc).getCode() == t.getCode()) {
                        //Add to location->biomeid map
                        mapTer.put(belowLoc, nuBiome.getIndex());
                        //Add it to Biome object
                        nuBiome.addLocation(belowLoc);
                    }
                }
                //check
            }
        }
        if (debug) {
            System.out.println(biomeDebugRender(mapTer, biomes));
        }
        return biomes;
    }

    public String biomeDebugRender(final HashMap<String, Integer> mapTer, final HashMap<Integer, Biome> biomes) {
        String out = "Biome Map: \n";
        for (int x = 0; x < size; x++) {
            //if (x % 2 != 0) {
            //    out = out + "   ";
            //}
            for (int y = 0; y < size; y++) {
                String key = Integer.toString(x) + "|" + Integer.toString(y);
                Biome b = biomes.get(mapTer.get(key));
                String num;
                if (b.index < 10) {
                    num = "00" + b.index;
                }
                else if (b.index < 100) {
                    num = "0" + b.index;
                } else {
                    num = "" + b.index;
                }
                out += "[" + num + "," + b.type + "]" + " ";
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
}
