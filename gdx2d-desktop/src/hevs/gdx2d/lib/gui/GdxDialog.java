package hevs.gdx2d.lib.gui;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import hevs.gdx2d.lib.Game2D;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.Version;

import javax.swing.*;
import java.awt.*;

/**
 * A simple frame to host a PortableApplication, used mainly by Demoselector
 *
 * @author Pierre-André Mudry
 */
public class GdxDialog extends JDialog {
	private LwjglAWTCanvas canvas;

	public GdxDialog(PortableApplication app, Frame owner) {
		// FIXME: the application title is not available at this moment
		super(owner, "GDX2D demos " + Version.VERSION + " - mui, chn, mei 2012-2015");

		setIconImage(new ImageIcon(getClass().getResource("/lib/icon16.png")).getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);

		FlowLayout l = new FlowLayout();
		l.setHgap(0); // No margin
		l.setVgap(0);
		Container container = getContentPane();
		container.setLayout(l);

		canvas = new LwjglAWTCanvas(new Game2D(app));
		canvas.getCanvas().setSize(app.getWindowWidth(), app.getWindowHeight());
		container.add(canvas.getCanvas());

		pack();
		setVisible(true);
	}

	@Override
	public void dispose() {
		canvas.stop();
		super.dispose();
	}
}
