package hevs.gdx2d.lib.gui;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import hevs.gdx2d.lib.Game2D;
import hevs.gdx2d.lib.GdxConfig;
import hevs.gdx2d.lib.PortableApplication;

import javax.swing.*;
import java.awt.*;

/**
 * A simple frame to host a PortableApplication, used mainly by Demoselector
 *
 * @author Pierre-André Mudry
 */
public class GdxDialog extends JDialog {
	LwjglCanvas canvas1;
	private Thread shutdownHook;

	public GdxDialog(PortableApplication p) {
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/lib/icon16.png")).getImage());

		FlowLayout l = new FlowLayout();
		l.setHgap(0); // No margin
		l.setVgap(0);
		Container container = getContentPane();
		container.setLayout(l);

		canvas1 = new LwjglCanvas(new Game2D(p), GdxConfig.getLwjglConfig(500, 500, false)) {
			protected void stopped() {
				GdxDialog.this.dispose();
			}

			protected void setTitle(String title) {
				GdxDialog.this.setTitle(title);
			}

			protected void setDisplayMode(int width, int height) {
				GdxDialog.this.getContentPane().setPreferredSize(new Dimension(width, height));
				GdxDialog.this.getContentPane().invalidate();
				GdxDialog.this.pack();
				GdxDialog.this.setLocationRelativeTo(null);
				//updateSize(width, height);
			}

			protected void resize(int width, int height) {
				//updateSize(width, height);
			}

			protected void start() {
//				GDXJFrame.this.start();
			}

			protected void exception(Throwable t) {
			}

			protected int getFrameRate() {
//				int frameRate = LwjglFrame.this.getFrameRate();
//				return frameRate == 0 ? super.getFrameRate() : frameRate;
				return 0;
			}
		};


		setHaltOnShutdown(true);
		canvas1.getCanvas().setSize(500, 500);
		container.add(canvas1.getCanvas());
		container.setBackground(Color.BLACK);

		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);
	}


	public void setHaltOnShutdown(boolean halt) {
		if (halt) {
			if (shutdownHook != null) return;
			shutdownHook = new Thread() {
				public void run() {
					Runtime.getRuntime().halt(0); // Because fuck you, deadlock causing Swing shutdown hooks.
				}
			};
			Runtime.getRuntime().addShutdownHook(shutdownHook);
		} else if (shutdownHook != null) {
			Runtime.getRuntime().removeShutdownHook(shutdownHook);
			shutdownHook = null;
		}
	}

	@Override
	public void dispose() {
		canvas1.stop();
		super.dispose();
	}
}