
package seproject;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author Group14
 */
public class FXMLGuiDocumentController implements Initializable {

    ///////////////// USER VARIABLES /////////////////
    
    private ShapeModel shapeToInsert;
    private OperationExecutor commandInvoker = new OperationExecutor();
    private Point2D startPoint;
    private Point2D endPoint;
    private FileChooser fc;
    private ShapeModel selectedShape;
    private BooleanProperty shapeIsSelected;
    
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
    @FXML
    private ColorPicker fillingColor;
    @FXML
    private Label statusLabel;
    @FXML
    private CheckBox selectShapeCheckBox;
    @FXML
    private HBox editBox;
    @FXML
    private Button deleteButton;
    @FXML
    private Menu undoMenu;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
        statusLabel.setText("Welcome");
        selectedShape = null;
        shapeIsSelected = new SimpleBooleanProperty(false);
        
        //  BINDINGS
        editBox.disableProperty().bind(shapeIsSelected.not());
        editBox.visibleProperty().bind(selectShapeCheckBox.selectedProperty());
    }

    @FXML
    private void handleMouseReleasedOnDrawingArea(MouseEvent event) {
        endPoint = new Point2D(event.getX(),event.getY());
        try{
            InsertCommand command = new InsertCommand(drawingArea, shapeToInsert,startPoint, endPoint, outlineColor.getValue(), fillingColor.getValue());
            commandInvoker.execute(command);
            shapeToInsert = shapeToInsert.nextDraw();
        }catch(ShapeNotSelectedDrawException ex){
            //manage exception message
        }
        
    }
    @FXML
    private void handleMousePressedOnDrawingArea(MouseEvent event) {
        startPoint = new Point2D(event.getX(),event.getY());
    }


    @FXML
    private void handleButtonActionRectangle(ActionEvent event) {
        shapeToInsert = new RectangleModel();
        statusLabel.setText("Rectangle");
        selectShapeCheckBox.setSelected(false);
    }

    @FXML
    private void handleButtonActionEllipse(ActionEvent event) {
        shapeToInsert = new EllipseModel();
        statusLabel.setText("Ellipse");
        selectShapeCheckBox.setSelected(false);
    }

    @FXML
    private void handleButtonActionLine(ActionEvent event) {
        shapeToInsert = new LineModel();
        statusLabel.setText("Line");
        selectShapeCheckBox.setSelected(false);        
    }

    @FXML
    private void handleActionSaveDrawing(ActionEvent event) {
        statusLabel.setText("");
        fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(null);
        if(selectedFile != null){
            try{
                commandInvoker.execute(new SaveDrawingOnFileCommand(drawingArea,selectedFile.getAbsolutePath()));
            }catch(FileErrorDrawException ex){
                statusLabel.setText("File error, try again");
            }
        }
    }

    @FXML
    private void handleActionLoadDrawing(ActionEvent event) {
        statusLabel.setText("");
        fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null){
            try{
                commandInvoker.execute(new LoadDrawingFromFileCommand(drawingArea,selectedFile.getAbsolutePath()));
            }catch(FileErrorDrawException | ShapeModelNotSupportedDrawException ex){
                statusLabel.setText("File not supported");
            }
        }
    }    

    @FXML
    private void handleClickedToolBox(MouseEvent event) {
        statusLabel.setText("");
        shapeToInsert = null;
    }


    @FXML
    private void handleSelectCheckBox(ActionEvent event) {
        if(selectShapeCheckBox.isSelected())
            statusLabel.setText("Select a shape");
        else
            statusLabel.setText("");
        selectedShape = null;
        shapeIsSelected.setValue(false);
        shapeToInsert = null;
    }

    @FXML
    private void handleMouseClickeOnDrawingArea(MouseEvent event) {
        if(selectShapeCheckBox.isSelected()){
            Point2D selectPoint = new Point2D(event.getX(),event.getY());
            selectedShape = selectShape(selectPoint);
        }
    }
    
    private ShapeModel selectShape(Point2D selectPoint){
        Node actualNode = null;
        
        for(int i = drawingArea.getChildren().size()-1; i>=0; i--){
            actualNode = drawingArea.getChildren().get(i);
            if(actualNode.contains(selectPoint)){
               shapeIsSelected.setValue(true);
               return (ShapeModel) actualNode;
            }
        }
        shapeIsSelected.setValue(false);
        return null;
    }


    
}
