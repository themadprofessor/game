package uk.co.reillyfamily.game.lwjglwrapper;

/**
 * Created by stuart on 12/12/16.
 */
public class GLException extends RuntimeException {
    public GLException() {
    }

    public GLException(String message) {
        super(message);
    }

    public GLException(int code) {
        this(ErrorUtil.codeToInfo(code));
    }

    public GLException(String message, int code) {
        this(ErrorUtil.codeToInfo(code) + "\t" + message);
    }

    public GLException(String message, Throwable cause) {
        super(message, cause);
    }
}
