package uk.co.reillyfamily.game;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import uk.co.reillyfamily.game.lwjglwrapper.Program;

import java.util.List;

/**
 * Created by stuart on 09/01/17.
 */
public interface Renderable {
    void render(List<Matrix4fc> mats, Program program, int matLoc);
}
