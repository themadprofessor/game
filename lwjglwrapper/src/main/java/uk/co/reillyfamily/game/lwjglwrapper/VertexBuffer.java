package uk.co.reillyfamily.game.lwjglwrapper;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.*;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * Created by stuart on 23/11/16.
 */
public abstract class VertexBuffer implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(VertexBuffer.class);

    final int handle;
    final BufferType type;
    int size;
    DataType dataType;

    /**
     * Creates a new VertexBuffer of the given type and dataType, containing the given data. Depending on the mutable
     * parameter, the VertexBuffer will be mutable or immutable.
     * @param mutable True for a mutable VertexBuffer, false otherwise.
     * @param type The type of VertexBuffer.
     * @param dataType The type of data to be stored by the VertexBuffer.
     * @param data The data to be initially stored in the VertexBuffer.
     * @return A VertexBuffer.
     */
    public static VertexBuffer create(boolean mutable, BufferType type, DataType dataType, Buffer data) {
        if (mutable) {
            return new MutableVertexBuffer(type, dataType, data);
        } else {
            return new ImmutableVertexBuffer(type, dataType, data);
        }
    }

    /**
     * Create an empty, mutable VertexBuffer of the given type and dataType. Use the other create method for an immutable
     * VertexBuffer.
     * @param type The type of VertexBuffer.
     * @param dataType The type of data to be stored by the VertexBuffer.
     * @return A mutable VertexBuffer.
     */
    public static VertexBuffer create(BufferType type, DataType dataType) {
        return new MutableVertexBuffer(type, dataType);
    }

    VertexBuffer(BufferType type, DataType dataType) {
        handle = glGenBuffers();
        this.type = type;
        this.dataType = dataType;
        size = 0;
    }

    VertexBuffer(BufferType type, DataType dataType, Buffer data) {
        this(type, dataType);
        addData(data);
    }

    /**
     * Adds the given data to the VertexBuffer.
     * @param data The data to be added.
     */
    public abstract void addData(Buffer data);

    /**
     * Binds the VertexBuffer.
     */
    public void bind() {
        glBindBuffer(type.getGlCode(), handle);
    }

    /**
     * Unbinds the VertexBuffer. Not normally needed.
     */
    public void unbind() {
        glBindBuffer(type.getGlCode(), 0);
    }

    /**
     * Deletes the VertexBuffer.
     */
    public void close() {
        glDeleteBuffers(handle);
        LOGGER.debug("Deleted VertexBuffer");
    }

    /**
     * Returns the type of data stored in the VertexBuffer.
     * @return The type of data stored in the VertexBuffer.
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Returns the size of the VertexBuffer.
     * @return The size of the VertexBuffer.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the type of VertexBuffer this VertexBuffer is.
     * @return The type of VertexBuffer this VertexBuffer is.
     */
    public BufferType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexBuffer that = (VertexBuffer) o;
        return handle == that.handle &&
                type == that.type &&
                size == that.size &&
                dataType == that.dataType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(handle, type, size, dataType);
    }
}
