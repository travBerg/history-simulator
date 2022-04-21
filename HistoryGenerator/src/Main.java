import Controller.Controller;
import Controller.IController;
import Logger.Logger;
import View.DebugView.DebugView;
import View.IView;
import World.World;
import World.IWorld;
import World.WorldManager;
import World.SettingsManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Logger.clear(true, true);
        //Get resource modifiers
        final HashMap<String, Double> resSettings = SettingsManager.getResourceMods();
        final HashMap<String, Float> groupMods = SettingsManager.getGroupMods();

        //We can get to size 10 (over 1 mil territories, at least ~30,000 groups) but long render time and the json write fails
        // (more tiles than the largest world in Rimworld. Not bad.)
        final HashMap<String, Integer> settings = SettingsManager.getSettings(420, 5, 2, true);

        //poles: 0 = north pole, 1 = south pole, 2 = both poles
        final IWorld world = new World(settings, resSettings, groupMods); //seed 7, size 2, poles 2

        //Create json file
        try {
            File myObj = new File("test_world.json");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("test_world.json");
            myWriter.write(WorldManager.asJSON(world).toJSONString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        
        final IView view = new DebugView();
        final IController controller = new Controller(world, view);
        controller.go();
    }

}
