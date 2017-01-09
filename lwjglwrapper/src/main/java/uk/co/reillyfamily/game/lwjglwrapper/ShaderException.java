package uk.co.reillyfamily.game.lwjglwrapper;

/**
 * Created by stuart on 12/12/16.
 */
public class ShaderException extends GLException {
    public ShaderException() {
    }

    public ShaderException(String message) {
        super(message);
    }

    public ShaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
