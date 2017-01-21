package uk.co.reillyfamily.game;

import uk.co.reillyfamily.game.lwjglwrapper.KeyCode;
import uk.co.reillyfamily.game.lwjglwrapper.Window;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by stuart on 21/01/17.
 */
public class InputThread implements Runnable {
    private Map<KeyCode, Runnable> actions;
    private Window window;

    public InputThread(Window window) {
        actions = Collections.synchronizedMap(new EnumMap<KeyCode, Runnable>(KeyCode.class));
        this.window = window;
    }

    public InputThread(Window window, Map<KeyCode, Runnable> actions) {
        actions = Collections.synchronizedMap(new EnumMap<>(actions));
        this.window = window;
    }

    public InputThread setAction(KeyCode key, Runnable action) {
        actions.put(key, action);
        return this;
    }

    public InputThread removeAction(KeyCode key) {
        actions.remove(key);
        return this;
    }

    @Override
    public void run() {
        actions.forEach((key, action) -> {
            if (window.isKeyPressed(key)){
                action.run();
            }
        });
    }
}
