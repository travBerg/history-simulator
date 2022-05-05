package WordGenerator;

public enum LanguageModelType {
    TEST("unigram_nouns_test.json", "bigram_nouns_test.json", "trigram_nouns_test.json");

    private final String uniNounPath;
    private final String biNounPath;
    private final String triNounPath;

    LanguageModelType(final String uniNounPath, final String biNounPath, final String triNounPath) {
        this.uniNounPath = uniNounPath;
        this.biNounPath = biNounPath;
        this.triNounPath = triNounPath;
    }

    public String getUniNounPath() { return uniNounPath; }
    public String getBiNounPath() { return biNounPath; }
    public String getTriNounPath() { return triNounPath; }
}
