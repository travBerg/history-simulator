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
        //Shouldnt ever be conflicting keys, but if there are it takes the most recent
        //filter out negative resources
        return resources.entrySet().stream().filter(e->e.getValue()>0).collect(Collectors
                .toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new));
    }

    private static int genAmount(final Pair<Integer, Integer> stats, final int seed) {
        final Random rand = new Random(seed);
        //stats is {mean : std dev}
        return (int)(rand.nextGaussian() * stats.getValue() + (stats.getKey()/*TODO: Add biome and global resource mods here*/));
    }

    public static JSONObject toJSONRes(final Resource res) {
        final JSONObject result = new JSONObject();
        result.put("name", res.getName());
        return  result;
    }
}