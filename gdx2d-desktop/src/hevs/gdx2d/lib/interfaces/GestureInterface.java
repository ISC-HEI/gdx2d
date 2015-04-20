package hevs.gdx2d.lib.interfaces;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * An interface that applications implement to interact with screen gestures on Android.
 *
 * @author Christopher Métrailler (mei)
 * @version 1.0
 * @see com.badlogic.gdx.input.GestureDetector.GestureListener
 */
public interface GestureInterface {

    /**
     * Called when the user performs a pinch zoom gesture on Android.
     * The original distance is the distance in pixels when the gesture started.
     *
     * @param initialDistance distance between fingers when the gesture started
     * @param distance        current distance between fingers
     */
    void onZoom(float initialDistance, float distance);

    /**
     * Invoked for a tap gesture on Android.
     * A tap happens if a touch went down on the screen and was lifted again without moving outside of the tap square.
     *
     * @param x      the screen X coordinate (origin is the lower left corner)
     * @param y      the screen Y coordinate (origin is the lower left corner)
     * @param count  the number of taps
     * @param button {@link Input.Buttons#LEFT} or {@link Input.Buttons#RIGHT} button clicked
     */
    void onTap(float x, float y, int count, int button);

    /**
     * Called when a user performs a pinch zoom gesture on Android.
     * Reports the initial positions of the two involved fingers and their current positions.
     *
     * @param initialPointer1 finger 1 initial position
     * @param initialPointer2 finger 2 initial position
     * @param pointer1        finger 1 current position
     * @param pointer2        finger 2 current position
     */
    void onPinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2);

    /**
     * Called when the user drags a finger over the screen on Android.
     *
     * @param x      the screen X coordinate (origin is the lower left corner)
     * @param y      the screen Y coordinate (origin is the lower left corner)
     * @param deltaX the difference in pixels to the last drag event on x
     * @param deltaY the difference in pixels to the last drag event on y
     */
    void onPan(float x, float y, float deltaX, float deltaY);

    /**
     * Called on long press on Android.
     *
     * @param x the screen X coordinate (origin is the lower left corner)
     * @param y the screen Y coordinate (origin is the lower left corner)
     */
    void onLongPress(float x, float y);

    /**
     * Called when the user dragged a finger over the screen and lifted it.
     * Reports the last known velocity of the finger in pixels per second.
     *
     * @param velocityX velocity on x in seconds
     * @param velocityY velocity on y in seconds
     * @param button    {@link Input.Buttons#LEFT} or {@link Input.Buttons#RIGHT} button clicked
     */
    void onFling(float velocityX, float velocityY, int button);
}