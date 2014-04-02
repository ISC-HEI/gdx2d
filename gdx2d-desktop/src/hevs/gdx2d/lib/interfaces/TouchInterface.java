package hevs.gdx2d.lib.interfaces;

/**
 * An interface that applications implement 
 * to interact with the user using mouse or touch
 *  @author Pierre-André Mudry (mui)
 */
public interface TouchInterface {
	/**
	 * Invoked when the pointer (mouse or touch) is "clicked"
	 */
	public abstract void onClick(int x, int y, int button);
	
	/**
	 * Invoked when the pointer (mouse or touch) is moved and down
	 */
	public abstract void onDrag(int x, int y);

	/**
	 * Invoked when the pointer (mouse or touch) is released
	 */
	public abstract void onRelease(int x, int y, int button);
	
	/**
	 * Invoked when the mouse scroll has been moved
	 * @param amount
	 */
	public abstract void onScroll(int amount);
}