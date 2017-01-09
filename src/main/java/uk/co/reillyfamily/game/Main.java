package uk.co.reillyfamily.game;

import org.lwjgl.BufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.*;
import uk.co.reillyfamily.game.lwjglwrapper.util.DataType;
import uk.co.reillyfamily.game.lwjglwrapper.util.FrameTimeCounter;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;

/**
 * Created by stuart on 20/11/16.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Window window;
        try {
            window = new Window(800, 600, "Test");
            LOGGER.info("OpenGL Version: {}", glGetString(GL_VERSION));
            LOGGER.info("OpenGL Vendor: {}", glGetString(GL_VENDOR));
            LOGGER.info("OpenGL Renderer: {}", glGetString(GL_RENDERER));
            LOGGER.info("GLSL Version: {}", glGetString(GL_SHADING_LANGUAGE_VERSION));
        } catch (GLFWException e) {
            LOGGER.error("Failed to crate window!", e);
            return;
        }

        Program program = new Program();
        try {
            program.loadShader(ShaderType.VERTEX, new File("src/main/resources/default.vert"));
            program.loadShader(ShaderType.FRAGMENT, new File("src/main/resources/default.frag"));
            program.link();
            program.bind();
        } catch (IOException e) {
            LOGGER.error("Failed to setup Program", e);
            return;
        }

        VertexBuffer buffer = VertexBuffer.create(BufferType.ARRAY, DataType.FLOAT);
        VertexArray array = VertexArray.create();
        FloatBuffer data;
        try {
            buffer.bind();
            Float[] coords = new CsvParser().parse(new File("test.csv"));
            float[] real_coords = new float[coords.length];
            for (int i = 0; i < coords.length; i++) {
                real_coords[i] = coords[i];
            }
            data = BufferUtils.createFloatBuffer(coords.length);
            data.put(real_coords);
            data.flip();
            buffer.addData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        array.bind();
        array.setAttribPtr("position", 3, DataType.FLOAT, false, program);
        array.enableAllAttribs();

        window.show();

        String titleBase = "Test    Frame Time: ";
        String titleEnd = "ms";
        int baseLen = titleBase.length();
        StringBuilder titleBuilder = new StringBuilder(titleBase);
        FrameTimeCounter counter = new FrameTimeCounter();

        LOGGER.info("Finished initialisation, entering game loop");
        glClearColor(0.5f, 0.5f, 0.5f, 1);
        while (!window.shouldClose()) {
            glDrawArrays(GL_TRIANGLES, 0, 3);

            window.update();
            titleBuilder.append(counter.tick()/1000000).append(titleEnd);
            window.setTitle(titleBuilder.toString());
            titleBuilder.setLength(baseLen);
        }

        buffer.close();
        array.close();
        program.close();
        window.close();
        LOGGER.info("Exiting");
    }
}
