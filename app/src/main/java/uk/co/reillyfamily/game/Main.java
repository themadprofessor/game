package uk.co.reillyfamily.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.GLFWException;
import uk.co.reillyfamily.game.lwjglwrapper.Program;
import uk.co.reillyfamily.game.lwjglwrapper.ShaderType;
import uk.co.reillyfamily.game.lwjglwrapper.Window;
import uk.co.reillyfamily.game.lwjglwrapper.util.FrameTimeCounter;
import uk.co.reillyfamily.game.unloaded.UnloadedModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;

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

        Model model;
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(
                Files.newInputStream(new File("../modelparser/teapot.model").toPath())))) {
            UnloadedModel unloadedModel = (UnloadedModel) in.readObject();
            model = new Model(unloadedModel, program);
        } catch (IOException e) {
            LOGGER.error("Failed to load model", e);
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene();
        scene.addNode(model);

        window.show();

        String titleBase = "Test    Frame Time: ";
        String titleEnd = "ms";
        int baseLen = titleBase.length();
        StringBuilder titleBuilder = new StringBuilder(titleBase);
        FrameTimeCounter counter = new FrameTimeCounter();

        LOGGER.info("Finished initialisation, entering game loop");
        glClearColor(0.1f, 0.6f, 1f, 1);
        while (!window.shouldClose()) {
            scene.draw(program);

            window.update();
            titleBuilder.append(counter.tick()/1000000).append(titleEnd);
            window.setTitle(titleBuilder.toString());
            titleBuilder.setLength(baseLen);
        }

        model.close();
        program.close();
        window.close();
        LOGGER.info("Exiting");
    }
}
