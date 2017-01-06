package uk.co.reillyfamily.game.lwjglwrapper;

/**
 * Created by stuart on 12/12/16.
 */
public class ShaderException extends GlException {
    public ShaderException() {
    }

    public ShaderException(String message) {
        super(message);
    }

    public ShaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShaderException(Throwable cause) {
        super(cause);
    }

    public ShaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
