package uk.co.reillyfamily.game.lwjglwrapper.util;

/**
 * Created by stuart on 09/01/17.
 */
public class FrameTimeCounter {
    private long last;

    public FrameTimeCounter() {
        last = System.nanoTime();
    }

    public long tick() {
        long time = System.nanoTime() - last;
        last = System.nanoTime();
        return time;
    }
}
