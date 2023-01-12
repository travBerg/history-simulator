package World.Groups.Culture.Religion;

import Logger.Logger;
import World.Groups.Culture.CultureManager;
import World.Groups.Culture.Religion.Categories.ReligionCategory;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class ReligionManager {
    private static final Logger LOG = Logger.getLogger(ReligionManager.class);

    /**create a starting religion
     * @param rand randomizer
     * @return a Religion
     */
    public static Religion createReligion(final Random rand, final String name){
        final Pair<ReligionCategory, ReligionCategory.Sub> categorySubPair = chooseCategory(rand, name);
        return new Religion(categorySubPair.getKey(), categorySubPair.getValue());
    }

    /**
     * Selects a category and sub-category for the religion
     * @param rand randomizer
     * @return a pair of (category,subcategory)
     */
    private static Pair<ReligionCategory, ReligionCategory.Sub> chooseCategory(final Random rand, final String name) {
        final int subChoice = rand.nextInt(ReligionCategory.Sub.values().length);
        final ReligionCategory.Sub sub = ReligionCategory.Sub.values()[subChoice];
        final Optional<ReligionCategory> category = Arrays.stream(ReligionCategory.values())
                .filter(r->Arrays.asList(r.getSubs()).contains(sub)).findFirst();
        LOG.debug("Religion created for group "+ name + " of type: \n"+ category.get()+"\n"+sub);
        return new Pair<>(category.orElseThrow(NullPointerException::new), sub);
    }
}
