package uk.co.reillyfamily.game.lwjglwrapper;

import java.util.Arrays;
import java.util.Optional;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL21.GL_PIXEL_PACK_BUFFER;
import static org.lwjgl.opengl.GL21.GL_PIXEL_UNPACK_BUFFER;
import static org.lwjgl.opengl.GL30.GL_TRANSFORM_FEEDBACK_BUFFER;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL40.GL_DRAW_INDIRECT_BUFFER;
import static org.lwjgl.opengl.GL42.GL_ATOMIC_COUNTER_BUFFER;
import static org.lwjgl.opengl.GL43.GL_DISPATCH_INDIRECT_BUFFER;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;
import static org.lwjgl.opengl.GL44.GL_QUERY_BUFFER;

/**
 * A type-safe wrapper to OpenGL's vertex buffer object types.
 */
public enum BufferType {
    ARRAY(GL_ARRAY_BUFFER), ELEMENT_ARRAY(GL_ELEMENT_ARRAY_BUFFER), COPY_READ(GL_COPY_READ_BUFFER),
    COPY_WRITE(GL_COPY_WRITE_BUFFER), PIXEL_UNPACK(GL_PIXEL_UNPACK_BUFFER), PIXEL_PACK(GL_PIXEL_PACK_BUFFER),
    QUERY(GL_QUERY_BUFFER), TEXTURE(GL_TEXTURE_BUFFER), TRANSFORM_FEEDBACK(GL_TRANSFORM_FEEDBACK_BUFFER),
    UNIFORM(GL_UNIFORM_BUFFER), DRAW_INDIRECT(GL_DRAW_INDIRECT_BUFFER), ATOMIC_COUNTER(GL_ATOMIC_COUNTER_BUFFER),
    DISPATCH_INDIRECT(GL_DISPATCH_INDIRECT_BUFFER), SHADER_STORAGE(GL_SHADER_STORAGE_BUFFER);

    private final int glCode;

    BufferType(int glCode) {
        this.glCode = glCode;
    }

    /**
     * Get the OpenGL vertex buffer type int value of this buffer type.
     * @return The OpenGL vertex buffer type int value of this buffer type.
     */
    public int getGlCode() {
        return glCode;
    }

     /**
     * Convert the given OpenGL int code into the corresponding BufferType enum, returning the enum in an optional, or
     * an empty optional if the given code is not for a known key code.
     * @param code The OpenGL int code to be converted.
     * @return The BufferType value for the given code.
     */
    public static Optional<BufferType> fromCode(final int code) {
        return Arrays.stream(BufferType.values())
                .filter(type -> type.glCode == code)
                .findFirst();
    }
}
