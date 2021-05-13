import Controller.Controller;
import Controller.IController;
import View.DebugView.DebugView;
import View.IView;
import World.World;
import World.IWorld;
public class Main {

    public static void main(String[] args) {

        //Looks like size 6 (65x65, 4225 territories) is the best we get rn.
        //Otherwise it overflows while performing the biome search.
        //May be able to fix this by limiting max biome size
        final IWorld world = new World(760, 3, 2, true); //seed 7, size 2, poles 2
        System.out.println(world.asJSON());
        final IView view = new DebugView();
        final IController controller = new Controller(world, view);
        controller.go();
    }

}
