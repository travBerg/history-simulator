package World.Groups.Culture.Ethics;

import World.Groups.Culture.Religion.Religion;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EthicsManager {
    public static Ethics createEthics(final Religion religion, final Random rand) {
        final List<EthicsType> choices;
        switch (religion.getSubcategory()) {
            case PANTHEISM:
            case HARMONY:
                choices = Arrays.stream(EthicsType.values())
                        .filter(v->v != EthicsType.MILITARISM)
                        .collect(Collectors.toList());
                break;
            default:
                choices = Arrays.asList(EthicsType.values());
                break;
        }
        final EthicsType type = choices.get(rand.nextInt(choices.size()));
        return new Ethics(type);
    }
}
