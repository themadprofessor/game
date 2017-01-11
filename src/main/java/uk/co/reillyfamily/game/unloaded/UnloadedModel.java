package uk.co.reillyfamily.game.unloaded;

import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * Created by stuart on 10/01/17.
 */
public class UnloadedModel {
    private ArrayList<Vector3f> vertices;

    public UnloadedModel(ArrayList<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public UnloadedModel() {
    }

    public ArrayList<Vector3f> getVertices() {
        return vertices;
    }

    public UnloadedModel setVertices(ArrayList<Vector3f> vertices) {
        this.vertices = vertices;
        return this;
    }
}
