
package seproject.shapes;

import static java.lang.Math.abs;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Group14
 */

public class EllipseModel extends Ellipse implements ShapeModel{
    private Point2D startPoint;
    private Point2D endPoint;
    
    public EllipseModel() {
        super();
        startPoint = new Point2D(0,0);
        endPoint = new Point2D(0,0);
    }
    
     @Override
    public Point2D getLowerBound() {
        return endPoint;
    }

    @Override
    public Point2D getUpperBound() {
        return startPoint;
    }

    @Override 
    public Color getOutlineColor(){
        return (Color) this.getStroke();
    }
    
    @Override
    public Color getFillingColor(){
        return (Color) this.getFill();
    }
    
    @Override
    public void insert(AnchorPane drawingArea, ArrayList<Point2D> points, Color outlineColor, Color fillingColor) {
        setShapeParameters(points);
        this.setStroke(outlineColor);
        this.setFill(fillingColor);
        drawingArea.getChildren().add(this);
    }
    
    @Override
    public ShapeModel nextDraw() {
        return new EllipseModel();
    }

    @Override
    public String saveOnFileString(String separator) {
        return this.getClass().getSimpleName() + separator + 2 + separator + this.getRotation() + separator + startPoint.getX() + separator + startPoint.getY() + separator + endPoint.getX() + separator + endPoint.getY() + separator + this.getStroke() + separator + this.getFill() + separator;
    }

    @Override
    public void setShapeParameters(ArrayList<Point2D> points) {
        
        double newStartX = points.get(0).getX();
        double newStartY = points.get(0).getY();
        double newEndX = points.get(1).getX();
        double newEndY = points.get(1).getY();
        
        this.startPoint = new Point2D((newStartX < newEndX ? newStartX : newEndX),(newStartY < newEndY ? newStartY : newEndY));
        this.endPoint = new Point2D((newStartX > newEndX ? newStartX : newEndX),(newStartY > newEndY ? newStartY : newEndY));
        
        double width = abs(this.endPoint.getX()-this.startPoint.getX());
        double height = abs(this.endPoint.getY()-this.startPoint.getY());
        double centerX = (this.startPoint.getX()+this.endPoint.getX())/2;
        double centerY = (this.startPoint.getY()+this.endPoint.getY())/2;

        this.setCenterX(centerX);
        this.setCenterY(centerY);
        this.setRadiusX(width/2);
        this.setRadiusY(height/2);
        this.setStrokeWidth(2.0);
    }

    @Override
    public void move(Point2D translatePoint) {
        ArrayList<Point2D> points = new ArrayList<>();
        double newStartX = this.getUpperBound().getX() + translatePoint.getX();
        double newStartY = this.getUpperBound().getY() + translatePoint.getY();
        double newEndX = this.getLowerBound().getX() + translatePoint.getX();
        double newEndY = this.getLowerBound().getY() + translatePoint.getY();
        points.add(new Point2D(newStartX,newStartY));
        points.add(new Point2D(newEndX,newEndY));
        this.setShapeParameters(points);
    }

    @Override
    public void changeOutlineColor(Color outlineColor) {
        this.setStroke(outlineColor);
    }

    @Override
    public void changeFillingColor(Color fillingColor) {
        this.setFill(fillingColor); 
    }
    
    @Override
    public ShapeModel pasteShape(AnchorPane drawingArea, Point2D startPoint) {
        EllipseModel toInsert = new EllipseModel();
        ArrayList<Point2D> newPoints = new ArrayList<>();
        newPoints.add(startPoint);
        double newEndX = abs(startPoint.getX() + this.getRadiusX()*2);
        double newEndY = abs(startPoint.getY()+ this.getRadiusY()*2);
        newPoints.add(new Point2D(newEndX, newEndY));
        toInsert.setShapeParameters(newPoints);
        toInsert.setStroke(this.getStroke());
        toInsert.setFill(this.getFill());
        drawingArea.getChildren().add(toInsert);
        return toInsert;
    }

    @Override
    public ArrayList<Point2D> getAllPoints() {
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(this.getUpperBound());
        points.add(this.getLowerBound());
        return points;
    }

    @Override
    public ArrayList<Point2D> getBounds() {
        return this.getAllPoints();
    }

    @Override
    public void rotate(double angle) {
        this.setRotate((this.getRotate() + angle) % 360);
    }

    @Override
    public double getRotation() {
        return this.getRotate();
    }
}
