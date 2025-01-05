package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a landmark in the environment map.
 * Landmarks are identified and updated by the FusionSlam service.
 */
public class LandMark {
    private String id;
    private String description;
    private List<CloudPoint> coordinates;

    public LandMark(String id, String description) {
        this.id = id;
        this.description = description;
        this.coordinates = new ArrayList<>();
    }

    public LandMark(String id, String description, List<CloudPoint> coordinates) {
        this.id = id;
        this.description = description;
        this.coordinates = coordinates;
    }

    public void addCoordinate(CloudPoint coordinate) {
        coordinates.add(coordinate);
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<CloudPoint> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<CloudPoint> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "{ID: " + id + ", Description: " + description + ", Coordinates: " + coordinates + "}";
    }
}
