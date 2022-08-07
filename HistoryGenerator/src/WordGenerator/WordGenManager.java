package WordGenerator;

import Logger.Logger;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WordGenManager {

    private final static Logger LOG = Logger.getLogger(WordGenManager.class);
    private final static String FILE_EXT = "HistoryGenerator/src/WordGenerator/Resources/";

    public static Set<LanguageModel> createLangModelSet() {
        final Set<LanguageModel> result = new HashSet<>();
        final LanguageModelType[] langTypes = LanguageModelType.values();
        Arrays.stream(langTypes).forEach(l->{
            result.add(initLangModel(l));
        });
        return result;
    }

    /**
     * Function to actually generate a word. DOES NOT add to lexicon
     * @param model language gram model to generate
     * @param seedWord english word this word will translate to
     * @param rand randomizer
     * @return
     */
    public static String genNoun(final LanguageModel model, final String seedWord, final Random rand) {
        LOG.debug("Generating word for: " + seedWord);
        final int len = seedWord.length();
        //TODO: Make all these helpers more generic so we can just pass the relevant fields
        return selectNoun(model, len, 0, rand);
    }

    /**
     * This first helper kicks off the word generating process and then validates the outputs from the next recursive
     * helper, only returning when a word is created that is proper length or we've recursed too many times
     * @param model
     * @param n
     * @param nCount
     * @param rand
     * @return
     */
    private static String selectNoun(final LanguageModel model, final int n, final int nCount, final Random rand) {
        //TODO: Placeholder, this should come from language model eventually
        final String[] vowels = model.getVowels();
        //Using an accumulator and while loop here; sorry functional programming gods
        final String wordRaw = genNounAttempt("$", model, rand);
        //clean off start and end characters
        final String word = wordRaw.substring(1, wordRaw.length() - 1);
        //if the candidate word is less than or equal to n letters long and contains at least one vowel OR we've
        // recursed more than 20 times
        if((word.length() <= n && Arrays.stream(vowels).anyMatch(word::contains)) || nCount > 20) {
            LOG.debug("Chose a new word in helper: " + word);
            return word;
        } else {
            LOG.debug("Rejected word: " + word);
            return selectNoun(model, n, nCount + 1, rand);
        }
    }

    /**
     * Creates a candidate word for jugement by selector function
     * @param word
     * @param model
     * @param rand
     * @return
     */
    private static String genNounAttempt(final String word, final LanguageModel model, final Random rand) {
        //LOG.debug("Word so far: " + word);
        //TODO: Maybe make this length bit an adjustable field and not just 12? Maybe a property of model?
        if(word.endsWith("%") || word.length() > 12) {
            return word;
        } else {
            final int p = word.length() - 1;
            //procedure for first character
            if(word.length() < 2) {
                final String prev = word.substring(p);
                if(model.getBiNouns().containsKey(prev)) {
                    final Gram bigram = model.getBiNouns().get(prev);
                    final String next = getNextChar(bigram, rand);
                    return genNounAttempt(word + next, model, rand);
                } else {
                    //LOG.debug("Something wacky is happening where starter character doesn't exist");
                    return word + "%";
                }
            //else if last two characters in trigrams
            } else if(model.getTriNouns()
                    .containsKey(word.substring(p - 1, p) + word.substring(p))) {
                final String prev = word.substring(p - 1, p) + word.substring(p);
                //LOG.debug("Prev for trigrams: " + prev);
                final Gram trigram = model.getTriNouns().get(prev);
                final String next = getNextChar(trigram, rand);
                return genNounAttempt(word + next, model, rand);
            //if not trigram but last character can make bigram
            } else if(model.getBiNouns().containsKey(word.substring(p))) {
                final Gram bigram = model.getBiNouns().get(word.substring(p));
                final String next = getNextChar(bigram, rand);
                return genNounAttempt(word + next, model, rand);
            //If all else fails, unigram
            } else {
                //LOG.debug("Using unigram");
                final String next = getNextChar(model.getUniNouns(), rand);
                return genNounAttempt(word + next, model, rand);
            }
        }
    }

    /**
     * Get next character randomly for a given gram
     * @param gram
     * @param rand
     * @return
     */
    private static String getNextChar(final Gram gram, final Random rand) {
        final int total = gram.getTotal();
        final int end = rand.nextInt(total);
        //This is kinda cheeky but Im using an accumulator here
        final int[] acc = new int[]{0};
        //next pair is whichever letter first has its count accumulate higher than end
        final Optional<Pair<String,Integer>> nextOp = gram.getNextLetters().stream()
                .map(pr->{
                    acc[0] = acc[0] + pr.getValue();
                    //LOG.debug("Letter: " + pr.getKey() + " Count: " + pr.getValue() + " Acc: " + acc[0] + " End: " + end);
                    return new Pair<>(pr.getKey(), acc[0]);
                }).filter(i->i.getValue() >= end).findFirst();
        return nextOp.map(Pair::getKey).orElseGet(()->{
            //LOG.debug("Wuh-oh, we didn't get a letter from the list. Aborting word");
            return "%";
        });
    }

    /**
     * Initiate language model from a LangModelType, including parsing grams from file
     * @param type
     * @return
     */
    private static LanguageModel initLangModel(final LanguageModelType type) {
        LOG.debug("Initiating language model: " + type);
        final Gram uniNounMap = createUniMap(FILE_EXT + type.getUniNounPath());
        final Map<String, Gram> biNounMap = createBiTrigramMap(FILE_EXT + type.getBiNounPath());
        final Map<String, Gram> triNounMap = createBiTrigramMap(FILE_EXT + type.getTriNounPath());
        final String[] vowels = type.getVowels();
        return new LanguageModel(type, uniNounMap, biNounMap, triNounMap, vowels);
    }

    private static Map<String, Gram> createBiTrigramMap(final String filepath) {
        final JSONParser parser = new JSONParser();
        final HashMap<String, Gram> resultMap = new HashMap<>();
        final List<String> debugOut = new ArrayList<>();
        try (FileReader reader = new FileReader(filepath)) {
            final Object obj = parser.parse(reader);
            final JSONObject allGramJson = (JSONObject) obj;

            allGramJson.keySet().forEach(k->{
                final JSONObject gramJson = (JSONObject) allGramJson.get(k);
                final int total = Integer.parseInt(gramJson.get("total").toString());
                final JSONArray listJson = (JSONArray) gramJson.get("list");
                final List<Pair<String, Integer>> list = (List<Pair<String, Integer>>) listJson.stream().map(x->{
                    //x is a {"letter":"a", "count":100}
                    final JSONObject letterObj = (JSONObject) x;
                    final String letter = letterObj.get("letter").toString();
                    final int count = Integer.parseInt(letterObj.get("count").toString());
                    return new Pair<String, Integer>(letter, count);
                }).collect(Collectors.toList());
                final Gram gram = new Gram(total, list);
                debugOut.add("\nletter: " + k.toString() + "\n" + "total: " + total + "\n" + "list: " + list);
                resultMap.put(k.toString(), gram);
            });

        } catch (FileNotFoundException e) {
            LOG.debug("Failed to find file: " + filepath);
        } catch (IOException e) {
            LOG.debug("IO Exception found: " + e.getMessage());
        } catch (ParseException e) {
            LOG.debug("Parse Exception: failed to parse json from file " + filepath + "\n" +
                    e.getMessage());
        }
        LOG.debug("Final map for " + filepath + "\n" + debugOut);
        return resultMap;
    }

    private static Gram createUniMap(final String filepath) {
        final JSONParser parser = new JSONParser();
        final List<String> debugOut = new ArrayList<>();
        final List<Pair<String, Integer>> list;
        final int total;
        try (FileReader reader = new FileReader(filepath)){
            final Object obj = parser.parse(reader);
            final JSONObject uniJson = (JSONObject) obj;
            total = Integer.parseInt(uniJson.get("total").toString());
            debugOut.add("total: " + total);
            list = (List<Pair<String, Integer>>) uniJson.keySet().stream()
                    .filter(k->k.toString() != "total").map(k->{
                final Integer val = Integer.parseInt(uniJson.get(k).toString());
                debugOut.add("\nletter: " + k.toString() + "\ncount: " + val);
                return new Pair<String, Integer>(k.toString(), val);
            }).collect(Collectors.toList());
            LOG.debug("Final uni map: " + debugOut);
            return new Gram(total, list);
        } catch (FileNotFoundException e) {
            LOG.debug("Failed to find file: " + filepath);
            return new Gram(0, new ArrayList<>());
        } catch (IOException e) {
            LOG.debug("IO Exception found: " + e.getMessage());
            return new Gram(0, new ArrayList<>());
        } catch (ParseException e) {
            LOG.debug("Parse Exception: failed to parse json from file " + filepath + "\n" +
                    e.getMessage());
            return new Gram(0, new ArrayList<>());
        }
    }
}
