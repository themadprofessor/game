package uk.co.reillyfamily.game.lwjglwrapper;

import org.lwjgl.opengl.GL20;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by stuart on 06/01/17.
 */
public class VertexArray implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(VertexBuffer.class);

    private final int handle;
    private Map<String, Integer> attribs;

    public static VertexArray create() {
        LOGGER.debug("Creating empty VertexArray");
        return new VertexArray();
    }

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

    public VertexArray enableAttrib(String id) {
        glEnableVertexAttribArray(attribs.get(id));
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to enable an attrib!", e))
                .ifPresent(e -> {throw e;});
        return this;
    }

    public VertexArray enableAllAttribs() {
        attribs.values().forEach(GL20::glEnableVertexAttribArray);
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to enable an/some/all attribs!", e))
                .ifPresent(e -> {throw e;});
        return this;
    }

    public VertexArray disableAttrib(String id) {
        glDisableVertexAttribArray(attribs.get(id));
        ErrorUtil.checkGlError().map(e -> new GLException("Failed to disable attrib!", e)).ifPresent(e -> {throw e;});
        return this;
    }

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

    @Override
    public void close() {
        glDeleteVertexArrays(handle);
    }
}
