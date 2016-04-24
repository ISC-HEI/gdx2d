package ch.hevs.gdx2d.lib;

/**
 * Core library version.
 */
public class Version {

  public final static String COPY = "mui, chn, mei, pim (c) 2012-2016";

  /**
   * Current version name of the gdx2d library (major.minor.revision).
   */
  public final static String VERSION = "1.2.1-SNAPSHOT";

  /**
   * Indicates if if it is a debug or release version.
   */
  public final static boolean isSnapshot = true;


  /**
   * Print the current version of {@code libgdx} and {@code gdx2d}.
   *
   * @return the version of {@code libgdx} and {@code gdx2d}
   */
  public static String printVerbose() {
    return String.format("gdx2d-core v%s, libgdx v%s | %s", VERSION, com.badlogic.gdx.Version.VERSION, COPY);
  }

  /**
   * Print the current version of {@code gdx2d}.
   *
   * @return the version of {@code gdx2d}
   */
  public static String print() {
    return String.format("gdx2d-core v%s | %s", VERSION, COPY);
  }
}
