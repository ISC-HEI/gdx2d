package hevs.gdx2d.lib.interfaces;

public interface TouchInterface {
	/*
	 * Invoked when the pointer (mouse or touch) is "clicked"
	 */
	public abstract void onClick(int x, int y, int button);
	
	/*
	 * Invoked when the pointer (mouse or touch) is moved and down
	 */
	public abstract void onDrag(int x, int y);

	/*
	 * Invoked when the pointer (mouse or touch) is released
	 */
	public abstract void onRelease(int x, int y, int button);
}