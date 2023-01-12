package World.Groups.Culture;

import World.Groups.Culture.Ethics.Ethics;
import World.Groups.Culture.Religion.Religion;
import World.Groups.Culture.Tradition.Tradition;

import java.util.List;

public class Culture {
    private final Religion religion;
    private final Ethics ethics;
    private final List<Tradition> traditions;

    public Culture(final Religion rel, final Ethics eth, final List<Tradition> trad) {
        religion = rel;
        ethics = eth;
        traditions = trad;
    }

    public Religion getReligion() {
        return religion;
    }

    public Ethics getEthics() {
        return ethics;
    }
}
