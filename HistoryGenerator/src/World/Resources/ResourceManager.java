package World.Resources;

import World.PointOfInterest.POI;
import World.Territory.Biome.Biome;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceManager {
    /**
     * This method adds resources to the territory based off the available POIs and biome
     * @param poi the set of places of interest for this territory
     * @param biome
     * @param seed
     * @return
     */
    public static HashMap<Resource, Integer> addResources(final Set<POI> poi, final Biome biome, final int seed) {
        final Map<Resource, Integer> resources = new HashMap<>();
        poi.stream().forEach(p->{
            p.getResources().forEach((r,i)->{
                if(resources.containsKey(r)) {
                    final int curVal = resources.get(r);
                    if(curVal != Integer.MAX_VALUE) {
                        final int nuVal = genAmount(i, seed);
                        final int val;
                        if(nuVal == Integer.MAX_VALUE) {
                            val = Integer.MAX_VALUE;
                        } else {
                            val = curVal + nuVal;
                        }
                        resources.put(r, val);
                    }
                } else {
                    resources.put(r, genAmount(i, seed));
                }
            });
        });
        /*TODO: Add fresh water source and global resource mods to the final values of the resources here*/
        //Shouldnt ever be conflicting keys, but if there are it takes the most recent
        //filter out negative resources
        return resources.entrySet().stream().filter(e->e.getValue()>0).collect(Collectors
                .toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new));
    }

    /**
     * Generates an amount of a resource based off resource base stats
     * @param stats is the base resource stats. Pair<mean, std dev>
     * @param seed is how we make the random
     * @return
     */
    private static int genAmount(final Pair<Integer, Integer> stats, final int seed) {
        final Random rand = new Random(seed);
        //stats is {mean : std dev}
        return (int)((rand.nextGaussian() * stats.getValue()) + stats.getKey());
    }

    public static JSONObject toJSONRes(final Resource res) {
        final JSONObject result = new JSONObject();
        result.put("name", res.getName());
        return  result;
    }
}