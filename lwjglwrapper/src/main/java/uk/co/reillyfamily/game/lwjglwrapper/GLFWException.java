package uk.co.reillyfamily.game.lwjglwrapper;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryUtil;

/**
 * Created by stuart on 20/11/16.
 */

/**
 * An exception which is thrown when GLFW fails to do something. For example, initialise itself or a window.
 */
public class GLFWException extends Exception {
    /**
     * Creates a GLFWException with the message "GLFW Error [<i>error code in hex</i>}: <i>description decoded from the
     * given address</i>".
     * @param error The GLFW error code.
     * @param description The address to the UTF8 encoded description of the error.
     */
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
