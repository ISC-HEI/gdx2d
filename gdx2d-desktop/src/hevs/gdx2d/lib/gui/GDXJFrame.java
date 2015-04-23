package hevs.gdx2d.lib.gui;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import hevs.gdx2d.lib.Game2D;
import hevs.gdx2d.lib.PortableApplication;

import javax.swing.*;
import java.awt.*;

/**
 * A simple frame to host a PortableApplication, used mainly by Demoselector
 *
 * @author Pierre-André Mudry
 */
public class GDXJFrame extends JFrame {
	LwjglAWTCanvas canvas1;

	public GDXJFrame(PortableApplication p) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);

		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		canvas1 = new LwjglAWTCanvas(new Game2D(p));
		canvas1.getCanvas().setSize(500, 500);
		container.add(canvas1.getCanvas());
		container.setBackground(Color.BLACK);

		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void dispose() {
		canvas1.stop();
		super.dispose();
	}
}
