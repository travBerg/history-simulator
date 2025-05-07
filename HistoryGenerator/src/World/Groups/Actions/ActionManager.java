package World.Groups.Actions;

import Logger.Logger;
import World.Groups.Group;
import World.Groups.GroupManager;
import World.Territory.Territory;
import World.Territory.TerritoryManager;
import World.World;
import javafx.util.Pair;

import java.util.Optional;
import java.util.Random;

public class ActionManager {
    private static final Logger LOG = Logger.getLogger(ActionManager.class);

    /**
     * get the score for a given action for a given group in the context of the given world
     * @param a the action
     * @param world the world context
     * @param group the group deciding on an action
     * @return a pair of action to its integer score OR an empty optional if the action is not worth consideration
     */
    public static Optional<Pair<Action, Integer>> getScore(final Action a, final World world, final Group group) {
        switch (a) {
            case SETTLE:
                return scoreSettle(a, world, group);
            case WANDER:
                return scoreWander(a, world, group);
            default:
                return Optional.empty();
        }
    }

    public static Pair<Group, Optional<String>> runAction(Optional<Action> action, Group group, World world, Random r) {
        return action.map(a->{
            switch (a){
                case WANDER:
                    return new Pair<>(group, Optional.of("The " + group.getName()
                            + " group of people wanders in search of a home."));
                case SETTLE:
                    final Group edited = new Group(group, true);
                    return new Pair<>(edited, Optional.of("The " + group.getName() + " peoples have settled in the "
                            + world.getTerritoryMap().get(edited.getLocation()).getName() + " territory."));
                default:
                    return new Pair<Group, Optional<String>>(group, Optional.empty());
            }
        }).orElse(new Pair<>(group, Optional.empty()));
    }

    /**
     * Determine a score for the WANDER action
     * @param a the WANDER action
     * @param world world context
     * @param group the group deciding whether to WANDER
     * @return
     */
    private static Optional<Pair<Action, Integer>> scoreWander(final Action a, final World world, final Group group) {
        //RANGE: 10-60
        //If settled or terr is uninhabitable then just skip (for now, in the future maybe group has supplies)
        final Territory curLoc = world.getTerritoryMap().get(group.getLocation());
        if(group.isSettled()) {
            return Optional.empty();
        }
        //If not habitable give big bonus to wander
        final int habitable = (!TerritoryManager.habitable(curLoc)) ? 30 : 0;
        //Give a bonus if the amount of total food is less than twice the population
        final int foodMod = (TerritoryManager.getTotalEdibleFood(curLoc).orElse(0) < (2 * group.getPopulation()))
                ? 10 : 0;
        //Give a bonus if the amount of total drink is less than twice the population
        final int drinkMod = (TerritoryManager.getTotalPotableDrink(curLoc).orElse(0) < (2 * group.getPopulation()))
                ? 10 : 0;
        //Give a bonus if the group is smaller or equal to the combined strength of the other groups in the terr
        final int crowdedMod = (curLoc.getGroups().stream()
                .filter(g->!g.getId().equals(group.getId()))
                .map(Group::getPopulation)
                .reduce(Integer::sum)
                .orElse(0) >= group.getPopulation()) ? 10 : 0;
        //Combine score
        final int score = habitable + foodMod + drinkMod + crowdedMod;
        LOG.debug("Group " + group.getId() + " has given a score of " + score + " to the WANDER action");
        if(score == 0) { return Optional.empty(); }
        return Optional.of(new Pair<>(a, score));
    }

    /**
     * Determine a score for the SETTLE action
     * @param a the SETTLE action
     * @param world the world context
     * @param group the group that is deciding whether to SETTLE
     * @return
     */
    private static Optional<Pair<Action, Integer>> scoreSettle(final Action a, final World world, final Group group) {
        //RANGE: 10-30
        //If settled or terr is uninhabitable then just skip (for now, in the future maybe group has supplies)
        final Territory curLoc = world.getTerritoryMap().get(group.getLocation());
        if(group.isSettled() || !TerritoryManager.habitable(curLoc)) {
            return Optional.empty();
        }
        //Give a bonus if the amount of total food is greater than twice the population
        final int foodMod = (TerritoryManager.getTotalEdibleFood(curLoc).orElse(0) >= (2 * group.getPopulation()))
                ? 10 : 0;
        //Give a bonus if the amount of total drink is greater than twice the population
        final int drinkMod = (TerritoryManager.getTotalPotableDrink(curLoc).orElse(0) >= (2 * group.getPopulation()))
                ? 10 : 0;
        //Give a bonus if the group is larger than the combined strength of the other groups in the terr
        final int crowdedMod = (curLoc.getGroups().stream()
                .filter(g->!g.getId().equals(group.getId()))
                .map(Group::getPopulation)
                .reduce(Integer::sum)
                .orElse(0) < group.getPopulation()) ? 10 : 0;
        //Combine score
        final int score = foodMod + drinkMod + crowdedMod;
        LOG.debug("Group " + group.getId() + " has given a score of " + score + " to the SETTLE action");
        if(score == 0) { return Optional.empty(); }
        return Optional.of(new Pair<>(a, score));
    }
}
