package uk.co.reillyfamily.game.lwjglwrapper;

import uk.co.reillyfamily.game.lwjglwrapper.util.ErrorUtil;

/**
 * Unchecked exception thrown if an error occurs with OpenGL. Primarly used to wrap a glError call producing a value not
 * equal to GL_NO_ERROR.
 */
public class GLException extends RuntimeException {
    private static final long serialVersionUID = -1357914467565628993L;

    /**
     * Constructs a GLException with no message.
     */
    public GLException() {
    }

    /**
     * Constructs a GLException with the given message.
     * @param message The message for this exception.
     */
    public GLException(String message) {
        super(message);
    }

    /**
     * Constructs a GLException with the given OpenGL error code converted into a human-readable string.
     * @param code The OpenGL error code for this exception.
     */
    public GLException(int code) {
        this(ErrorUtil.codeToInfo(code));
    }

    /**
     * Constructs a GLException with the given message and given OpenGL code converted into a human-readable string, in
     * the form "<i>message</i>\t<i>code as string</i>".
     * @param message The message for this exception.
     * @param code The OpenGL error code for this exception.
     */
    public GLException(String message, int code) {
        this(ErrorUtil.codeToInfo(code) + "\t" + message);
    }

    /**
     * Constructs a GLException with the given message and given cause.
     * @param message The message for this exception.
     * @param cause The cause of this exception.
     */
    public GLException(String message, Throwable cause) {
        super(message, cause);
    }
}
