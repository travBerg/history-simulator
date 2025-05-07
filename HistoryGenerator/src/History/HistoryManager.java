package History;

import Logger.Logger;
import WordGenerator.LanguageModel;
import World.Groups.Group;
import World.Groups.GroupManager;
import World.World;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class HistoryManager {
    public static final Logger LOG = Logger.getLogger(HistoryManager.class);
    private static final File HISTORY = new File("history");

    public static World generateHistory(final HashMap<String, Integer> settings, final HashMap<String, Double> resSettings,
                                        final HashMap<String, Float> groupMods, final Set<LanguageModel> languageModels) {
        //Removes old history directory and makes a new one
        Logger.deleteDir(HISTORY);
        System.out.println("Made a new dir: HISTORY");
        HISTORY.mkdirs();

        //Get years
        final int years = settings.get("years");
        System.out.println("Initializing World");
        //Initialize world at year 0
        final World world = new World(settings, resSettings, groupMods, languageModels);
        System.out.println("World Initialized");
        //TODO: Pass the random to world instead of making two
        final Random random = new Random(world.getSeed());
        System.out.println("Generating history");
        //This is the main loop for iterating through the years (for now)
        return Stream.iterate(new Pair<Integer, World>(1, world), i->i.getKey() < years + 1, i->{
            final int year = i.getKey();
            final World w = runYear(i.getValue(), year, random);
            //TODO: Placeholder
            return new Pair<Integer, World>(i.getKey() + 1, w);
        }).reduce((first, second)->second).orElse(null).getValue();
    }

    public static World runYear(final World world, final int year, final Random random) {
        System.out.println("Running year " + year);
        LOG.debug("Running year " + year);
        final JSONObject yearLog = new JSONObject();

        //Iterate through months, do the work, output to file and return resulting world
        final World yearEnd = Stream.iterate(new Pair<Month, World>(Month.getMonthByOrder(0), world),
                i->i.getKey().getOrder() + 1 < Month.values().length, i->{
            LOG.debug("Running year " + year + " month: " + i.getKey().name());
            final Pair<World, Map<Integer, String>> monthResult = HistoryManager.runMonth(world, random);
            final JSONObject monthLog = new JSONObject();
            monthLog.put("name", i.getKey().getName());
            //TODO: Log events
            yearLog.put(i.getKey().getOrder(), monthLog);
            return new Pair<Month, World>(Month.getMonthByOrder(i.getKey().getOrder() + 1), monthResult.getKey());
        }).reduce((first, second)->second).orElse(null).getValue();

        //Save this year to file
        HistoryManager.writeHistory(yearLog, year);
        //Return most recent world
        return yearEnd;
    }

    private static Pair<World, Map<Integer, String>> runMonth(final World world, final Random random) {
        final HashMap<String, Group> groups = world.getGroups();
        final HashMap<Integer, String> historyLog = new HashMap<>();
        final int counter = 0;
        //Set turn order
        final List<String> turnOrder = groups.keySet().stream().sorted().collect(Collectors.toList());
        Collections.shuffle(turnOrder, random);
        LOG.debug("Order: " + turnOrder);
        //Give each group a turn
        turnOrder.stream().forEach(i -> {
            //TODO: instead of this Pair make an object for containing all the altered info + who must respond if anyone
            final Pair<Group, Optional<String>> altered = GroupManager.runTurn(groups.get(i), world, random);
            altered.getValue().ifPresent(v->historyLog.put(counter, v));
            groups.put(i, altered.getKey());
        });
        //TODO: Placeholder
        return new Pair<World, Map<Integer, String>>(new World(world, groups), historyLog);
    }

    private static void writeHistory(final JSONObject history, final int year) {
        try {
            final FileWriter historyWriter = new FileWriter(HISTORY + "/" + year + "CE.json");
            historyWriter.write(history.toJSONString());
            historyWriter.close();
        } catch (IOException e) {
            System.out.println("COULD NOT WRITE TO HISTORY FILE");
            e.printStackTrace();
        }
    }
}
