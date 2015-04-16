package hevs.gdx2d.lib;

/**
 * Gets the version of the library.
 * 
 * @author Pierre-André Mudry
 */
public class Version {
	
	// Current version name of the gdx2d library
	public static final String version = "1.0.1-SNAPSHOT";

	/**
	 * Prints the current version of the library
	 * @return
	 */
	public final static String printVerbose() {
		return String.format(
				"gdx2d v%s, libgdx v%s | mui, chn, mei (c) 2012-2015",
				version, com.badlogic.gdx.Version.VERSION);
	}
	
	/**
	 * Print the current version of the library and libgdx.
	 */
	public final static String print() {
		return String.format(
				"gdx2d v%s, | mui,chn,mei (c) 2012-2015",
				version);
	}
}
