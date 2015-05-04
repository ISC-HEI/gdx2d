package hevs.gdx2d.lib;

/**
 * Gets the version of the library.
 *
 * @author Pierre-André Mudry
 */
public class Version {

	private final static String COPY = "| mui, chn, mei, pim (c) 2012-2015";

	/**
	 * Current version name of the gdx2d library (major.minor.revision).
	 */
	public static final String VERSION = "1.1.1";

	/**
	 * Print the current version of {@code libgdx} and {@code gdx2d}.
	 *
	 * @return the version of {@code libgdx} and {@code gdx2d}
	 */
	public static String printVerbose() {
		return String.format("gdx2d v%s, libgdx v%s %s", VERSION, com.badlogic.gdx.Version.VERSION, COPY);
	}

	/**
	 * Print the current version of {@code gdx2d}.
	 *
	 * @return the version of {@code gdx2d}
	 */
	public static String print() {
		return String.format("gdx2d v%s %s", VERSION, COPY);
	}
}
