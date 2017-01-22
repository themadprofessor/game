package uk.co.reillyfamily.game;

import org.joml.*;
import uk.co.reillyfamily.game.lwjglwrapper.Program;
import uk.co.reillyfamily.game.lwjglwrapper.Window;

import java.util.*;

/**
 * Created by stuart on 09/01/17.
 */
public class Scene implements Transformable {
    private Set<Node> nodes;
    private List<Matrix4fc> worldMat;
    private int matLoc = -1;
    private Matrix4f viewMatrix;
    private Vector3f translation;
    private Quaternionf rotation;
    private Vector3f scale;

    public Scene(Window window) {
        nodes = new HashSet<>();
        init(window.getProjectionMatrix());
    }

    public Scene(Window window, Collection<Node> nodes) {
        this.nodes = new HashSet<>(nodes);
        init(window.getProjectionMatrix());
    }

    private void init(Matrix4fc proj) {
        translation = new Vector3f();
        rotation = new Quaternionf();
        scale = new Vector3f(1);
        viewMatrix = new Matrix4f().lookAt(0,4,4,0,2,0,0,1,0).translate(0,0,-5);
        worldMat = new ArrayList<>();
        worldMat.add(proj);
        worldMat.add(viewMatrix);
    }

    public Scene addNode(Node node) {
        nodes.add(node);
        return this;
    }

    public void draw(Program program) {
        if (matLoc == -1) {
            matLoc = program.getUniformLoc("mvp");
        }

        applyTransform();
        nodes.forEach(node -> node.render(worldMat, program, matLoc));
    }

    @Override
    public Transformable translate(Vector3fc translate) {
        translation.add(translate);
        return this;
    }

    @Override
    public Transformable rotate(Quaternionfc rotate) {
        rotation.add(rotate);
        return this;
    }

    @Override
    public Transformable scale(Vector3fc scale) {
        this.scale.add(scale);
        return this;
    }

    @Override
    public Transformable applyTransform() {
        viewMatrix.translate(translation).rotate(rotation).scale(scale);
        translation.zero();
        rotation.identity();
        scale.set(1);
        return this;
    }
}
