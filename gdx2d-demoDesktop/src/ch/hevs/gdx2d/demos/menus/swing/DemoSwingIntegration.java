package ch.hevs.gdx2d.demos.menus.swing;


import ch.hevs.gdx2d.demos.physics.DemoSimplePhysics;
import ch.hevs.gdx2d.desktop.Game2D;
import ch.hevs.gdx2d.desktop.PortableApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import ch.hevs.gdx2d.demos.simple.DemoCircles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DemoSwingIntegration extends JFrame {

	int current = 0;

	ArrayList<LwjglAWTCanvas> canvasList = new ArrayList<LwjglAWTCanvas>();

	public DemoSwingIntegration() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Embedding a gdx2demo into Swing components");
		this.setResizable(false);

		final Container container = getContentPane();
		container.setLayout(new FlowLayout());

		PortableApplication.CreateLwjglApplication = false; // TODO: configuration this way is ugly...

		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JButton jb1 = new JButton("Change demo");
		jp1.add(jb1);
		container.add(jp1);

		LwjglAWTCanvas canvas = new LwjglAWTCanvas(new Game2D(new DemoSimplePhysics()));
		canvas.getCanvas().setSize(400, 400);
		container.add(canvas.getCanvas());
		canvasList.add(canvas);

		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				canvasList.get(0).stop();

				LwjglAWTCanvas canvas;
				if ((current % 2) == 1)
					canvas = new LwjglAWTCanvas(new Game2D(new DemoSimplePhysics()));
				else
					canvas = new LwjglAWTCanvas(new Game2D(new DemoCircles()));

				current++;

				canvasList.clear();
				canvasList.add(0, canvas);

				container.remove(1);
				canvas.getCanvas().setSize(400, 400);
				container.add(canvas.getCanvas());
				pack();
			}
		});

		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new DemoSwingIntegration();
			}
		});
	}

}
