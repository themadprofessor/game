package uk.co.reillyfamily.game.lwjglwrapper;

import org.lwjgl.system.MemoryUtil;

/**
 * An exception which is thrown when GLFW fails to do something. For example, initialise itself or a window.
 */
public class GLFWException extends GLException {

    /**
     * Constructs a GLFWException with the message "GLFW Error [<i>error code in hex</i>}: <i>description decoded from the
     * given address</i>".
     * @param error The GLFW error code.
     * @param description The address to the UTF8 encoded description of the error.
     */
    public GLFWException(int error, long description) {
        super(String.format("GLFW Error [0x%X]: %s", error, MemoryUtil.memUTF8(description)));
    }

    /**
     * Constructs a GLFWException with the given message.
     * @param message The message for this exception.
     */
    public GLFWException(String message) {
        super(message);
    }

    /**
     * Constructs a GLFWException with the given message and cause.
     * @param message The message for this exception.
     * @param cause The cause of this exception.
     */
    public GLFWException(String message, Throwable cause) {
        super(message, cause);
    }
}
