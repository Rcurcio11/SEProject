
package seproject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 *
 * @author Group14
 */
public class FXMLGuiDocumentController implements Initializable {

    ///////////////// USER VARIABLES /////////////////
    
    private ShapeModel selectedShape;
    private OperationExecutor commandInvoker = new OperationExecutor();
    private Point2D startPoint;
    private Point2D endPoint;
    
    //////////////////////////////////////////////////
    
    @FXML
    private AnchorPane drawingArea;
    @FXML
    private HBox toolBox;
    @FXML
    private Button rettangleButton;
    @FXML
    private Button ellipseButton;
    @FXML
    private Button lineButton;
    @FXML
    private VBox menuVBox;
    @FXML
    private Menu fileMenu;
    @FXML
    private MenuItem saveMenu;
    @FXML
    private MenuItem loadMenu;
    @FXML
    private ColorPicker outlineColor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
    }

    @FXML
    private void handleMouseReleasedOnDrawingArea(MouseEvent event) {
        endPoint = new Point2D(event.getX(),event.getY());
        InsertCommand command = new InsertCommand(drawingArea, selectedShape, startPoint, endPoint);
        commandInvoker.execute(command);
        selectedShape = selectedShape.nextDraw();
        
    }
    @FXML
    private void handleMousePressedOnDrawingArea(MouseEvent event) {
        startPoint = new Point2D(event.getX(),event.getY());
    }


    @FXML
    private void handleButtonActionRectangle(ActionEvent event) {
        selectedShape = new RectangleModel();
    }

    @FXML
    private void handleButtonActionEllipse(ActionEvent event) {
        selectedShape = new EllipseModel();
    }

    @FXML
    private void handleButtonActionLine(ActionEvent event) {
        selectedShape = new LineModel();
        
    }

    @FXML
    private void handleActionSaveDrawing(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(null);
        if(selectedFile != null){
            try{
                commandInvoker.execute(new SaveDrawingOnFileCommand(drawingArea,selectedFile.getAbsolutePath()));
            }catch(FileErrorDrawException ex){
            }
        }
    }

    @FXML
    private void handleActionLoadDrawing(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null){
            try{
                commandInvoker.execute(new LoadDrawingFromFileCommand(drawingArea,selectedFile.getAbsolutePath()));
            }catch(FileErrorDrawException ex){
            }
        }
    }

    @FXML
    private void handleActionChangeColor(ActionEvent event) {
        selectedShape.setColor(outlineColor.getValue(), Color.TRANSPARENT);
    }

    

    
}
