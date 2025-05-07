package World.Groups;

import Logger.Logger;
import WordGenerator.LanguageModel;
import WordGenerator.WordGenManager;
import World.Groups.Actions.Action;
import World.Groups.Actions.ActionManager;
import World.Groups.Language.Language;
import World.Territory.Territory;
import World.World;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupManager {
    private static final Logger LOG = Logger.getLogger(GroupManager.class);

    public static JSONObject asJSON(final Group group) {
        final JSONObject groupObj = new JSONObject();
        groupObj.put("name", group.getName());
        groupObj.put("nameMeaning", group.getNameTranslation());
        groupObj.put("id", group.getId());
        groupObj.put("pop", group.getPopulation());
        final JSONObject cultureObj = new JSONObject();
        cultureObj.put("ethics", group.getCulture().getEthics().getType().toString());
        cultureObj.put("religionType", group.getCulture().getReligion().getCategory().toString());
        cultureObj.put("religionSubtype", group.getCulture().getReligion().getSubcategory().toString());
        groupObj.put("culture", cultureObj);
        return groupObj;
    }

    public static Pair<Group, Optional<String>> runTurn(final Group group, final World world, final Random r) {
        final Optional<String> historyEvent;
        //TODO: So far all groups only have 2 actions: settle or wander
        //First determine the move (if indeed there is a move at all)
        final Optional<Action> action = getAction(group, world, r);
        //Then make the group's move and convert to historical event if necessary
        //TODO: need to return some object that not only covers all possible changes to Groups and Terrs,
        // but also has an optional String. So signature of this method will likely change
        final Pair<Group, Optional<String>> result = ActionManager.runAction(action, group, world, r);
        return result;
    }

    private static Optional<Action> getAction(final Group group, final World world, final Random r) {
        //Generate a score for each action
        //TODO: Filter for only actions that apply to the group. Or store a list in each group/group type?
        final List<Pair<Action, Integer>> scores = Arrays.stream(Action.values())
                .map(a->ActionManager.getScore(a, world, group))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        //LOG.debug("Group " + group.getId() + " scores: " + scores);
        if(scores.isEmpty()) { return Optional.empty(); }
        final Action choice;
        if(scores.size() == 1) {
            choice = scores.get(0).getKey();
        } else {
            //Randomize the result based off the scores (spin the wheel)
            final int total = scores.stream().map(Pair::getValue).reduce(Integer::sum).orElse(0);
            final int spin = r.nextInt(total) + 1;
            //LOG.debug("Group total: " + total + " and spin: " + spin);
            //Spin the wheel
            choice = scores.get(Stream.iterate(new Pair<>(0, 0),
                    n->n.getValue() < spin,
                    n->new Pair<>(n.getKey() + 1, n.getValue() + scores.get(n.getKey()).getValue()))
                    .reduce((first, second)->second).orElse(new Pair<>(0,0)).getKey()).getKey();
        }
        //LOG.debug("Group " + group.getId() + " has chosen the " + choice.toString() + " action");
        //Return chosen action
        return Optional.of(choice);
    }
}
