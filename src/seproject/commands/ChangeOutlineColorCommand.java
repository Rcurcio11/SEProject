
package seproject.commands;

import seproject.shapes.ShapeModel;
import javafx.scene.paint.Color;
import seproject.exceptions.GenericDrawException;

/**
 *
 * @author Group14
 */
public class ChangeOutlineColorCommand implements OperationCommand {
    
    private ShapeModel shapeSelected;
    private Color outlineColor;
    private Color oldOutlineColor;
    
    public ChangeOutlineColorCommand(ShapeModel shapeSelected, Color outlineColor) {
        this.shapeSelected = shapeSelected;
        this.outlineColor = outlineColor;
        this.oldOutlineColor = shapeSelected.getOutlineColor();
    }
    
    @Override
    public void execute() throws GenericDrawException {
        shapeSelected.changeOutlineColor(outlineColor);
    }

    @Override
    public void undo() {
        shapeSelected.changeOutlineColor(oldOutlineColor);
    }
    
}
