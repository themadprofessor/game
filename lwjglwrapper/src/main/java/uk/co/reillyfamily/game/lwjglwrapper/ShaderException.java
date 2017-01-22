package uk.co.reillyfamily.game.lwjglwrapper;

/**
 * Unchecked exception thrown if an issue occurs with shader or shader program objects.
 */
public class ShaderException extends GLException {
    private static final long serialVersionUID = -7286327589912549162L;

    /**
     * {@inheritDoc}
     */
    public ShaderException() {
    }

    /**
     * {@inheritDoc}
     */
    public ShaderException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public ShaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
