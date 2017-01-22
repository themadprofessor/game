package uk.co.reillyfamily.game.lwjglwrapper;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import uk.co.reillyfamily.game.lwjglwrapper.util.ErrorUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * A wrapper to an OpenGL shader program object.
 */
public class Program implements AutoCloseable {
    private final int handle;

    /**
     * Constructs a new shader program.
     * @throws ShaderException Thrown if a shader program cannot be created.
     */
    public Program() {
        handle = glCreateProgram();
        if (handle == GL_FALSE) {
            throw new ShaderException("Failed to create program!");
        }
    }

    /**
     * Loads the shader from the given file, compiles it and attaches it to this shader program.
     * @param type The type of shader to be loaded.
     * @param file The shader file.
     * @return This shader program.
     * @throws IOException Thrown if an I/O error occurs.
     * @throws NullPointerException Thrown if any of the arguments are null.
     * @throws ShaderException Thrown if the shader cannot be initialised, compiled or attached. If the shader cannot be
     * compiled, the exception will contain the OpenGL shader log, if present.
     */
    public Program loadShader(ShaderType type, File file) throws IOException {
        Preconditions.checkNotNull(type, "The type of shader cannot be null!");
        Preconditions.checkNotNull(file, "The shader file cannot be null!");
        int shader = glCreateShader(type.getGlCode());
        if (shader == GL_FALSE) {
            throw new ShaderException("Failed to create shader!");
        }

        glShaderSource(shader, Files.toString(file, Charset.defaultCharset()));
        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            String log = glGetShaderInfoLog(shader);
            throw new ShaderException("Failed to compile shader: " + log);
        }

        glAttachShader(handle, shader);
        ErrorUtil.checkGlError()
                .map(e -> new ShaderException("Failed to attach shader to program!", e))
                .ifPresent(e -> {throw e;});
        return this;
    }

    /**
     * Links this shader program.
     * @return This shader program.
     * @throws ShaderException Thrown if the shader fails to link. The exception will contain the OpenGL program log, if
     * present.
     */
    public Program link() {
        glLinkProgram(handle);
        if (glGetProgrami(handle, GL_LINK_STATUS) == GL_FALSE) {
            String log = glGetProgramInfoLog(handle);
            throw new ShaderException("Failed to link program: " + log);
        }

        return this;
    }

    /**
     * Gets the OpenGL int location of the uniform of the given name.
     * @param name The name of the uniform.
     * @return The OpengGL int location of the uniform
     */
    public int getUniformLoc(String name) {
        return glGetUniformLocation(handle, name);
    }

    /**
     * Binds this shader program.
     * @return This shader program.
     */
    public Program bind() {
        glUseProgram(handle);
        return this;
    }

    /**
     * Unbind this shader program. This method is not normally used as a new shader program being bound will implicitly
     * unbind this shader program without the extra call.
     * @return This shader program.
     */
    public Program unbind() {
        glUseProgram(0);
        return this;
    }

    /**
     * Closes this shader program. This shader program object must not be called upon after this call.
     */
    @Override
    public void close() {
        glDeleteProgram(handle);
    }

    /**
     * Get the OpenGL program handle of this shader program.
     * @return The OpenGL program handle.
     */
    protected int getHandle() {
        return handle;
    }
}
