package ViewGenerator;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class HexCell extends Application {


  /**
   * @param center represents the point at the center of the hexagon.
   * @param zoom represents how zoomed in the map is (just a side length for now)
   * @return array of doubles
   */

  private Double[] createPoints(int center, double zoom) {
    double verticalOffset = ((center / 10) * 100) + 20;
    double horizontalOffset = ((center % 10) * 100) + 20;
    double sideLength = zoom * 20.0;

    if((center / 10) % 2 == 1) {
      horizontalOffset += 20;
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



  @Override
  public void start(Stage stage) {
    //Creating a Polygon
    Polygon polygon0 = createHex(0, 1.0);
    Polygon polygon1 = createHex(1, 1.0);
    Polygon polygon2 = createHex(2, 1.0);
    Polygon polygon3 = createHex(3, 1.0);
    Polygon polygon4 = createHex(4, 1.0);
    Polygon polygon5 = createHex(5, 1.0);
    Polygon polygon6 = createHex(6, 1.0);
    Polygon polygon7 = createHex(7, 1.0);
    Polygon polygon8 = createHex(8, 1.0);
    Polygon polygon9 = createHex(9, 1.0);
    Polygon polygon10 = createHex(10, 1.0);
    Polygon polygon11 = createHex(11, 1.0);
    //Creating a Group object
    Group root = new Group();

    //adds second polygon to the view.
    root.getChildren().add(polygon0);
    root.getChildren().add(polygon1);
    root.getChildren().add(polygon2);
    root.getChildren().add(polygon3);
    root.getChildren().add(polygon4);
    root.getChildren().add(polygon5);
    root.getChildren().add(polygon6);
    root.getChildren().add(polygon7);
    root.getChildren().add(polygon8);
    root.getChildren().add(polygon9);
    root.getChildren().add(polygon10);
    root.getChildren().add(polygon11);

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