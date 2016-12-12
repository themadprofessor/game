package uk.co.reillyfamily.game.lwjglwrapper;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 */
public enum DataType {
    FLOAT(GL_FLOAT), DOUBLE(GL_DOUBLE), UBYTE(GL_UNSIGNED_BYTE), BYTE(GL_BYTE), UINT(GL_UNSIGNED_INT),
    INT(GL_INT), USHORT(GL_UNSIGNED_SHORT), SHORT(GL_SHORT);

    private final int glCode;

    DataType(int code) {
        this.glCode = code;
    }

    /**
     * Returns the OpenGL constant which corrisponds to the datatype.
     * @return The OpenGL constant.
     */
    public int getGlCode() {
        return glCode;
    }

    /**
     * Returns the corrisponding DataType for the given code.
     * @param code The OpenGL constant for the data type.
     * @return The DataType.
     * @throws UnsupportedOperationException Thrown if the given code does not corrispond to an OpenGL datatype.
     */
    public static DataType fromCode(final int code) {
        switch (code) {
            case GL_FLOAT: return FLOAT;
            case GL_DOUBLE: return DOUBLE;
            case GL_UNSIGNED_BYTE: return UBYTE;
            case GL_BYTE: return BYTE;
            case GL_UNSIGNED_INT: return UINT;
            case GL_INT: return INT;
            case GL_UNSIGNED_SHORT: return USHORT;
            case GL_SHORT: return SHORT;
            default: throw new UnsupportedOperationException("Unknown data type!");
        }
    }
}
