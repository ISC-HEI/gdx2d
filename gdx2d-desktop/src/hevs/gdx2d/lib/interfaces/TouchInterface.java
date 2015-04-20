package hevs.gdx2d.lib.interfaces;

import com.badlogic.gdx.Input;

/**
 * An interface that applications implement to interact with the user using mouse or touch.<br>
 * Coordinates origin is the lower left corner.
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
public interface TouchInterface {

    /**
     * Invoked when the pointer (mouse or touch) is pressed (once).
     * The button parameter will be {@link Input.Buttons#LEFT} on Android.
     *
     * @param x      the screen X coordinate (origin is the lower left corner)
     * @param y      the screen Y coordinate (origin is the lower left corner)
     * @param button {@link Input.Buttons#LEFT} or {@link Input.Buttons#RIGHT} button clicked
     */
    void onClick(int x, int y, int button);

    /**
     * Invoked when the pointer (mouse or touch) is moved and down.
     *
     * @param x the screen X coordinate (origin is the lower left corner)
     * @param y the screen Y coordinate (origin is the lower left corner)
     */
    void onDrag(int x, int y);

    /**
     * Invoked when the pointer (mouse or touch) is released.
     * The button parameter will be {@link Input.Buttons#LEFT} on Android.
     *
     * @param x      the screen X coordinate (origin is the lower left corner)
     * @param y      the screen Y coordinate (origin is the lower left corner)
     * @param button {@link Input.Buttons#LEFT} or {@link Input.Buttons#RIGHT} button clicked
     */
    void onRelease(int x, int y, int button);

    /**
     * Invoked when the mouse scroll (wheel) has been moved.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled
     */
    void onScroll(int amount);
}