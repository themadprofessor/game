package uk.co.reillyfamily.game.lwjglwrapper;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryUtil;

/**
 * Created by stuart on 20/11/16.
 */
public class GLFWException extends Exception {
    public GLFWException(int error, long description) {
        super(String.format("GLFW Error [0x%X]: %s", error, MemoryUtil.memUTF8(description)));
    }

    public GLFWException(String message) {
        super(message);
    }

    public GLFWException(String message, Throwable cause) {
        super(message, cause);
    }

    public GLFWException(Throwable cause) {
        super(cause);
    }

    public GLFWException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
