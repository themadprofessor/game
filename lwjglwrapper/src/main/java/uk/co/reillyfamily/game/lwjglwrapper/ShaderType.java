package uk.co.reillyfamily.game.lwjglwrapper;

import java.util.Arrays;
import java.util.Optional;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

/**
 * Created by stuart on 12/12/16.
 */
public enum ShaderType {
    /**
     * Represents a GLSL vertex shader.
     */
    VERTEX(GL_VERTEX_SHADER),

    /**
     * Represents a GLSL tessellation control shader.
     */
    TESS_CONTROL(GL_TESS_CONTROL_SHADER),

    /**
     * Represents a GLSL tessellation evaluation shader.
     */
    TESS_EVAL(GL_TESS_EVALUATION_SHADER),

    /**
     * Represents a GLSL geometry shader.
     */
    GEOMETRY(GL_GEOMETRY_SHADER),

    /**
     * Represents a GLSL fragment shader.
     */
    FRAGMENT(GL_FRAGMENT_SHADER),

    /**
     * Represents a GLSL compute shader.
     */
    COMPUTE(GL_COMPUTE_SHADER);

    private final int glCode;

    ShaderType(int glCode) {
        this.glCode = glCode;
    }

    /**
     * Get the OpenGL int value of this shader type.
     * @return The OpenGL int value of this shader type.
     */
    public int getGlCode() {
        return glCode;
    }

    /**
     * Convert the given OpenGL int code into the corresponding ShaderType enum, returning the enum in an optional, or
     * an empty optional if the given code is not for a known shader type.
     * @param code The OpenGL int code to be converted.
     * @return The ShaderType value for the given code.
     */
    public static Optional<ShaderType> fromCode(int code) {
        return Arrays.stream(ShaderType.values()).filter(type -> type.getGlCode() == code).findFirst();
    }
}
