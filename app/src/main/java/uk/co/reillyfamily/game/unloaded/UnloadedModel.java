package uk.co.reillyfamily.game.unloaded;

import java.io.Serializable;

/**
 * Created by stuart on 10/01/17.
 */
public class UnloadedModel implements Serializable {
    private static final long serialVersionUID = 6003203624135877402L;
    private float[] vertices;
    private float[] normals;
    private int[] uvs;
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

    public float[] getNormals() {
        return normals;
    }

    public UnloadedModel setNormals(float[] normals) {
        this.normals = normals;
        return this;
    }

    public int[] getUvs() {
        return uvs;
    }

    public UnloadedModel setUvs(int[] uvs) {
        this.uvs = uvs;
        return this;
    }
}
