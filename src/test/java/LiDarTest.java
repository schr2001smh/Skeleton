import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bgu.spl.mics.application.objects.LiDarWorkerTracker;
import bgu.spl.mics.application.services.LiDarService;
import bgu.spl.mics.application.objects.CloudPoint;
import bgu.spl.mics.application.objects.DetectedObject;
import bgu.spl.mics.application.objects.TrackedObject;
import java.util.ArrayList;
import java.util.List;

public class LiDarTest {

    @Test
    public void testPrepareDataWithValidInput() {
        LiDarWorkerTracker tracker = new LiDarWorkerTracker(1, 1);
        LiDarService service = new LiDarService(tracker);
        List<List<Double>> cloudPointsList = new ArrayList<>();
        List<Double> point = new ArrayList<>();
        point.add(1.0);
        point.add(2.0);
        cloudPointsList.add(point);
        DetectedObject detectedObject = new DetectedObject("1", "Test Object");
        List<TrackedObject> trackedObjectsList = new ArrayList<>();

        service.prepareData(cloudPointsList, detectedObject, trackedObjectsList);

        assertEquals(1, trackedObjectsList.size());
        TrackedObject trackedObject = trackedObjectsList.get(0);
        assertEquals("1", trackedObject.getId());
        assertEquals("Test Object", trackedObject.getDescription());
        assertEquals(1, trackedObject.getCoordinates().size());
        CloudPoint cloudPoint = trackedObject.getCoordinates().get(0);
        assertEquals(1.0, cloudPoint.getX());
        assertEquals(2.0, cloudPoint.getY());
    }

    @Test
    public void testPrepareDataWithEmptyCloudPointsList() {
        LiDarWorkerTracker tracker = new LiDarWorkerTracker(1, 1);
        LiDarService service = new LiDarService(tracker);
        List<List<Double>> cloudPointsList = new ArrayList<>();
        DetectedObject detectedObject = new DetectedObject("1", "Test Object");
        List<TrackedObject> trackedObjectsList = new ArrayList<>();

        service.prepareData(cloudPointsList, detectedObject, trackedObjectsList);

        assertEquals(1, trackedObjectsList.size());
        TrackedObject trackedObject = trackedObjectsList.get(0);
        assertEquals("1", trackedObject.getId());
        assertEquals("Test Object", trackedObject.getDescription());
        assertTrue(trackedObject.getCoordinates().isEmpty());
    }

    @Test
    public void testPrepareDataWithDuplicateTrackedObject() {
        LiDarWorkerTracker tracker = new LiDarWorkerTracker(1, 1);
        LiDarService service = new LiDarService(tracker);
        List<List<Double>> cloudPointsList = new ArrayList<>();
        List<Double> point = new ArrayList<>();
        point.add(1.0);
        point.add(2.0);
        cloudPointsList.add(point);
        DetectedObject detectedObject = new DetectedObject("1", "Test Object");
        List<TrackedObject> trackedObjectsList = new ArrayList<>();
        TrackedObject existingTrackedObject = new TrackedObject("1", 0, "Test Object", new ArrayList<>());
        trackedObjectsList.add(existingTrackedObject);

        service.prepareData(cloudPointsList, detectedObject, trackedObjectsList);

        assertEquals(2, trackedObjectsList.size());
    }
}
