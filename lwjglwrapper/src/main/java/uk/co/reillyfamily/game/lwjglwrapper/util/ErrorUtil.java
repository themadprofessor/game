package uk.co.reillyfamily.game.lwjglwrapper.util;

import uk.co.reillyfamily.game.lwjglwrapper.GLException;

import java.util.Optional;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_INVALID_FRAMEBUFFER_OPERATION;

/**
 * Created by stuart on 12/12/16.
 */
public class ErrorUtil {
    public static Optional<GLException> checkGlError() {
        StringBuilder log = new StringBuilder();
        int code;
        while ((code = glGetError()) != GL_NO_ERROR) {
            log.append(codeToInfo(code)).append(" ");
        }
        return log.length() != 0 ?
                Optional.of(new GLException(log.toString().trim())):
                Optional.empty();
    }

    public static String codeToInfo(int code) {
        switch (code) {
            case GL_NO_ERROR: return "No Error";
            case GL_INVALID_ENUM: return "Invalid Enum";
            case GL_INVALID_VALUE: return "Invalid Value";
            case GL_INVALID_OPERATION: return "Invalid Operation";
            case GL_INVALID_FRAMEBUFFER_OPERATION: return "Invalid Framebuffer Operation";
            case GL_OUT_OF_MEMORY: return "Out of Memory";
            case GL_STACK_UNDERFLOW: return "Stack Underflow";
            case GL_STACK_OVERFLOW: return "Stack Overflow";
            default: return "Unknown Error [" + code + "]";
        }
    }
}
