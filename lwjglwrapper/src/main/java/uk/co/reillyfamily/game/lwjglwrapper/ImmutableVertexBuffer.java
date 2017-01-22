package uk.co.reillyfamily.game.lwjglwrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.util.DataType;
import uk.co.reillyfamily.game.lwjglwrapper.util.ErrorUtil;

import java.nio.*;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL30.GL_MAP_READ_BIT;
import static org.lwjgl.opengl.GL44.glBufferStorage;

/**
 * An immutable implementation of VertexBuffer.
 */
class ImmutableVertexBuffer extends VertexBuffer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImmutableVertexBuffer.class);

    ImmutableVertexBuffer(BufferType type, DataType dataType, Buffer data) {
        super(type, dataType);

        bind();
        this.size += data.capacity();

        switch (dataType) {
            case FLOAT: glBufferStorage(type.getGlCode(), (FloatBuffer) data, GL_MAP_READ_BIT); break;
            case DOUBLE: glBufferStorage(type.getGlCode(), (DoubleBuffer) data, GL_MAP_READ_BIT); break;
            case UBYTE: case BYTE: glBufferStorage(type.getGlCode(), (ByteBuffer) data, GL_MAP_READ_BIT); break;
            case UINT: case INT: glBufferStorage(type.getGlCode(), (IntBuffer) data, GL_MAP_READ_BIT); break;
            case USHORT: case SHORT: glBufferStorage(type.getGlCode(), (ShortBuffer) data, GL_MAP_READ_BIT); break;
        }
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
