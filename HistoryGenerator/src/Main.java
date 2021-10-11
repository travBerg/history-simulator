import Controller.Controller;
import Controller.IController;
import View.DebugView.DebugView;
import View.IView;
import World.World;
import World.IWorld;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        //NOTE: Looks like size 6 (65x65, 4225 territories) is the best we get rn.
        //Otherwise it overflows while performing the biome search.
        //May be able to fix this by limiting max biome size
        //Without biome search, we can get to size 10 (over 1 mil territories) and maybe larger (more tiles than the largest world in Rimworld. Not bad.)

        //poles: 0 = north pole, 1 = south pole, 2 = both poles
        final IWorld world = new World(420, 8, 2, true); //seed 7, size 2, poles 2

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
            myWriter.write(world.asJSON().toJSONString());
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
