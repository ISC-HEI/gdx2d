package ch.hevs.gdx2d.lib;

/**
 * Core library version.
 */
public class Version {

  /** Copyright and authors information. */
  public final static String COPY = "mui, chn, mei, pim (c) 2012-2016";

  /**
   * Current version name of the gdx2d library (major.minor.revision).
   */
  public final static String VERSION = "1.2.1";

  /**
   * Indicates if it is a debug or release version.
   * When snapshot version is set to `false`, debug logs are disabled.
   */
  public final static boolean isSnapshot = false;


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
