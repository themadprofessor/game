package uk.co.reillyfamily.game;

import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.*;
import uk.co.reillyfamily.game.lwjglwrapper.util.FrameTimeCounter;
import uk.co.reillyfamily.game.unloaded.UnloadedModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

        Model modelMove;
        Model modelStationary;
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(
                Files.newInputStream(new File("../modelparser/teapot.model").toPath())))) {
            UnloadedModel unloadedModel = (UnloadedModel) in.readObject();
            modelMove = new Model(unloadedModel, program);
            modelStationary = new Model(unloadedModel, program);
            modelStationary.translate(new Vector3f(3,0,0)).applyTransform();
        } catch (IOException e) {
            LOGGER.error("Failed to load model", e);
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(window);
        scene.addNode(modelMove).addNode(modelStationary);

        String titleBase = "Test    Frame Time: ";
        String titleEnd = "ms";
        int baseLen = titleBase.length();
        StringBuilder titleBuilder = new StringBuilder(titleBase);
        FrameTimeCounter counter = new FrameTimeCounter();

        //Eventually will be done through parsing a config file
        InputThread inputThread = new InputThread(window);
        inputThread.setAction(KeyCode.RIGHT, () -> modelMove.translate(new Vector3f(1,0,0)));
        inputThread.setAction(KeyCode.LEFT, () -> modelMove.translate(new Vector3f(-1,0,0)));
        inputThread.setAction(KeyCode.UP, () -> modelMove.translate(new Vector3f(0,0,-1)));
        inputThread.setAction(KeyCode.DOWN, () -> modelMove.translate(new Vector3f(0,0,1)));
        inputThread.setAction(KeyCode.D, () -> scene.translate(new Vector3f(-1,0,0)));
        inputThread.setAction(KeyCode.A, () -> scene.translate(new Vector3f(1,0,0)));
        inputThread.setAction(KeyCode.W, () -> scene.translate(new Vector3f(0,0,1)));
        inputThread.setAction(KeyCode.S, () -> scene.translate(new Vector3f(0,0,-1)));

        ScheduledExecutorService inputService = Executors.newSingleThreadScheduledExecutor();
        inputService.scheduleAtFixedRate(inputThread, 0, 30, TimeUnit.MILLISECONDS);

        LOGGER.info("Finished initialisation, entering game loop");
        window.show();
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.1f, 0.6f, 1f, 1);
        while (!window.shouldClose()) {
            scene.draw(program);
            modelMove.applyTransform();

            window.update();
            titleBuilder.append(counter.tick()/1000000).append(titleEnd);
            window.setTitle(titleBuilder.toString());
            titleBuilder.setLength(baseLen);
        }

        modelMove.close();
        program.close();
        window.close();
        inputService.shutdownNow();
        LOGGER.info("Exiting");
    }
}
