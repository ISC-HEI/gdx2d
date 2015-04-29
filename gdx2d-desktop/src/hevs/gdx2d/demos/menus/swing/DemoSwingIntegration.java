package hevs.gdx2d.demos.menus.swing;


import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import hevs.gdx2d.demos.physics.DemoSimplePhysics;
import hevs.gdx2d.demos.simple.DemoCircles;
import hevs.gdx2d.lib.Game2D;
import hevs.gdx2d.lib.PortableApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoSwingIntegration extends JFrame {
	LwjglAWTCanvas canvas2;

	int current = 0;

	public DemoSwingIntegration() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Embedding a gdx2demo into Swing components");
		this.setResizable(false);

		final Container container = getContentPane();
		container.setLayout(new FlowLayout());

		PortableApplication.CreateLwjglApplication = false; // TODO: configuration this way is ugly...

		canvas2 = new LwjglAWTCanvas(new Game2D(new DemoSimplePhysics(false)));
		canvas2.getCanvas().setSize(400, 400);
		container.add(canvas2.getCanvas());

		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JButton jb1 = new JButton("Change demo");

		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (canvas2 == null) {
					if ((current % 2) == 1)
						canvas2 = new LwjglAWTCanvas(new Game2D(new DemoSimplePhysics(false)));
					else
						canvas2 = new LwjglAWTCanvas(new Game2D(new DemoCircles(false)));

					canvas2.getCanvas().setSize(400, 400);
					container.add(canvas2.getCanvas());
					pack();
				} else {
					canvas2.stop();
					container.remove(canvas2.getCanvas());
					canvas2 = null;
					current++;
				}
			}
		});

		jp1.add(jb1);
		container.add(jp1);

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
