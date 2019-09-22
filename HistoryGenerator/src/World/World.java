package World;

import TerrainGenerator.IGenerator;
import TerrainGenerator.TerrainGen;
import World.Territory.*;

import java.util.HashMap;

public class World implements IWorld{
    int seed;
    HashMap<String, Territory> territoryMap;

    public World(int seed, int size) {
        this.seed = seed;
        IGenerator generator = new TerrainGen(5, 690);
        System.out.println(((TerrainGen) generator).render());

    }

    public HashMap<String, Territory> createTerritoryMap(int seed, HashMap<String, String> terrain) {
        HashMap<String, Territory> tMap = new HashMap<String, Territory>();
        for (String location:terrain.keySet()) {
            String val = terrain.get(location);
            Territory territory = createTerritory(val, seed);
            tMap.put(location, territory);
        }
        return tMap;
    }

    public Territory createTerritory(String val, int seed) {
        Territory t;
        switch(val) {
            case "OC":
                t = new Ocean(seed);
                break;
            case "CD":
                t = new ColdDesert(seed);
                break;
            case "SL":
                t = new SnowField(seed);
                break;
            
            default:
                System.out.println("WARNING: Territory tag defaulted");
                t = new Ocean(seed);
        }

        return t;
    }
}
