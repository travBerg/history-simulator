package World;

import org.json.simple.JSONObject;
import java.util.ArrayList;

public interface IRegion {
    public int getIndex();
    public String getName();
    public String getType();
    public ArrayList<String> getLocations();
}
