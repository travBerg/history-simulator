package WordGenerator;

public enum LanguageModelType {
    TEST("unigram_nouns_test.json", "bigram_nouns_test.json", "trigram_nouns_test.json",
            new String[]{"a", "e", "i", "o", "u", "y"});

    private final String uniNounPath;
    private final String biNounPath;
    private final String triNounPath;
    private final String[] vowels;

    LanguageModelType(final String uniNounPath, final String biNounPath, final String triNounPath, final String[] vowels) {
        this.uniNounPath = uniNounPath;
        this.biNounPath = biNounPath;
        this.triNounPath = triNounPath;
        this.vowels = vowels;
    }

    public String getUniNounPath() { return uniNounPath; }
    public String getBiNounPath() { return biNounPath; }
    public String getTriNounPath() { return triNounPath; }
    public String[] getVowels() { return vowels; }
}
