package World.Resources;

import World.Animals.Animal;
import World.PointOfInterest.POI;
import World.Territory.Biome.Biome;
import World.World;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceManager {
    /**
     * This method adds resources to the territory based off the available POIs and biome
     * @param poi the set of places of interest for this territory
     * @param biome
     * @param rand randomizer
     * @return
     */
    public static Pair<HashMap<Resource, Integer>, Set<Animal>> addResources(final Set<POI> poi, final Biome biome, final Random rand) {
        final Map<Resource, Integer> resources = new HashMap<>();
        //List of animals
        final Set<Animal> animals = new HashSet<>();
        poi.stream().forEach(p->{
            //Unpack the animals for this poi into a list of pairs of its resources with its generated number
            final List<Pair<Resource, Integer>> aniRes = p.getAnimals().entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .map(r->{
                        final List<Pair<Resource, Integer>> resList = ResourceManager.convertToResList(r,rand);
                        if(!resList.isEmpty()) { animals.add(r.getKey()); }
                        return resList;
                    }).flatMap(List::stream).collect(Collectors.toList());
            //convert poi resource map to List<Pair<Resource, Integer>> and combine with the animal one before
            // running it through process of adding to resource map
            List<Pair<Resource, Integer>> total = Stream.concat(p.getResources().entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .map(e->{
                        final Pair<Resource, Integer> prr = new Pair<Resource, Integer>(e.getKey(), genAmount(e.getValue(), rand));
                        //System.out.println("Resource: " + prr.getKey() + " Amount: " + prr.getValue());
                        return prr;
                    })
                    .collect(Collectors.toList()).stream(), aniRes.stream()).collect(Collectors.toList());
            //add all poi resources to resource map
            total.stream().forEach(pair->{
                final Resource r = pair.getKey();
                final Integer nuVal = pair.getValue();
                if(resources.containsKey(r)) {
                    final int curVal = resources.get(r);
                    if(curVal != Integer.MAX_VALUE) {
                        final int val;
                        if(nuVal == Integer.MAX_VALUE) {
                            val = Integer.MAX_VALUE;
                        } else {
                            val = curVal + nuVal;
                        }
                        resources.put(r, val);
                    }
                } else {
                    resources.put(r, nuVal);
                }
            });


        });
        // Add fresh water source and global resource mods to the final values of the resources here
        final int freshWaterMod;
        if(resources.containsKey(Resource.WATER) && resources.get(Resource.WATER) >= 100000) {
            freshWaterMod = 3;
        } else {
            freshWaterMod = 1;
        }
        resources.forEach((r,n) -> {
            final String resModName = r.getName()+"_mod";
            final double resMod = World.RESOURCE_MODS.getOrDefault(resModName, 1.0);
            if(r.isFreshBoosted()) {
                resources.put(r, (int)(n * resMod * freshWaterMod));
            } else {
                resources.put(r, (int)(n * resMod));
            }
        });
        //Shouldnt ever be conflicting keys, but if there are it takes the most recent.
        //Filter out negative resources
        return new Pair<>(resources.entrySet().stream().filter(e->e.getValue()>0).collect(Collectors
                .toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next, HashMap::new)), animals);
    }

    private static List<Pair<Resource, Integer>> convertToResList(final Map.Entry<Animal, Pair<Integer,Integer>> entry,
                                                                  final Random rand) {
        //Get number for animal, shared by all that animal's resources
        final int n = genAmount(entry.getValue(), rand);
        //If 0 or less, return empty list
        if(n <= 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(entry.getKey().getResources()).filter(pr->!pr.getValue())
                .map(x->new Pair<Resource, Integer>(x.getKey(), n))
                .collect(Collectors.toList());
    }

    /**
     * Generates an amount of a resource based off resource base stats
     * @param stats is the base resource stats. Pair<mean, std dev>
     * @param rand is how we make the random
     * @return
     */
    private static int genAmount(final Pair<Integer, Integer> stats, final Random rand) {
        //stats is {mean : std dev}
        return (int)((rand.nextGaussian() * stats.getValue()) + stats.getKey());
    }

    public static JSONObject toJSONRes(final Resource res) {
        final JSONObject result = new JSONObject();
        result.put("name", res.getName());
        return  result;
    }
}