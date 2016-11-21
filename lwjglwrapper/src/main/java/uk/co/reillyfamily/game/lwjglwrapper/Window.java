package uk.co.reillyfamily.game.lwjglwrapper;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by stuart on 20/11/16.
 */
public class Window implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    private final long handle;
    private int width;
    private int height;

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
    }

    public void update() {
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }

    public void show() {
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
    }

    public void hide() {
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(handle);
    }

    public void close() {
        glfwDestroyWindow(handle);
        glfwTerminate();
    }
}
