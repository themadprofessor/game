package uk.co.reillyfamily.game;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import uk.co.reillyfamily.game.lwjglwrapper.BufferType;
import uk.co.reillyfamily.game.lwjglwrapper.Program;
import uk.co.reillyfamily.game.lwjglwrapper.VertexArray;
import uk.co.reillyfamily.game.lwjglwrapper.VertexBuffer;
import uk.co.reillyfamily.game.lwjglwrapper.util.DataType;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

/**
 * Created by stuart on 09/01/17.
 */
public class Model extends Node implements AutoCloseable {
    private VertexArray vertexArray;
    private Set<VertexBuffer> vertexBuffers;
    private Matrix4f mat;
    private FloatBuffer buff;

    public Model() {
        vertexArray = VertexArray.create();
        vertexBuffers = new HashSet<>();
        mat = new Matrix4f();
        buff = BufferUtils.createFloatBuffer(16);
    }

    public void addData(VertexBuffer data, String id, int entryLen, boolean normalize, Program program) {
        vertexBuffers.add(data);
        data.bind();
        vertexArray.bind();
        vertexArray.setAttribPtr(id, entryLen, data.getDataType(), normalize, program);
        vertexArray.enableAttrib(id);
    }

    public void addData(Buffer data, DataType dataType, String id, int entryLen, boolean normalize, Program program) {
        addData(VertexBuffer.create(false, BufferType.ARRAY, dataType, data), id, entryLen, normalize, program);
    }

    @Override
    public void render(List<Matrix4f> mats, Program program, int matLoc) {
        buff.clear();
        vertexArray.bind();
        mats.add(mat);
        mats.stream().reduce((matrix4f, matrix4f2) -> {
            Matrix4f mat = new Matrix4f();
            matrix4f.mul(matrix4f2, mat);
            return mat;
        }).orElseGet(Matrix4f::new).get(buff);
        mats.remove(mats.size() -1);

        glUniformMatrix4fv(matLoc, false, buff);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }

    @Override
    public void close() {
        vertexBuffers.forEach(VertexBuffer::close);
        vertexArray.close();
    }
}
