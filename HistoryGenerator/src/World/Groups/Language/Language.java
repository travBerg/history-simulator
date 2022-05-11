package World.Groups.Language;

import WordGenerator.LanguageModel;

import java.util.Map;

public class Language {
    private final LanguageModel model;
    private final Map<String, String> lexicon;
    private final String name;

    public Language(final LanguageModel model, final String langName, final Map<String,String> lexicon) {
        this.model = model;
        this.lexicon = lexicon;
        this.name = langName;
    }
}
