package WordGenerator;

import java.util.Map;

public class LanguageModel {
    private final Gram uniNouns;
    private final Map<String, Gram> biNouns;
    private final Map<String, Gram> triNouns;
    private final LanguageModelType type;
    private final String[] vowels;
    private final String specChar;
    public LanguageModel(final LanguageModelType type, final Gram uniNouns,
                         final Map<String,Gram> biNouns, final Map<String, Gram> triNouns, final String[] vowels){
        this.type = type;
        this.uniNouns = uniNouns;
        this.biNouns = biNouns;
        this.triNouns = triNouns;
        this.vowels = vowels;
        //TODO: Make a method for choosing a spec char based off what is most common in unigrams ( ' , " " , - )
        this.specChar = " ";
    }

    public Map<String, Gram> getBiNouns() { return biNouns; }
    public Map<String, Gram> getTriNouns() { return triNouns; }
    public Gram getUniNouns() { return uniNouns; }
    public LanguageModelType getType() { return type; }
    public String[] getVowels() { return vowels; }
    public String getSpecChar() { return specChar; }
}
