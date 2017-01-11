package uk.co.reillyfamily.game;

import org.joml.Matrix4f;
import uk.co.reillyfamily.game.lwjglwrapper.Program;

import java.util.List;

/**
 * Created by stuart on 09/01/17.
 */
public interface Renderable {
    void render(List<Matrix4f> mats, Program program, int matLoc);
}
