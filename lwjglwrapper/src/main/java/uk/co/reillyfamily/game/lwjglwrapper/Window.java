package uk.co.reillyfamily.game.lwjglwrapper;

import javafx.scene.input.KeyCode;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.util.ErrorUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * A GLFW window, which currently only allows for one window, which is bound to an OpenGL context.
 */
public class Window implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    private final long handle;
    private int width;
    private int height;

    /**
     * Creates a new window with the given width, height and title at the centre of the screen.
     * @param width The width of the window.
     * @param height The height of the window.
     * @param title The title of the window.
     * @throws GLFWException Thrown if GLFW could not be initialised or the window could not be created.
     */
    public Window(int width, int height, String title) throws GLFWException {
        this.width = width;
        this.height = height;

        glfwSetErrorCallback(GLFWErrorCallback.createThrow());
        if (!glfwInit()) {
            throw new GLFWException("Failed to initalise GLFW!");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        handle = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (handle == MemoryUtil.NULL) {
            throw new GLFWException("Failed to create window!");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(handle, (vidMode.width() - width)/2, (vidMode.height() - height)/2);

        glfwMakeContextCurrent(handle);
        glfwShowWindow(handle);
        GL.createCapabilities();
        ErrorUtil.checkGlError()
                .map(e -> new GLException("Failed to initialise OpenGL!", e))
                .ifPresent(e -> {throw e;});
        LOGGER.debug("Created Window");
    }

    /**
     * Updates the window and polls for events in that order.
     */
    public void update() {
        glfwSwapBuffers(handle);
        glfwPollEvents();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(handle, title);
    }

    /**
     * Makes the window visible.
     */
    public void show() {
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        LOGGER.debug("Showing Window");
    }

    /**
     * Makes the window invisible.
     */
    public void hide() {
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        LOGGER.debug("Hiding Window");
    }

    /**
     * Returns true if the given GLFW keycode is currently marked as pressed, false otherwise
     * @param key The GLFW keycode of the the key.
     * @return Returns true if the given key is pressed, false otherwise.
     */
    public boolean isKeyPressed(int key) {
        return glfwGetKey(handle, key) == GLFW_PRESS;
    }

    /**
     * Returns true if the given GLFW keycode is currently marked as released, false otherwise
     * @param key The GLFW keycode of the the key.
     * @return Returns true if the given key is released, false otherwise.
     */
    public boolean isKeyReleased(int key) {
        return glfwGetKey(handle, key) == GLFW_RELEASE;
    }

    /**
     * Checks if GLFW believes the window should be closed. For example, the user clicks the window's close button, or
     * the windowing system believes the window should be closed.
     * @return True if the window should close.
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    /**
     * Closes the window and since the GLFW instance is being used to create one window, the GLFW instance will be
     * terminated.
     */
    public void close() {
        glfwDestroyWindow(handle);
        glfwTerminate();
        LOGGER.debug("Closed Window");
    }
}
