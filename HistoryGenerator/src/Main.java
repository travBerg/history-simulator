import Controller.Controller;
import Controller.IController;
import View.DebugView.DebugView;
import View.IView;
import World.World;
import World.IWorld;
public class Main {

    public static void main(String[] args) {

        //IGenerator generator = new TerrainGen(7, 690);
        //System.out.println(generator.render());
        final IWorld world = new World(7, 2, true);
        final IView view = new DebugView();
        final IController controller = new Controller(world, view);
        controller.go();
    }

}
