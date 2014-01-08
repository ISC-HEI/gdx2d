package hevs.gdx2d.components.geometry;

/**
 * A simple class for working with mathematical vectors
 * 
 * @version 1.0, January 2010
 * @author <a href='mailto:pandre.mudry&#64;hevs.ch'> Pierre-Andre Mudry</a>
 */
public class Vector2D {

	public float x;
	public float y;

	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Used to determine the distance to another vector
	 * 
	 * @param p
	 *            The other vector
	 * @return The distance between the two
	 */
	public double distanceTo(Vector2D p) {
		double a = Math.pow(x - p.x, 2.0);
		double b = Math.pow(y - p.y, 2.0);
		return Math.sqrt(a + b);
	}

	/**
	 * @return The norm of the vector
	 */
	public double norm() {
		double a = Math.pow(x, 2.0);
		double b = Math.pow(y, 2.0);
		return Math.sqrt(a + b);
	}

	public String toString() {
		String result = "";
		result += "X : " + x + " / " + "Y : " + y;
		return result;
	}
}
