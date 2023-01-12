package World.Groups;

import Logger.Logger;
import WordGenerator.LanguageModel;
import WordGenerator.WordGenManager;
import World.Groups.Language.Language;
import World.Territory.Territory;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GroupManager {
    private static final Logger LOG = Logger.getLogger(GroupManager.class);

    public static JSONObject asJSON(final Group group) {
        final JSONObject groupObj = new JSONObject();
        groupObj.put("name", group.getName());
        groupObj.put("nameMeaning", group.getNameTranslation());
        groupObj.put("id", group.getId());
        groupObj.put("pop", group.getPopulation());
        final JSONObject cultureObj = new JSONObject();
        cultureObj.put("ethics", group.getCulture().getEthics().getType());
        cultureObj.put("religionType", group.getCulture().getReligion().getCategory());
        cultureObj.put("religionSubtype", group.getCulture().getReligion().getSubcategory());
        groupObj.put("culture", cultureObj);
        return groupObj;
    }

}
