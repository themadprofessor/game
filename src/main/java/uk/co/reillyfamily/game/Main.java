package uk.co.reillyfamily.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.reillyfamily.game.lwjglwrapper.GLFWException;
import uk.co.reillyfamily.game.lwjglwrapper.Window;

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
        } catch (GLFWException e) {
            LOGGER.error("Failed to crate window!", e);
            return;
        }
        window.show();
        LOGGER.info("Showing window");

        while (!window.shouldClose()) {

        }

        window.close();
        LOGGER.info("Closed window");
    }
}
