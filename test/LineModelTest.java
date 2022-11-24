
import javafx.scene.layout.AnchorPane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import seproject.LineModel;


/**
 *
 * @author Group14
 */
public class LineModelTest {
    
    private LineModel testShapeLine;
    
    public LineModelTest() {
        testShapeLine = new LineModel();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testInsert(){    
        AnchorPane drawingPane = new AnchorPane();
        double startX = Math.random()*663;
        double startY = Math.random()*479;
        double endX = Math.random()*663; 
        double endY = Math.random()*479;
        
        testShapeLine.insert(drawingPane, startX, startY, endX, endY);
        assertEquals(1,drawingPane.getChildren().size());
        assertEquals(javafx.scene.shape.Line.class,drawingPane.getChildren().get(0).getClass());
    }
    
    

}
