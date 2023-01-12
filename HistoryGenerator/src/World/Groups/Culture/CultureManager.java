package World.Groups.Culture;

import Logger.Logger;
import World.Groups.Culture.Ethics.Ethics;
import World.Groups.Culture.Ethics.EthicsManager;
import World.Groups.Culture.Religion.Religion;
import World.Groups.Culture.Religion.ReligionManager;
import World.Groups.Culture.Tradition.Tradition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CultureManager {
    private static final Logger LOG = Logger.getLogger(CultureManager.class);

    public static Culture createCulture(final Random rand, final String name) {
        //Set up Religion and Ethics
        final Religion religion = ReligionManager.createReligion(rand, name);
        final Ethics ethics = EthicsManager.createEthics(religion, rand);
        //TODO: Placeholder
        final List<Tradition> trads = new ArrayList<>();
        return new Culture(religion, ethics, trads);
    }
}
