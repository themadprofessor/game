package uk.co.reillyfamily.game.lwjglwrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.util.DataType;
import uk.co.reillyfamily.game.lwjglwrapper.util.ErrorUtil;

import java.nio.*;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL44.glBufferStorage;

/**
 * Created by stuart on 12/12/16.
 */
public class ImmutableVertexBuffer extends VertexBuffer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableVertexBuffer.class);

    ImmutableVertexBuffer(BufferType type, DataType dataType, Buffer data) {
        super(type, dataType, data);

        bind();
        this.size += data.capacity();

        switch (dataType) {
            case FLOAT: glBufferStorage(type.getGlCode(), (FloatBuffer) data, GL_STATIC_DRAW); break;
            case DOUBLE: glBufferStorage(type.getGlCode(), (DoubleBuffer) data, GL_STATIC_DRAW); break;
            case UBYTE: case BYTE: glBufferStorage(type.getGlCode(), (ByteBuffer) data, GL_STATIC_DRAW); break;
            case UINT: case INT: glBufferStorage(type.getGlCode(), (IntBuffer) data, GL_STATIC_DRAW); break;
            case USHORT: case SHORT: glBufferStorage(type.getGlCode(), (ShortBuffer) data, GL_STATIC_DRAW); break;
        }
        unbind();
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to create immutable vertex buffer!", e))
                .ifPresent(e -> {throw e;});
        LOGGER.debug("Creating immutable VertexBuffer");
        LOGGER.trace("VertexBuffer info [Type: {}, DataType: {}, Size: {}]", type, dataType, size);
    }

    public void addData(Buffer data) {
        throw new UnsupportedOperationException("This vertex buffer is immutable!");
    }
}
