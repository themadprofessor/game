package uk.co.reillyfamily.game.modelparser;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;
import uk.co.reillyfamily.game.unloaded.UnloadedModel;

import java.io.InputStream;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.stream.Stream;

/**
 * Created by stuart on 24/01/17.
 */
public class AssimpLoader implements ModelParser {
    @Override
    public UnloadedModel parse(InputStream in) {
        AIScene scene = Assimp.aiImportFile("teapot.obj", Assimp.aiProcessPreset_TargetRealtime_Fast);
        IntBuffer meshIds = scene.mRootNode().mMeshes();
        scene.mMeshes().
        return null;
    }
}
