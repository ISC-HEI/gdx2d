package ch.hevs.gdx2d.desktop;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ch.hevs.gdx2d.lib.interfaces.GameInterface;
import ch.hevs.gdx2d.lib.interfaces.KeyboardInterface;
import ch.hevs.gdx2d.lib.interfaces.MouseInterface;

/**
 * The base class that should be sub-classed by all {@code gdx2d} desktop
 * applications. To get the functionality required you simply have to overload
 * the required methods.
 *
 * @see MouseInterface
 * @see KeyboardInterface
 * @see GameInterface
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Metrailler (mei)
 * @version 2.0
 */
public abstract class DesktopApplication
		implements MouseInterface, KeyboardInterface, GameInterface {

	// Default window dimensions
	private static final int DEFAULT_HEIGHT = 500;
	private static final int DEFAULT_WIDTH = 500;

	/**
	 * Escape hatch used by the demo selector / Swing integration demos to
	 * construct a {@link DesktopApplication} without spinning up an LWJGL
	 * context. Set to {@code false} before calling {@code super(...)} when you
	 * intend to host the game in an existing Swing/AWT window.
	 */
	public static boolean CreateLwjglApplication = true;

	/**
	 * Creates an application using {@code gdx2d} with a default window size.
	 */
	public DesktopApplication() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Creates a windowed {@code gdx2d} application.
	 *
	 * @param width  The width of the window
	 * @param height The height of the window
	 */
	public DesktopApplication(int width, int height) {
		this(width, height, false);
	}

	/**
	 * Creates a {@code gdx2d} application, optionally full-screen.
	 *
	 * @param width      The width of the window (ignored in full-screen)
	 * @param height     The height of the window (ignored in full-screen)
	 * @param fullScreen {@code true} to launch full-screen
	 */
	public DesktopApplication(int width, int height, boolean fullScreen) {
		// On macOS GLFW must run on the first thread. If we are not on it,
		// re-exec the JVM with -XstartOnFirstThread and exit the current process.
		if (!fromDemoSelector() && CreateLwjglApplication && startNewJvmIfRequired()) {
			return;
		}

		if (fullScreen) {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			width = gd.getDisplayMode().getWidth();
			height = gd.getDisplayMode().getHeight();
		}

		// We only create a context when we were not built from the DemoSelector
		if (!fromDemoSelector() && CreateLwjglApplication)
			createLwjglApplication(width, height, fullScreen);
	}

	/**
	 * On macOS, GLFW requires the main thread to be the first thread of the
	 * process, which in turn requires the JVM to be started with the
	 * {@code -XstartOnFirstThread} flag. If we are not already in that
	 * situation, spawn a new JVM with the flag and exit the current process.
	 *
	 * @return {@code true} if a child JVM was spawned (and the caller should
	 *         return immediately without launching libgdx).
	 */
	private static boolean startNewJvmIfRequired() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (!osName.contains("mac")) {
			return false;
		}

		// Use a simple sentinel env var to avoid an infinite restart loop.
		if ("true".equals(System.getenv("GDX2D_STARTED_ON_FIRST_THREAD"))) {
			return false;
		}

		// If the user already added -XstartOnFirstThread, nothing to do.
		List<String> inputArgs = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments();
		for (String arg : inputArgs) {
			if (arg.equals("-XstartOnFirstThread")) {
				return false;
			}
		}

		String classPath = System.getProperty("java.class.path");
		String mainClass = System.getProperty("sun.java.command");
		if (mainClass == null || mainClass.isEmpty()) {
			System.err.println("Warning: running on macOS without -XstartOnFirstThread and "
					+ "the main class could not be determined. GLFW will crash. "
					+ "Re-run the JVM manually with -XstartOnFirstThread.");
			return false;
		}
		// sun.java.command may contain the jar path (e.g. "-jar foo.jar arg1 arg2")
		// or a class name followed by arguments. Preserve the form.
		String[] mainParts = mainClass.split(" ", 2);

		List<String> jvmArgs = new ArrayList<>();
		jvmArgs.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java");
		jvmArgs.add("-XstartOnFirstThread");
		// Is the original command a -jar launch?
		if (mainClass.endsWith(".jar") || mainParts[0].endsWith(".jar")) {
			jvmArgs.add("-jar");
			jvmArgs.add(mainParts[0]);
		} else {
			jvmArgs.add("-cp");
			jvmArgs.add(classPath);
			jvmArgs.add(mainParts[0]);
		}
		if (mainParts.length > 1) {
			for (String a : mainParts[1].split(" ")) {
				if (!a.isEmpty()) jvmArgs.add(a);
			}
		}

		try {
			ProcessBuilder pb = new ProcessBuilder(jvmArgs);
			pb.redirectErrorStream(true);
			pb.environment().put("GDX2D_STARTED_ON_FIRST_THREAD", "true");
			Process process = pb.start();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			}
			int exitCode = process.waitFor();
			System.exit(exitCode);
			return true;
		} catch (IOException | InterruptedException e) {
			System.err.println("Failed to spawn child JVM with -XstartOnFirstThread: " + e);
			return false;
		}
	}

	private boolean fromDemoSelector() {
		String className = "DemoSelectorGUI";

		StackTraceElement[] callStack = Thread.currentThread().getStackTrace();

		for (StackTraceElement elem : callStack) {
			if (elem.getClassName().contains(className)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Change the title of the application window.
	 *
	 * @param title the application title
	 */
	public void setTitle(String title) {
		Gdx.graphics.setTitle(title);
	}

	/**
	 * @return The height of the display surface (window)
	 */
	public int getWindowHeight() {
		return Gdx.graphics.getHeight();
	}

	/**
	 * @return The width of the display surface (window)
	 */
	public int getWindowWidth() {
		return Gdx.graphics.getWidth();
	}

	/**
	 * Schedule an exit from the application.
	 */
	public void exit() {
		Gdx.app.exit();
	}

	/* MouseInterface callbacks */

	@Override
	public void onClick(int x, int y, int button) {
	}

	@Override
	public void onDrag(int x, int y) {
	}

	@Override
	public void onRelease(int x, int y, int button) {
	}

	@Override
	public void onScroll(int amount) {
	}

	/* KeyboardInterface callbacks */

	@Override
	public void onKeyDown(int keycode) {
		// Exit when the {@code ESCAPE} key is pressed
		if (keycode == Input.Keys.ESCAPE) {
			Gdx.app.exit();
		}
	}

	@Override
	public void onKeyUp(int keycode) {
	}

	/* GameInterface callbacks */

	// Must be implemented in every subclass:
	// void onInit();
	// void onGraphicRender(GdxGraphics g);

	@Override
	public void onDispose() {
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onGameLogicUpdate() {
	}

	@Override
	public void onResume() {
	}

	private void createLwjglApplication(int width, int height, boolean fullScreen) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		Lwjgl3ApplicationConfiguration config = GdxConfig.getLwjgl3Config(width, height, fullScreen);

		Game2D theGame = new Game2D(this);
		new Lwjgl3Application(theGame, config);
	}
}
