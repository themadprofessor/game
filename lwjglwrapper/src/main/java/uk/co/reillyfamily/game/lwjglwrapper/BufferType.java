package uk.co.reillyfamily.game.lwjglwrapper;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
 * Created by stuart on 12/12/16.
 */
public enum BufferType {
    ARRAY(GL_ARRAY_BUFFER), ELEMENT_ARRY(GL_ELEMENT_ARRAY_BUFFER), COPY_READ(GL_COPY_READ_BUFFER),
    COPY_WRITE(GL_COPY_WRITE_BUFFER), PIXEL_UNPACK(GL_PIXEL_UNPACK_BUFFER), PIXEL_PACK(GL_PIXEL_PACK_BUFFER),
    QUERY(GL_QUERY_BUFFER), TEXTURE(GL_TEXTURE_BUFFER), TRANSFORM_FEEDBACK(GL_TRANSFORM_FEEDBACK_BUFFER),
    UNIFORM(GL_UNIFORM_BUFFER), DRAW_INDIRECT(GL_DRAW_INDIRECT_BUFFER), ATOMIC_COUNTER(GL_ATOMIC_COUNTER_BUFFER),
    DISPATCH_INDIRECT(GL_DISPATCH_INDIRECT_BUFFER), SHADER_STORAGE(GL_SHADER_STORAGE_BUFFER);

    private final int glCode;

    BufferType(int glCode) {
        this.glCode = glCode;
    }

    public int getGlCode() {
        return glCode;
    }

    public static BufferType fromCode(final int code) {
        return Arrays.stream(BufferType.values())
                .filter(type -> type.glCode == code)
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Unknown buffer type!"));
    }
}
