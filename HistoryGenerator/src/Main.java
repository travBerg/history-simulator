import Controller.Controller;
import Controller.IController;
import View.DebugView.DebugView;
import View.IView;
import World.World;
import World.IWorld;
public class Main {

    public static void main(String[] args) {

        //NOTE: Looks like size 6 (65x65, 4225 territories) is the best we get rn.
        //Otherwise it overflows while performing the biome search.
        //May be able to fix this by limiting max biome size
        //Without biome search, we can get to size 10 (over 1 mil territories) and maybe larger (more tiles than the largest world in Rimworld. Not bad.)

        //poles: 0 = north pole, 1 = south pole, 2 = both poles
        final IWorld world = new World(844, 6, 0, true); //seed 7, size 3, poles 0
        final IView view = new DebugView();
        final IController controller = new Controller(world, view);
        controller.go();
    }

}
