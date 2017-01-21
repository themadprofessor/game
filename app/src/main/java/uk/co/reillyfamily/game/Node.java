package uk.co.reillyfamily.game;

import org.joml.*;
import uk.co.reillyfamily.game.lwjglwrapper.Program;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by stuart on 09/01/17.
 */
public class Node implements Renderable, Transformable {
    private Set<Node> subnodes;
    protected Matrix4f mat;
    protected Vector3f translation;
    protected Quaternionf rotation;
    protected Vector3f scale;

    public Node() {
        subnodes = new HashSet<>();
        mat = new Matrix4f();
        init();
    }

    public Node(Collection<Node> nodes) {
        subnodes = new HashSet<>(nodes);
        mat = new Matrix4f();
        init();
    }

    private void init() {
        mat = new Matrix4f();
        translation = new Vector3f();
        rotation = new Quaternionf();
        scale = new Vector3f(1);
    }

    @Override
    public void render(List<Matrix4fc> mats, Program program, int matLoc) {
        mats.add(mat);
        applyTransform();
        subnodes.forEach(node -> node.render(mats, program, matLoc));
        mats.remove(mats.size()-1);
    }

    @Override
    public Transformable translate(Vector3fc translate) {
        translation.add(translate);
        return this;
    }

    @Override
    public Transformable rotate(Quaternionfc rotate) {
        rotation.rotate(rotate.x(), rotate.y(), rotate.z());
        return this;
    }

    @Override
    public Transformable scale(Vector3fc scale) {
        this.scale.add(scale);
        return this;
    }

    public Node applyTransform() {
        mat.translate(translation).rotate(rotation).scale(scale);
        translation.zero();
        rotation.identity();
        scale.set(1);
        return this;
    }
}
