package ViewGenerator;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;


public class HexCell extends Application {


  /**
   * @param center represents the point at the center of the hexagon.
   * @param zoom represents how zoomed in the map is (just a side length for now)
   * @return array of doubles
   */

  private Double[] createPoints(int center, double zoom) {
    double verticalOffset = ((center / 10) * 25) + 20;
    double horizontalOffset = ((center % 10) * 90) + 20;
    double sideLength = zoom * 30.0;

    if((center / 10) % 2 == 1) {
      horizontalOffset += 45;
    }

    double rad3 = Math.sqrt(3);
    Double[] points = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};

    //right middle point
    points[0] =  horizontalOffset  + sideLength;
    points[1] =  verticalOffset ;

    //top right point
    points[2] = horizontalOffset +  (sideLength / 2);
    points[3] = verticalOffset + (sideLength * rad3) / 2;

    //top left point
    points[4] =  horizontalOffset - (sideLength / 2);
    points[5] =  verticalOffset + ((sideLength * rad3) / 2);

    //left middle point
    points[6] =  horizontalOffset - sideLength;
    points[7] =  verticalOffset;


    //left bottom point
    points[8] =  horizontalOffset - (sideLength / 2);
    points[9] =  verticalOffset  - ((sideLength * rad3) / 2);

    //right bottom point
    points[10] = horizontalOffset + (sideLength / 2);
    points[11] = verticalOffset  - ((sideLength * rad3) / 2);

    return points;
    }

  private Polygon createHex(int center, double zoom) {
    Double[] points = createPoints(center, zoom);
    Polygon polygon = new Polygon();
    polygon.getPoints().addAll(points);
    return polygon;
  }

  private Group addHexes(Group root, int numHexes) {

    for(int i = 0; i < numHexes; i++) {
      Polygon cell = createHex(i, 1.0);
      if((i / 10) % 2 == 1) {
        cell.setFill(Color.BLUE);
      }
      else {
        cell.setFill(Color.RED);
      }
      cell.setStroke(Color.BLACK);
      root.getChildren().add(cell);
    }

    return root;
  }


  @Override
  public void start(Stage stage) {
    //Creating a Group object
    Group root = new Group();


    root = addHexes(root, 1000);

    //Creating a scene object
    Scene scene = new Scene(root, 1000, 1000);

    //Setting title to the Stage
    stage.setTitle("Drawing a Polygon");

    //Adding scene to the stage
    stage.setScene(scene);

    //Displaying the contents of the stage
    stage.show();
  }
  public static void main(String args[]){
    launch(args);
  }
}