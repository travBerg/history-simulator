package World.Groups.Language;

import Logger.Logger;
import WordGenerator.LanguageModel;
import WordGenerator.WordGenManager;
import World.Groups.GroupManager;
import World.Groups.Language.RuleEnums.GroupNameFormat;
import World.Territory.Territory;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class LanguageManager {
    private static final Logger LOG = Logger.getLogger(LanguageManager.class);
    private static final String[] PEOPLE_TERMS = new String[]{"people", "folk", "kind", "tribe", "clan"};

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
     * initializes the language of a group, then the name of group and its english translation
     * @param model
     * @param terr
     * @return String[name, english translation], Language
     */
    public static Pair<String[], Language> initGroupLanguage(final LanguageModel model, final Territory terr,
                                                                       final Random rand) {
        final Map<String,String> lexicon = new HashMap<>();

        //TODO: This is a placeholder pending Ava's research. Just gets a biome-related word
        //Get the english seed terms (descriptor and people)
        final String[] terms = terr.getBiome().getTerms();
        final String engBiomeString = terms[rand.nextInt(terms.length)].toLowerCase();
        final String engPeopleString = readablePeopleTerm(rand);

        //Generate the rules for this language
        final LanguageRules rules = new LanguageRules(rand);

        final String englishName = engBiomeString + " " + engPeopleString;

        //Generate the words
        final String biomeWord = WordGenManager.genNoun(model, engBiomeString, rand);
        lexicon.put(engBiomeString, biomeWord);
        LOG.debug("The biome word " + biomeWord + ", means " + engBiomeString);
        final String peopleWord = WordGenManager.genNoun(model, engPeopleString, rand);
        lexicon.put(engPeopleString, peopleWord);

        //Format the group name
        final String groupName = formatGroupName(peopleWord, biomeWord, rules.getGpNameRule(), model);
        LOG.debug("Group name: " + groupName + ", means " + englishName);

        //TODO: Generate a language name instead of just reusing group name
        return new Pair<>(new String[]{groupName, englishName}, new Language(model, groupName, lexicon));
    }

    /**
     * Returns a readable english "seed" term for groups referring to themselves
     * @param rand randomizer
     * @return a term for "people"
     */
    public static String readablePeopleTerm(final Random rand) {
        return PEOPLE_TERMS[rand.nextInt(PEOPLE_TERMS.length)];
    }

    public static String formatGroupName(final String people, final String mod, final GroupNameFormat rule,
                                         final LanguageModel model) {
        LOG.debug("People: " + people + " Mod: " + mod + " Rule " + rule);
        switch (rule) {
            case CONCAT:
                return mod + people;
            case MOD_PREFIX:
                //TODO: maybe join on a vowel instead of just grafting prefix to full word?
                return parsePrefix(mod, model.getVowels()) + people;
            case PEOPLE_PREFIX:
                return parsePrefix(people, model.getVowels()) + mod;
            case SPEC_CHAR:
                return mod + model.getSpecChar() + people;
            default:
                return mod + people;
        }

    }

    /**
     * Parse a prefix (the word up to and including its first vowel)
     * @param word the word to be prefix'd
     * @param vowels This language's vowel set
     * @return
     */
    private static String parsePrefix(final String word, final String[] vowels) {
        //Make list of ints 0 - mod word length
        final IntStream intStream = IntStream.range(0, word.length());
        final int idx = intStream.filter(i->{
            String c = "" + word.toLowerCase().charAt(i);
            return Arrays.stream(vowels).anyMatch(c::contains);
        }).findFirst().orElse(1);
        final String prefix = word.substring(0,idx + 1);
        LOG.debug("Prefix: " + prefix);
        return prefix;
    }
}
