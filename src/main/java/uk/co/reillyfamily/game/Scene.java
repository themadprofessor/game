package uk.co.reillyfamily.game;

import org.joml.Matrix4f;
import uk.co.reillyfamily.game.lwjglwrapper.Program;

import java.util.*;

/**
 * Created by stuart on 09/01/17.
 */
public class Scene {
    private Set<Node> nodes;
    private List<Matrix4f> worldMat;
    private int matLoc = -1;

    public Scene() {
        nodes = new HashSet<>();
        init();
    }

    public Scene(Collection<Node> nodes) {
        nodes = new HashSet<>(nodes);
        init();
    }

    private void init() {
        worldMat = new ArrayList<>();
        worldMat.add(new Matrix4f().perspective((float) Math.PI/2, 16/9, 0.1f, 100f));
        worldMat.add(new Matrix4f().lookAt(4,3,3,0,0,0,0,1,0));
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void draw(Program program) {
        if (matLoc == -1) {
            matLoc = program.getUniformLoc("mvp");
        }

        nodes.forEach(node -> node.render(worldMat, program, matLoc));
    }
}
