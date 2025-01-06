import org.junit.jupiter.api.Test;
import bgu.spl.mics.application.objects.TrackedObject;
import bgu.spl.mics.application.objects.CloudPoint;
import bgu.spl.mics.application.objects.Pose;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FusionSLAMtest {
    
    @Test
    public void testSetCoordinates() {
        TrackedObject obj = new TrackedObject("1", 0, "test", Arrays.asList(new CloudPoint(0, 0)));
        List<CloudPoint> newCoordinates = Arrays.asList(new CloudPoint(1, 1), new CloudPoint(2, 2));
        obj.setCoordinates(newCoordinates);
        assertEquals(newCoordinates, obj.getCoordinates());
    }

    @Test
    public void testAddCoordinate() {
        TrackedObject obj = new TrackedObject("1", 0, "test", new ArrayList<>(Arrays.asList(new CloudPoint(0, 0))));
        CloudPoint newPoint = new CloudPoint(1, 1);
        obj.addCoordinate(newPoint);
        assertTrue(obj.getCoordinates().contains(newPoint));
    }

    @Test
    public void testTransformToCoordinateSystem() {
        TrackedObject obj = new TrackedObject("1", 0, "test", Arrays.asList(new CloudPoint(1, 0)));
        Pose pose = new Pose(0, 0, 0, 90);
        obj.transformToCoordinateSystem(pose);
        CloudPoint transformedPoint = obj.getCoordinates().get(0);
        assertEquals(0, transformedPoint.getX(), 0.001);
        assertEquals(1, transformedPoint.getY(), 0.001);
    }

    @Test
    public void testGetCoordinates() {
        List<CloudPoint> coordinates = Arrays.asList(new CloudPoint(0, 0), new CloudPoint(1, 1));
        TrackedObject obj = new TrackedObject("1", 0, "test", coordinates);
        assertEquals(coordinates, obj.getCoordinates());
    }

    @Test
    public void testUpdateWithNewData() {
        List<CloudPoint> coordinates1 = new ArrayList<>(Arrays.asList(new CloudPoint(0, 0), new CloudPoint(1, 1)));
        List<CloudPoint> coordinates2 = Arrays.asList(new CloudPoint(2, 2), new CloudPoint(3, 3));
        TrackedObject obj1 = new TrackedObject("1", 0, "test1", coordinates1);
        TrackedObject obj2 = new TrackedObject("1", 1, "test2", coordinates2);
        obj1.updateWithNewData(obj2);
        assertEquals(1, obj1.getTime());
        assertEquals("test2", obj1.getDescription());
        assertEquals(3, obj1.getCoordinates().size());
        assertEquals(1, obj1.getCoordinates().get(0).getX(), 0.001);
        assertEquals(1, obj1.getCoordinates().get(0).getY(), 0.001);
    }
}
