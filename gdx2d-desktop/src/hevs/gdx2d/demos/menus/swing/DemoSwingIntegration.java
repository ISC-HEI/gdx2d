package hevs.gdx2d.demos.menus.swing;


import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTFrame;
import hevs.gdx2d.demos.physics.DemoSimplePhysics;
import hevs.gdx2d.lib.Game2D;
import hevs.gdx2d.lib.PortableApplication;

import javax.swing.*;
import java.awt.*;

public class DemoSwingIntegration extends JFrame {
	LwjglAWTCanvas canvas1;

	public DemoSwingIntegration() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Embedding a gdx2demo into Swing components");
		this.setResizable(false);

		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		PortableApplication.CreateLwjglApplication = false; // TODO: configuration this way is ugly...
		canvas1 = new LwjglAWTCanvas(new Game2D(new DemoSimplePhysics(false)));
		canvas1.getCanvas().setSize(400, 400);

		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		jp1.add(new JButton("A button"));
		jp1.add(new JButton("Another button"));

		container.add(canvas1.getCanvas());
		container.add(jp1);

		pack();
		setSize(600, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new DemoSwingIntegration();
			}
		});
	}

	@Override
	public void dispose() {
		canvas1.stop();
		super.dispose();
	}
}
