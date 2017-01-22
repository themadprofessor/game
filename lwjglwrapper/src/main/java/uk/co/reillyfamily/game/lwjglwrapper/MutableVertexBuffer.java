package uk.co.reillyfamily.game.lwjglwrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.util.DataType;
import uk.co.reillyfamily.game.lwjglwrapper.util.ErrorUtil;

import java.nio.*;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBufferData;

/**
 * A mutable implementation of VertexBuffer.
 */
class MutableVertexBuffer extends VertexBuffer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MutableVertexBuffer.class);

    MutableVertexBuffer(BufferType type, DataType dataType) {
        super(type, dataType);
        LOGGER.debug("Creating mutable VertexBuffer");
        LOGGER.trace("VertexBuffer info [Type: {}, DataType: {}, Size: {}]", type, dataType, size);
    }

    MutableVertexBuffer(BufferType type, DataType dataType, Buffer data) {
        super(type, dataType, data);
        LOGGER.debug("Creating mutable VertexBuffer");
        LOGGER.trace("VertexBuffer info [Type: {}, DataType: {}, Size: {}]", type, dataType, size);
    }

    public void addData(Buffer data) {
        this.size += data.capacity();

        switch (dataType) {
            case FLOAT: glBufferData(type.getGlCode(), (FloatBuffer) data, GL_STATIC_DRAW); break;
            case DOUBLE: glBufferData(type.getGlCode(), (DoubleBuffer) data, GL_STATIC_DRAW); break;
            case UBYTE: case BYTE: glBufferData(type.getGlCode(), (ByteBuffer) data, GL_STATIC_DRAW); break;
            case UINT: case INT: glBufferData(type.getGlCode(), (IntBuffer) data, GL_STATIC_DRAW); break;
            case USHORT: case SHORT: glBufferData(type.getGlCode(), (ShortBuffer) data, GL_STATIC_DRAW); break;
        }
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to add data to mutable vertex buffer!", e))
                .ifPresent(e -> {throw e;});
        LOGGER.trace("Added data to VertexBuffer");
    }
}
