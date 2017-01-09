package uk.co.reillyfamily.game.lwjglwrapper;

import com.google.common.io.Files;
import uk.co.reillyfamily.game.lwjglwrapper.util.ErrorUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by stuart on 12/12/16.
 */
public class Program implements AutoCloseable {
    private final int handle;

    public Program() {
        handle = glCreateProgram();
        if (handle == GL_FALSE) {
            throw new ShaderException("Failed to create program!");
        }
    }

    public Program loadShader(ShaderType type, File file) throws IOException {
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

    public Program link() {
        glLinkProgram(handle);
        if (glGetProgrami(handle, GL_LINK_STATUS) == GL_FALSE) {
            String log = glGetProgramInfoLog(handle);
            throw new ShaderException("Failed to link program: " + log);
        }

        return this;
    }

    public Program bind() {
        glUseProgram(handle);
        return this;
    }

    public Program unbind() {
        glUseProgram(0);
        return this;
    }

    @Override
    public void close() {
        glDeleteProgram(handle);
    }

    protected int getHandle() {
        return handle;
    }
}
