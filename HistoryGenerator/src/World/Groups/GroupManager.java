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
        return groupObj;
    }

}
