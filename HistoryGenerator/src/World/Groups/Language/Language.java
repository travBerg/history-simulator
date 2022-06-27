package World.Groups.Language;

import WordGenerator.LanguageModel;

import java.util.Map;

public class Language {
    private final LanguageModel model;
    //Map of english word to language translation
    private final Map<String, String> lexicon;
    private final String name;
    private final LanguageRules rules;

    //TODO: Once culture is implemented, add cultural terms
    public Language(final LanguageModel model, final String langName, final Map<String,String> lexicon,
                    final LanguageRules rules) {
        this.model = model;
        this.lexicon = lexicon;
        this.name = langName;
        this.rules = rules;
    }

    public LanguageRules getRules() { return rules; }
    public Map<String, String> getLexicon() { return lexicon; }
    public LanguageModel getModel() { return model; }
    public String getName() { return name; }
}
