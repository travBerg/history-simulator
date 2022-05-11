package World.Groups.Language;

import World.Groups.Language.RuleEnums.GroupNameFormat;

import java.util.Random;

public class LanguageRules {
    private final GroupNameFormat gpNameRule;

    public LanguageRules (final Random rand) {
        //Get random name rule
        this.gpNameRule = GroupNameFormat.values()[rand.nextInt(GroupNameFormat.values().length)];
    }

    public GroupNameFormat getGpNameRule() { return gpNameRule; }
}
