
package seproject.commands;

import seproject.shapes.ShapeModel;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import seproject.exceptions.GenericDrawException;
import seproject.exceptions.ShapeNotSelectedDrawException;

/**
 *
 * @author Group14
 */
public class ChangeShapeDimensionsCommand implements OperationCommand{

    private AnchorPane drawingArea;
    private ArrayList<Point2D> points;
    private ShapeModel shape;  
    private ArrayList<Point2D> oldPoints;
    
    public ChangeShapeDimensionsCommand(AnchorPane drawingArea, ArrayList<Point2D> points, ShapeModel shape) {
        this.drawingArea = drawingArea;
        this.points = points;
        this.shape = shape;
        this.oldPoints = shape.getAllPoints();
    }
    
    @Override
    public void execute() throws GenericDrawException {
        if(shape == null)
            throw new ShapeNotSelectedDrawException();
        shape.changeDimensions(points);
    }

    @Override
    public void undo() {
        shape.setShapeParameters(oldPoints);
    }
    
}
