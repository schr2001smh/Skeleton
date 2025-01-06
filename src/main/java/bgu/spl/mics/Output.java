package bgu.spl.mics;

import bgu.spl.mics.application.messages.TrackedObjectsEvent;
import bgu.spl.mics.application.messages.DetectObjectsEvent;
import bgu.spl.mics.application.objects.LandMark;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class Output {
    private int systemRuntime;
    private int numDetectedObjects;
    private int numTrackedObjects;
    private int numLandmarks;
    private Object[] landmarks;

    public void generateOutputJson() {

        Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            // Add logic to skip fields that cause circular references
                            return false;
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .setPrettyPrinting()
                    .create();
    String path = OutputHolder.filePath.substring(0, OutputHolder.filePath.lastIndexOf('\\'));
    try (FileWriter writer = new FileWriter(path + "\\output_file.json")) {
        gson.toJson(this, writer);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    private static class OutputHolder {
        private static final Output instance = new Output();
        private static String filePath;
    }

    public static Output getInstance() {
        return OutputHolder.instance;
    }

    public void setSystemRuntime(int systemRuntime) {
        this.systemRuntime = systemRuntime;
    }

    public void setNumLandmarks(int numLandmarks) {
        this.numLandmarks = numLandmarks;
    }

    public void setLandmarks(Object[] landmarks) {
        this.landmarks = landmarks;
    }

    public void setNumDetectedObjects(int numDetectedObjects) {
        this.numDetectedObjects = numDetectedObjects;
    }

    public void setNumTrackedObjects(int numTrackedObjects) {
        this.numTrackedObjects = numTrackedObjects;
    }

    public void setFilePath(String filePath) {
        OutputHolder.filePath = filePath;
    }
}