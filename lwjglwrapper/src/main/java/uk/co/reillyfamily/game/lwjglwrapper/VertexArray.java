package uk.co.reillyfamily.game.lwjglwrapper;

import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.util.DataType;
import uk.co.reillyfamily.game.lwjglwrapper.util.ErrorUtil;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * A wrapper to an OpenGL vertex array object.
 */
public class VertexArray implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(VertexBuffer.class);

    private final int handle;
    private Map<String, Integer> attribs;

    /**
     * Creates an empty vertex array.
     * @return The vertex array object.
     */
    public static VertexArray create() {
        LOGGER.debug("Creating empty VertexArray");
        return new VertexArray();
    }

    /**
     * Creates a vertex array object with the given attributes. The given map's keys must be the string ID of the
     * attribute to be used in GLSL and the value must be the attribute location given by glGetAttibLocation.
     * @param attribs A map of the attributes for this vertex array.
     * @return A vertex array object with the given attributes.
     */
    public static VertexArray withAttribs(Map<String, Integer> attribs) {
        LOGGER.debug("Creating populated VertexArray");
        VertexArray array = new VertexArray();
        array.attribs.putAll(attribs);
        LOGGER.trace("Vertex Array info [Length: {}]", attribs.size());

        return array;
    }

    private VertexArray() {
        handle = glGenVertexArrays();
        attribs = new HashMap<>();
    }

    /**
     * Sets the currently bound vertex array (which is an ARRAY_BUFFER) to the given ID to be used by GLSL. A call is
     * made to check for OpenGL errors before this function returns. If an error is found, an exception will be thrown.
     * The given program is assumed to be bound before this method is called.
     * @param id The ID of the data for GLSL.
     * @param entryLen The length of a single entry in the data, i.e. 3 if a 3 dimensional vector.
     * @param dataType The type of data stored by the vertex array.
     * @param normalize Is the data to be normalized by OpenGL.
     * @param program The shader program this will be bound to.
     * @return This.
     * @throws GLException Thrown if glError produces an error.
     */
    public VertexArray setAttribPtr(String id, int entryLen, DataType dataType, boolean normalize, Program program) {
        int loc = attribs.computeIfAbsent(id, name -> glGetAttribLocation(program.getHandle(), name));
        glVertexAttribPointer(loc, entryLen, dataType.getGlCode(), normalize, 0, 0);
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to set attrib pointer!", e))
                .ifPresent(e -> {throw e;});

        LOGGER.debug("Set {} attrib pointer", id);
        LOGGER.trace("Attrib Pointer info [id: {}, entryLen: {}, dataType: {}", id, entryLen, dataType);
        return this;
    }

    /**
     * Enables the attribute with the given ID.
     * @param id The ID of the attribute to be enabled.
     * @return This.
     * @throws GLException Thrown if glError produces an error.
     */
    public VertexArray enableAttrib(String id) {
        glEnableVertexAttribArray(attribs.get(id));
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to enable an attrib!", e))
                .ifPresent(e -> {throw e;});
        return this;
    }

    /**
     * Enables all attributes bound to this vertex array.
     * @return This.
     * @throws GLException Thrown if glError produces an error.
     */
    public VertexArray enableAllAttribs() {
        attribs.values().forEach(GL20::glEnableVertexAttribArray);
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to enable an/some/all attribs!", e))
                .ifPresent(e -> {throw e;});
        return this;
    }

    /**
     * Disables the attribute with the given ID.
     * @param id The ID of the attribute to be disabled.
     * @return This.
     * @throws GLException Thrown if glError produces an error.
     */
    public VertexArray disableAttrib(String id) {
        glDisableVertexAttribArray(attribs.get(id));
        ErrorUtil.checkGlError().map(e -> new GLException("Failed to disable attrib!", e)).ifPresent(e -> {throw e;});
        return this;
    }

    /**
     * Disables all attributes bound to this vertex array.
     * @return This.
     * @throws GLException Thrown if glError produces an error.
     */
    public VertexArray disableAllAttribs() {
        attribs.values().forEach(GL20::glDisableVertexAttribArray);
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to disable an/some/all attribs!", e))
                .ifPresent(e -> {throw e;});
        return this;
    }

    /**
     * Binds this VertexArray.
     */
    public VertexArray bind() {
        glBindVertexArray(handle);
        return this;
    }

    /**
     * Unbinds this VertexArray. Not needed normally as would be a redundant call if another VertexArray was to be bound
     * after the call.
     */
    public VertexArray unbind() {
        glBindVertexArray(0);
        return this;
    }

    /**
     * Closes this vertex array. This vertex array must not be called upon after this call.
     */
    @Override
    public void close() {
        glDeleteVertexArrays(handle);
    }
}
