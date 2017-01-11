package uk.co.reillyfamily.game.unloaded;

import java.io.Serializable;

/**
 * Created by stuart on 10/01/17.
 */
public class UnloadedModel implements Serializable {
    private static final long serialVersionUID = 6003203624135877402L;
    private float[] vertices;
    private int[] faces;

    public UnloadedModel(float[] vertices, int[] faces) {
        this.vertices = vertices;
        this.faces = faces;
    }

    public UnloadedModel() {
    }

    public float[] getVertices() {
        return vertices;
    }

    public UnloadedModel setVertices(float[] vertices) {
        this.vertices = vertices;
        return this;
    }

    public int[] getFaces() {
        return faces;
    }

    public UnloadedModel setFaces(int[] faces) {
        this.faces = faces;
        return this;
    }
}
