package World.Groups;

import org.json.simple.JSONObject;

public class GroupManager {
    public static JSONObject asJSON(final Group group) {
        final JSONObject groupObj = new JSONObject();
        groupObj.put("id", group.getId());
        groupObj.put("pop", group.getPopulation());
        return groupObj;
    }
}
