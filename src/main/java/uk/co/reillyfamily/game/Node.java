package uk.co.reillyfamily.game;

import org.joml.Matrix4f;
import uk.co.reillyfamily.game.lwjglwrapper.Program;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by stuart on 09/01/17.
 */
public class Node implements Renderable {
    private Set<Node> subnodes;
    private Matrix4f mat;

    public Node() {
        subnodes = new HashSet<>();
        mat = new Matrix4f();
    }

    public Node(Collection<Node> nodes) {
        subnodes = new HashSet<>(nodes);
        mat = new Matrix4f();
    }

    @Override
    public void render(List<Matrix4f> mats, Program program, int matLoc) {
        mats.add(mat);
        subnodes.forEach(node -> node.render(mats, program, matLoc));
        mats.remove(mats.size()-1);
    }
}
