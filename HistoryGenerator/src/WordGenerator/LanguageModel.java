package WordGenerator;

import java.util.Map;

public class LanguageModel {
    private final Gram uniNouns;
    private final Map<String, Gram> biNouns;
    private final Map<String, Gram> triNouns;
    private final LanguageModelType type;
    public LanguageModel(final LanguageModelType type, final Gram uniNouns,
                         final Map<String,Gram> biNouns, final Map<String, Gram> triNouns){
        this.type = type;
        this.uniNouns = uniNouns;
        this.biNouns = biNouns;
        this.triNouns = triNouns;

    }

    public Map<String, Gram> getBiNouns() { return biNouns; }
    public Map<String, Gram> getTriNouns() { return triNouns; }
    public Gram getUniNouns() { return uniNouns; }
    public LanguageModelType getType() { return type; }
}
