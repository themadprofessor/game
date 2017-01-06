package uk.co.reillyfamily.game.lwjglwrapper;

/**
 * Created by stuart on 12/12/16.
 */
public class GlException extends RuntimeException {
    public GlException() {
    }

    public GlException(String message) {
        super(message);
    }

    public GlException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlException(Throwable cause) {
        super(cause);
    }

    public GlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
