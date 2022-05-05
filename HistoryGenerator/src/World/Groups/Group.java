package World.Groups;

import Logger.Logger;
import WordGenerator.LanguageModel;
import World.Groups.Language.Language;
import World.Groups.Language.LanguageManager;
import World.Resources.Resource;
import World.Territory.Territory;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Stream;

public class Group {
    final private int population;
    //id is a mix of created location + starting pop + year (may want to add more to ensure uniqueness)
    //row|col.pop.year
    final private String id;
    final private Language language;
    final private String name;
    final private String nameTranslation;

    private static final Logger LOG = Logger.getLogger(Group.class);

    public Group(final Territory t, final LanguageModel model, final Random rand) {
        //Get the maximum possible population
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
        //use maximum population as upper bound for randomly rolled population
        //min population is 5 unless resources restrict it further
        this.population = (maxPop > 5) ? rand.nextInt(maxPop - 4) + 5 : maxPop;
        //create language
        //TODO: Add rand to this so that we can make meta-rules for word ordering etc.
        final Pair<String[], Map<String,String>> nameInfo = LanguageManager.initTrueGroupName(model, t, rand);
        this.name = nameInfo.getKey()[0];
        this.nameTranslation = nameInfo.getKey()[1];
        this.language = LanguageManager.createLanguage(model, nameInfo.getKey()[2], nameInfo.getValue());
        this.id = t.getLocation() + "." + population + "." + 0 + "." + name;

    }

    public String getId() { return id; }
    public int getPopulation() {return population;}
    public Language getLanguage() { return language; }
    public String getName() { return name; }
    public String getNameTranslation() { return nameTranslation; }
}
