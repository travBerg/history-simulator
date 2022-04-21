package World.Groups;

import Logger.Logger;
import World.Resources.Resource;
import World.Territory.Territory;

import java.util.*;
import java.util.stream.Stream;

public class Group {
    final private int population;
    //id is a mix of created location + starting pop + year (may want to add more to ensure uniqueness)
    //row|col.pop.year
    final private String id;

    private static final Logger LOG = Logger.getLogger(Group.class);

    public Group(final Territory t, final Random rand) {
        final Optional<Integer> maxPopFood = t.getResources().entrySet().stream()
                .filter(r->r.getKey().isEdible()).map(Map.Entry::getValue).reduce(Integer::sum);
        final Optional<Integer> maxPopDrink = t.getResources().entrySet().stream().filter(r->r.getKey().isPotable())
                .map(Map.Entry::getValue).reduce(Integer::sum);
        final Optional<Integer> maxPopRes = Stream.of(maxPopFood, maxPopDrink).flatMap(Optional::stream)
                .min(Integer::compare);
        final int maxPop = maxPopRes.orElseGet(() -> {
                LOG.debug("WARNING: uninhabitable terr habited at " + t.getRow() + "|" + t.getCol());
                return 1;
        });
        //min population is 5 unless resources restrict it further
        this.population = (maxPop > 5) ? rand.nextInt(maxPop - 4) + 5 : maxPop;
        this.id = t.getLocation() + "." + population + "." + 0;
    }

    public String getId() { return id; }
    public int getPopulation() {return population;};
}
