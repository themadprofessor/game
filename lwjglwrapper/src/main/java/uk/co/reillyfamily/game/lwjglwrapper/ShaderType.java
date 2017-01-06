package uk.co.reillyfamily.game.lwjglwrapper;

import java.util.Arrays;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_GEOMETRY_SHADER_INVOCATIONS;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

/**
 * Created by stuart on 12/12/16.
 */
public enum ShaderType {
    VERTEX(GL_VERTEX_SHADER), TESS_CONTROL(GL_TESS_CONTROL_SHADER), TESS_EVAL(GL_TESS_EVALUATION_SHADER),
    GEOMETRY(GL_GEOMETRY_SHADER), FRAGMENT(GL_FRAGMENT_SHADER), COMPUTE(GL_COMPUTE_SHADER);

    final int glCode;

    ShaderType(int glCode) {
        this.glCode = glCode;
    }

    public int getGlCode() {
        return glCode;
    }

    public static ShaderType fromCode(int code) {
        return Arrays.stream(ShaderType.values())
                .filter(type -> type.getGlCode() == code)
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unknown Shader Type!"));
    }
}
