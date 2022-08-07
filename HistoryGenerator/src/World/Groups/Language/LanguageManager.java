package World.Groups.Language;

import Logger.Logger;
import WordGenerator.LanguageModel;
import WordGenerator.WordGenManager;
import World.Groups.Language.RuleEnums.ComboNameFormat;
import World.Territory.Biome.Biome;
import World.Territory.Territory;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LanguageManager {
    private static final Logger LOG = Logger.getLogger(LanguageManager.class);
    private static final String[] PEOPLE_TERMS = new String[]{"people", "folk", "kind", "tribe", "clan"};

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
        final String groupName = formatComboName(peopleWord, biomeWord, rules.getComboNameRule(), model);
        LOG.debug("Group name: " + groupName + ", means " + englishName);

        //TODO: Generate a language name instead of just reusing group name
        return new Pair<>(new String[]{groupName, englishName}, new Language(model, groupName, lexicon, rules));
    }

    /**
     * Helper method to generate the mod half of a POI name and combine it into full name
     * @param biome
     * @param lang
     * @param rand
     * @param pOIWord
     * @param pOIWordMeaning
     * @return Pair of mod, mod meaning
     */
    private static Pair<String,String> genPOIModAndName(final Biome biome, final Language lang,
                                                                       final Random rand, final String pOIWord,
                                                                       final String pOIWordMeaning) {
        //TODO: Affected by cultural terms, resources, poi, characters, biome adj
        //eng trans mod
        final String modMeaning;
        //mod in language
        final String mod;
        //territory name (in language)
        final String pOIName;
        final int roll = rand.nextInt(6);
        switch(roll) {
            case 0:
                //Biome adjective
                //TODO: Implement adjectives in language model
            case 1:
                //Combined biome terms
                final String s1 = biome.getTerms()[rand.nextInt(biome.getTerms().length)];
                final String s2 = biome.getTerms()[rand.nextInt(biome.getTerms().length)];
                modMeaning = s1 + " " + s2;
                final String s1t = translateToLangNoun(lang, s1, rand);
                final String s2t = translateToLangNoun(lang, s2, rand);
                mod = formatComboName(s2t, s1t, lang.getRules().getComboNameRule(),lang.getModel());
                pOIName = mod + " " + pOIWord;
                break;
            case 2:
                //Resource
            case 3:
                //Character name
            case 4:
                //Cultural term
            default:
                //Random adjective
                //TODO: Placeholder - read from bigger list of adjectives in future
                final String[] adjs = new String[]{"whistling", "cursed", "red", "black", "shattered", "beautiful",
                        "motherly", "peaceful", "thundering", "blue", "hazy", "wicked", "wild", "dark", "misty", "light",
                        "huge", "dangerous", "wise", "golden", "whispering", "ivory", "mellow", "elegant", "white", "heavenly"};
                modMeaning = adjs[rand.nextInt(adjs.length)];
                mod = translateToLangNoun(lang, modMeaning, rand);
                pOIName = formatComboName(pOIWord, mod, lang.getRules().getComboNameRule(),lang.getModel());
                break;
        }
        //TODO: Implement different formats (ex: pOIWord of mod, mod's pOIWord) based off lang model
        final String engTer = modMeaning + " " + pOIWordMeaning;
        return new Pair<>(pOIName, engTer);
    }

    /**
     * Method to generate a territory name based off biome and language
     * @param biome The biome of this terr
     * @param lang The language used
     * @return Pair<terr name, eng translated name>
     */
    public static Pair<String, String> genTerName(final Biome biome, final Language lang, final Random rand) {
        final String subBiome = biome.getSubBiomes()[rand.nextInt(biome.getSubBiomes().length)].toLowerCase();
        final String subBiomeTrans = translateToLangNoun(lang, subBiome, rand);
        final Pair<String, String> name = genPOIModAndName(biome, lang, rand, subBiomeTrans, subBiome);
        LOG.debug("Generating new territory name: " + name.getKey() + ". Translation: " + name.getValue() +
                ". Created in the " + lang.getName() + " language");
        return name;
    }

    /**
     * Generates a name for a river + translation. Either named after terr or named using terr rules
     * @param biome biome of this river seg
     * @param lang discoverer language
     * @param rand random
     * @param terName territory name
     * @param terNameMeaning territory name meaning
     * @return Pair of (river name, eng translated river name)
     */
    public static Pair<String, String> genRiverName(final Biome biome, final Language lang, final Random rand,
                                                    final String terName, final String terNameMeaning) {
        final int coinFlip = rand.nextInt(2);
        //Pair<river name, eng translated river name>
        final Pair<String, String> names;
        final String rivWord = translateToLangNoun(lang, "river", rand);
        switch (coinFlip) {
            case 0:
                //Name after territory
                names = new Pair<>(terName + " " + rivWord, terNameMeaning + " " + "river");
                break;
            case 1:
                names = genPOIModAndName(biome, lang, rand, rivWord, "river");
                break;
            default:
                LOG.debug("COINFLIP FAILED!");
                names = new Pair<>(terName + " " + rivWord, terNameMeaning + " " + "river");
                break;
        }
        LOG.debug("Generating new river name: " + names.getKey() + ". Translation: " + names.getValue() +
                ". Created in the " + lang.getName() + " language");
        return names;
    }

    public static Pair<String, String> genLakeName(final Biome biome, final Language lang, final Random rand,
                                                   final String terName, final String terNameMeaning) {
        final int coinFlip = rand.nextInt(2);
        //Pair<river name, eng translated river name>
        final Pair<String, String> names;
        final String lakeWord = translateToLangNoun(lang, "lake", rand);
        switch (coinFlip) {
            case 0:
                //Name after territory
                names = new Pair<>(terName + " " + lakeWord, terNameMeaning + " " + "lake");
                break;
            case 1:
                names = genPOIModAndName(biome, lang, rand, lakeWord, "lake");
                break;
            default:
                LOG.debug("COINFLIP FAILED!");
                names = new Pair<>(terName + " " + lakeWord, terNameMeaning + " " + "lake");
                break;
        }
        LOG.debug("Generating new lake name: " + names.getKey() + ". Translation: " + names.getValue() +
                ". Created in the " + lang.getName() + " language");
        return names;
    }

    /**
     * Returns a readable english "seed" term for groups referring to themselves
     * @param rand randomizer
     * @return a term for "people"
     */
    public static String readablePeopleTerm(final Random rand) {
        return PEOPLE_TERMS[rand.nextInt(PEOPLE_TERMS.length)];
    }

    public static String formatComboName(final String term, final String mod, final ComboNameFormat rule,
                                         final LanguageModel model) {
        LOG.debug("Term: " + term + " Mod: " + mod + " Rule " + rule);
        switch (rule) {
            case CONCAT:
                return cleanCombine(mod, term);
            case MOD_PREFIX:
                return cleanCombine(parsePrefix(mod, model.getVowels()), term);
            case TERM_PREFIX:
                return cleanCombine(parsePrefix(term, model.getVowels()), mod);
            case SPEC_CHAR:
                return mod + model.getSpecChar() + term;
            default:
                return cleanCombine(mod, term);
        }

    }

    /**
     * Method to automatically combine two words and remove duplicate characters
     * @param s1 leading word
     * @param s2 trailing word
     * @return combined word
     */
    private static String cleanCombine(final String s1, final String s2) {
        if(s1.endsWith(s2.substring(0,1))) {
            return s1 + s2.substring(1);
        } else {
            return s1 + s2;
        }
    }

    /**
     * Returns this word's translation and adds to lexicon if generated
     * @param lang
     * @param word
     * @param rand
     * @return
     */
    private static String translateToLangNoun(final Language lang, final String word, final Random rand) {
        //TODO: Make generic for different parts of speech
        final Map<String, String> lex = lang.getLexicon();
        if(lex.containsKey(word)) {
            return lex.get(word);
        } else {
            final String nu = WordGenManager.genNoun(lang.getModel(), word, rand);
            lang.getLexicon().put(word, nu);
            LOG.debug("Created new word for " + word + ": " + nu + ". In lexicon of language: " + lang.getName());
            return nu;
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
        //Get all indexes where there are vowels
        final List<Integer> idxs = intStream.filter(i->{
            String c = "" + word.toLowerCase().charAt(i);
            return Arrays.stream(vowels).anyMatch(c::contains);
        }).boxed().collect(Collectors.toList());
        //Get index of the first vowel unless word starts on a vowel but contains multiple vowels, then go to second one
        final int idx;
        if(idxs.size() > 1 && (idxs.get(0) == 0)) {
            idx = idxs.get(1);
        } else {
            idx = idxs.stream().findFirst().orElse(0);
        }
        final String prefix = word.substring(0,idx + 1);
        LOG.debug("Prefix: " + prefix);
        return prefix;
    }
}
