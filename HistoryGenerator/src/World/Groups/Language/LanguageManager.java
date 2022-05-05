package World.Groups.Language;

import Logger.Logger;
import WordGenerator.LanguageModel;
import WordGenerator.WordGenManager;
import World.Groups.GroupManager;
import World.Territory.Territory;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LanguageManager {
    private static final Logger LOG = Logger.getLogger(LanguageManager.class);

    /**
     * Method to initialize a Language
     * @param model
     * @return
     */
    public static Language createLanguage(final LanguageModel model, final String langName,
                                          final Map<String,String> lexicon) {
        return new Language(model, langName, lexicon);
    }

    /**
     * initializes the name of group, its english translation, the name of the language, and the beginning of the
     * lexicon with related words
     * @param model
     * @param terr
     * @return String[name, english translation, language name], lexicon
     */
    public static Pair<String[], Map<String,String>> initTrueGroupName(final LanguageModel model, final Territory terr,
                                                                       final Random rand) {
        final Map<String,String> lexicon = new HashMap<>();
        //TODO: This is a placeholder pending Ava's research.
        // Also we maybe want to create language as part of this instead of returning name and lexicon?
        final String[] terms = terr.getBiome().getTerms();
        final String engBiomeString = terms[rand.nextInt(terms.length)].toLowerCase();
        //TODO: For now, always use the word "people" for groups and format is always biome-people
        final String engPeopleString = "people";
        final String englishName = engBiomeString + " " + engPeopleString;
        //TODO: Use language rule to combine parts of name - for now we just graft them together
        final String biomeWord = WordGenManager.genNoun(model, engBiomeString, rand);
        lexicon.put(engBiomeString, biomeWord);
        LOG.debug("The biome word " + biomeWord + ", means " + engBiomeString);
        final String peopleWord = WordGenManager.genNoun(model, engPeopleString, rand);
        lexicon.put(engPeopleString, peopleWord);
        final String groupName = formatGroupName(model, peopleWord, biomeWord);
        return new Pair<>(new String[]{groupName, englishName, groupName}, lexicon);
    }

    public static String formatGroupName(final LanguageModel model, final String people, final String mod) {
        //TODO: Have the model actually effect this
        return mod + people;
    }
}
