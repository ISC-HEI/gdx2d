package hevs.gdx2d.lib;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import hevs.gdx2d.demos.simple.DemoCircles;

public class SwingIntegration extends JFrame {
	LwjglCanvas canvas;

	public SwingIntegration () {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container container = getContentPane();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.useGL30 = false;
		config.height = 300;
		config.width = 300;
		config.fullscreen = false;
		config.title = "GDX2D application";
		config.vSyncEnabled = true; // Ignored under Linux
		config.foregroundFPS = 60; // Target value if vSync not working
		config.backgroundFPS = config.foregroundFPS;
		config.samples = 3; // Multi-sampling enables anti-alias for lines
		config.forceExit = false; // Setting true calls system.exit(), with no coming back

//		canvas = new LwjglCanvas(new DemoCircles().theGame, config);
//
//		container.add(canvas.getCanvas(), BorderLayout.CENTER);

		pack();
		setVisible(true);
		setSize(800, 480);
	}

	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SwingIntegration();
			}
		});
	}
}