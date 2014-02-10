package hevs.gdx2d.demos;

import hevs.gdx2d.lib.Version;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.backends.lwjgl.LwjglPreferences;
import com.badlogic.gdx.files.FileHandle;

/**
 * A demo selector class, most of the code taken from Libgdx own demo selector.
 * @author Pierre-André Mudry (mui)
 * @version 1.12
 */
@SuppressWarnings("serial")
public class DemoSelector extends JFrame {

	final LinkedHashMap<String, String> tests = new LinkedHashMap<String, String>();

	public DemoSelector() throws HeadlessException {
		super("GDX2D demos " + Version.version + " - mui, chn, mei 2013");

		tests.put("Simple shapes", "simple.DemoSimpleShapes");
		tests.put("Drawing circles", "simple.DemoCircles");
		tests.put("Mistify screen saver", "simple.mistify.DemoLines");
		tests.put("Basic animation", "simple.DemoSimpleAnimation");
		tests.put("Image drawing", "image_drawing.DemoSimpleImage");
		tests.put("Mirroring image", "image_drawing.DemoMirrorImage");
		tests.put("Alpha transparency", "image_drawing.DemoAlphaImage");
		tests.put("Rotating image", "image_drawing.DemoRotatingImage");
		tests.put("Music player", "music.DemoMusicPlay");
		tests.put("Font generation", "fonts.DemoFontGeneration");
		tests.put("Scrolling", "scrolling.DemoScrolling");
		tests.put("Position interpolator", "tween.interpolatorengine.DemoPositionInterpolator");
		tests.put("Complex shapes", "complex_shapes.DemoComplexShapes");
		tests.put("Simple physics (dominoes)", "physics.DemoSimplePhysics");
		tests.put("Physics soccer ball", "physics.DemoPhysicsBalls");
		tests.put("Physics chain collisions", "physics.DemoChainPhysics");
		tests.put("Physics particles", "physics.particle.DemoParticlePhysics");
		tests.put("Physics mouse interactions", "physics.DemoPhysicsMouse");
		tests.put("Physics collision detection", "physics.collisions.DemoCollisionListener");
		tests.put("Lights", "lights.DemoLight");
		tests.put("Rotating lights", "lights.DemoRotateLight");
		tests.put("Shaders postprocessing", "shaders.advanced.DemoPostProcessing");
		tests.put("Shaders convolution", "shaders.advanced.DemoConvolution");
		tests.put("Shaders collection", "shaders.DemoAllShaders");

		// Populate the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new TestList());		
		
		pack();
		setSize(450, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	class TestList extends JPanel {
		private static final long serialVersionUID = 1L;

		private void setIcon(){
			List<Image> icons = new ArrayList<Image>();
			icons.add(new ImageIcon(getClass().getResource("/icon16.png")).getImage());
			icons.add(new ImageIcon(getClass().getResource("/icon32.png")).getImage());
			icons.add(new ImageIcon(getClass().getResource("/icon64.png")).getImage());
			setIconImages(icons);
		}
		
		private void createMenus(){
			JMenuBar bar = new JMenuBar();			
			JMenu about = new JMenu("Help");
			JMenuItem it = new JMenuItem("About");
			it.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ImageIcon icon = new ImageIcon(getClass().getResource("/icon64.png"));
					// TODO: add real link in dialog
					JOptionPane.showMessageDialog(null,							
						    "DemoSelector application for gdx2d lib\nPierre-André Mudry, 2013\nHES-SO Valais 2013",
						    "About this application",
						    JOptionPane.INFORMATION_MESSAGE,
						    icon);
				}
			});
			about.add(it);
			
			JMenu file = new JMenu("File");
			file.setMnemonic(KeyEvent.VK_F);
			JMenuItem q = new JMenuItem("Quit");
			q.setMnemonic(KeyEvent.VK_C);
			q.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
			q.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			file.add(q);
			
			bar.add(file);
			bar.add(Box.createHorizontalGlue());
			bar.add(about);
			setJMenuBar(bar);
			
		}
		
		Image img;
		public TestList() {
			setLayout(new BorderLayout());
			setIcon();
			createMenus();
			final JButton button = new JButton("Run selected demo");
			String[] elements = tests.keySet().toArray(new String[0]);
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			final JList list = new JList(elements);			
			JScrollPane pane = new JScrollPane(list);
			
			DefaultListSelectionModel m = new DefaultListSelectionModel();
			m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m.setLeadAnchorNotificationEnabled(false);
			list.setSelectionModel(m);
			
			list.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent event) {
					if (event.getClickCount() == 2)
						button.doClick();
				}
			});

			list.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
						button.doClick();
				}
			});

			// Save the prefs if required
			final Preferences prefs = new LwjglPreferences(new FileHandle(new LwjglFiles().getExternalStoragePath() + ".prefs/lwjgl-tests"));

			list.setSelectedValue(prefs.getString("last", null), true);

			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String testName = (String) list.getSelectedValue();
					prefs.putString("last", testName);
					prefs.flush();
					dispose();

					// Loads the class based on its name
					try {
						Class<?> clazz = Class.forName("hevs.gdx2d.demos." + tests.get(testName));
						Constructor<?> constructor = clazz.getConstructor(boolean.class);
						constructor.newInstance(false);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});

			add(pane, BorderLayout.CENTER);
			add(button, BorderLayout.SOUTH);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(img, 100, 100, this);
			g2d.dispose();
		}
	}

	public static void main(String[] argv) throws Exception {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

		// Class[] c = DemoSelector.getClasses("hevs.gdx2d.demos");
		//
		// for (Class class1 : c) {
		// if (class1.getSimpleName().startsWith("Demo")) {
		// System.out.println(class1.getSimpleName());
		// }
		// }
		new DemoSelector();
	}
}
