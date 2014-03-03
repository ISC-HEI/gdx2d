package hevs.gdx2d.lib;


/**
 * Version of the library.
 * 
 * @author Pierre-André Mudry
 */
public class Version {
	
	// Current version name of the gdx2d library
	public static final String version = "1.0.0-gamma";

	/**
	 * Print the current version of the library and libgdx.
	 */
	public final static String print() {
		return String.format(
				"gdx2d version \"%s\", libgdx version \"%s\" / mui, chn, mei (c) 2013-2014",
				version, com.badlogic.gdx.Version.VERSION);
	}
}
