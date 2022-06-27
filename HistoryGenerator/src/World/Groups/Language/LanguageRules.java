package World.Groups.Language;

import World.Groups.Language.RuleEnums.ComboNameFormat;

import java.util.Random;

public class LanguageRules {
    private final ComboNameFormat comboNameRule;

    public LanguageRules (final Random rand) {
        //Get random name rule
        this.comboNameRule = ComboNameFormat.values()[rand.nextInt(ComboNameFormat.values().length)];
    }

    public ComboNameFormat getComboNameRule() { return comboNameRule; }
}
