package bgu.spl.mics.application.objects;

/**
 * DetectedObject represents an object detected by the camera.
 * It contains information such as the object's ID and description.
 */
public class DetectedObject {
    public String id;
    public String description;

    public DetectedObject(String id, String description) {
        this.id = id;
        this.description = description;
    }
    public DetectedObject(){}; // For serialization

    public String getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public  void setDescription(String description){
        this.description=description;
    }
    public void setId(String id){
        this.id=id;
    }
    @Override
    public String toString() {
        return id + '\'' +
        " (" + description + '\'' +
         ")";
    }
}
//     {
//         "id": "Wall_1",
//         "description": "Wall"
//     },
