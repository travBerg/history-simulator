package World.Groups;

import Logger.Logger;
import WordGenerator.LanguageModel;
import World.Groups.Culture.Culture;
import World.Groups.Culture.CultureManager;
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
    final private Culture culture;
    //TODO: Groups need a notion of origin and current location

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
        final Pair<String[], Language> langResult = LanguageManager.initGroupLanguage(model, t, rand);
        this.name = langResult.getKey()[0];
        this.nameTranslation = langResult.getKey()[1];
        this.language = langResult.getValue();
        this.id = t.getLocation() + "." + population + "." + 0 + "." + name;
        //TODO: Placeholder for testing
        this.culture = CultureManager.createCulture(rand, name);
    }

    public String getId() { return id; }
    public int getPopulation() {return population;}
    public Language getLanguage() { return language; }
    public String getName() { return name; }
    public String getNameTranslation() { return nameTranslation; }
    public Culture getCulture() {return culture;}
}
