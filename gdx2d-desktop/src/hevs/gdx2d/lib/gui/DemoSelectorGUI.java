package hevs.gdx2d.lib.gui;

import hevs.gdx2d.lib.Version;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.javaswingcomponents.accordion.JSCAccordion;
import com.javaswingcomponents.accordion.TabOrientation;
import com.javaswingcomponents.accordion.plaf.steel.SteelAccordionUI;

/**
 * A demo selector class, most of the code taken from Libgdx own demo selector.
 * 
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 2.0.1
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class DemoSelectorGUI extends JFrame {
	public DemoSelectorGUI() throws Exception {
		super("GDX2D demos " + Version.version + " - mui, chn, mei 2012-2014");

		// TODO: add these demos and all other to the JSON file
		/*
		 * tests.put("Julia fractal", "simple.DemoJuliaFractal");
		 * tests.put("Complex shapes", "complex_shapes.DemoComplexShapes");
		 * tests.put("Simple physics (dominoes)", "physics.DemoSimplePhysics");
		 * tests.put("Physics soccer ball", "physics.DemoPhysicsBalls");
		 * tests.put("Physics chain collisions",
		 * "physics.chains.DemoChainPhysics"); tests.put("Physics particles",
		 * "physics.particle.DemoParticlePhysics");
		 * tests.put("Physics mouse interactions",
		 * "physics.mouse_interaction.DemoPhysicsMouse");
		 * tests.put("Physics collision detection",
		 * "physics.collisions.DemoCollisionListener");
		 * tests.put("Physics anchor points", "physics.joints.DemoWindmill");
		 * "shaders.advanced.DemoPostProcessing");
		 * tests.put("Shaders convolution", "shaders.advanced.DemoConvolution");
		 * tests.put("Shaders collection", "shaders.DemoAllShaders");
		 */

		// Populate the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new TestList());

		pack();
		setSize(500, 500);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Display all available demos classed in categories.
	 */
	class TestList extends JPanel {

		private Map<String, DemoDescriptor> demosMap;
		private String selectedDemoName = null;

		/* GUI components */
		private JSCAccordion accordion = new JSCAccordion();
		private RunButton btRun = new RunButton();
		private JTextPane paneComments = new JTextPane();

		// TODO: use preferences to select the last selected demo
		// private final Preferences prefs;

		class RunButton extends JButton implements ActionListener {

			public RunButton() {
				super("Run selected demo");
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedDemoName == null)
					return; // Do nothing if no selection

				// prefs.putString("last", selectedDemoName);
				// prefs.flush();

				// FIXME: Do not close the current window ? Only one demo can be
				// started at one time...
				// dispose();

				// Loads the class based on its name
				try {
					Class<?> clazz = Class.forName("hevs.gdx2d.demos."
							+ demosMap.get(selectedDemoName).clazz);
					final Constructor<?> constructor = clazz
							.getConstructor(boolean.class);

					constructor.newInstance(false);

					// Runnable r = new Runnable() {
					// private Constructor<?> c;
					//
					// @Override
					// public void run() {
					// try {
					// c.newInstance(false);
					// } catch (Exception e) {
					// }
					// }
					//
					// private Runnable init(Constructor<?> t) {
					// c = t;
					// return this;
					// }
					// }.init(constructor);
					//
					// new Thread(r).start();
					//
				} catch (Exception e1) {
					System.err.println("Unable to find " + selectedDemoName);
				}
			}
		}

		class DemoList extends JList {

			@SuppressWarnings("unchecked")
			public DemoList(String[] demos) {
				super(demos);

				// TODO: set a fixed height to paneComments (mei)
				paneComments.setBackground(new Color(0xF5F5F5));

				DefaultListSelectionModel m = new DefaultListSelectionModel();
				m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				m.setLeadAnchorNotificationEnabled(false);
				setSelectionModel(m);

				addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						selectedDemoName = (String) getSelectedValue();
						paneComments
								.setText(demosMap.get(selectedDemoName).desc);
					}
				});

				addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent event) {
						if (event.getClickCount() == 2)
							btRun.doClick();
					}
				});

				// Add a Mouse Motion adapter to display a tooltip on list
				// elements
				addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseMoved(MouseEvent e) {
						ListModel model = getModel();
						int index = locationToIndex(e.getPoint());
						if (index >= 0) {
							// Display the demo description as tooltip
							final String desc = demosMap.get(model
									.getElementAt(index)).desc;
							setToolTipText(null);
							setToolTipText(desc);
							paneComments.setText(desc);
						}
					}
				});

				addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER)
							btRun.doClick();
					}
				});
			}
		}

		private void setIcon() {
			List<Image> icons = new ArrayList<Image>();
			icons.add(new ImageIcon(getClass().getResource("/lib/icon16.png"))
					.getImage());
			icons.add(new ImageIcon(getClass().getResource("/lib/icon32.png"))
					.getImage());
			icons.add(new ImageIcon(getClass().getResource("/lib/icon64.png"))
					.getImage());
			setIconImages(icons);
		}

		private void createMenus() {
			JMenuBar bar = new JMenuBar();
			JMenu about = new JMenu("Help");
			JMenuItem it = new JMenuItem("About");
			it.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
					ActionEvent.CTRL_MASK));
			it.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ImageIcon icon = new ImageIcon(getClass().getResource(
							"/lib/icon64.png"));
					// TODO: make this link clickable... Possible but ugly !
					// (mei)
					final String msg = "<HTML><BODY>"
							+ "<b>Demos selector for the \"gdx2d\" library, 2014.</b><br><br>"
							+ "Pierre-Andr&eacute; Mudry<br>"
							+ "Christopher M&eacute;trailler<br><br>"
							+ "Made for the <a href=\"http://inf1.begincoding.net\"/>inf1 course</a> "
							+ "(http://inf1.begincoding.net)." + "<pre>"
							+ Version.print() + "</pre>" + "</BODY></HTML>";
					JOptionPane.showMessageDialog(null, msg,
							"About this application",
							JOptionPane.INFORMATION_MESSAGE, icon);
				}
			});
			about.add(it);

			JMenu file = new JMenu("File");
			file.setMnemonic(KeyEvent.VK_F);
			JMenuItem q = new JMenuItem("Quit");
			q.setMnemonic(KeyEvent.VK_C);
			q.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
					ActionEvent.CTRL_MASK));
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

		private void setupAccordeon() {

			accordion.setTabOrientation(TabOrientation.VERTICAL);
			accordion.setTabHeight(25);
			accordion.setDrawShadow(false);

			SteelAccordionUI steelAccordionUI;
			steelAccordionUI = (SteelAccordionUI) accordion.getUI();

			// Remove all paddings
			steelAccordionUI.setHorizontalBackgroundPadding(0);
			steelAccordionUI.setVerticalBackgroundPadding(0);
			steelAccordionUI.setHorizontalTabPadding(0);
			steelAccordionUI.setVerticalTabPadding(0);
			steelAccordionUI.setTabPadding(0);

			accordion.setVerticalAccordionTabRenderer(new TabRenderer());

			// Load tabs
			try {
				loadFromJson();
			} catch (FileNotFoundException e) {
				System.err.println("Unable to read the Json demos file.");
				return;
			}
		}

		/**
		 * Load demos and categories from an external JSON file.
		 * 
		 * @throws FileNotFoundException
		 */
		class DemoDescriptor {
			final String clazz;
			final String desc;

			DemoDescriptor(String c, String d) {
				desc = d;
				clazz = c;
			}
		}

		private void loadFromJson() throws FileNotFoundException {
			JsonReader r = new JsonReader();
			JsonValue demos = r.parse(getClass().getResourceAsStream(
					"/lib/demosList.json"));

			demosMap = new LinkedHashMap<String, DemoDescriptor>();

			int demoCounter = 1;

			// Read all categories
			for (int i = 0; i < demos.size(); i++) {
				JsonValue catDemos = demos.get(i);
				String catName = catDemos.name();

				List<String> l = new ArrayList<String>();
				// Read all demos in the category
				for (int j = 0; j < catDemos.size(); j++) {
					JsonValue currentDemo = catDemos.get(j);
					final String name = demoCounter + ". "
							+ currentDemo.getString("name");
					final String clazz = currentDemo.getString("class");
					final String descText = currentDemo.getString("desc");
					DemoDescriptor d = new DemoDescriptor(clazz, descText);
					demosMap.put(name, d);
					l.add(name);
					demoCounter++;
				}

				// Add the current tab with demo list
				DemoList dl = new DemoList(l.toArray(new String[0]));
				ToolTipManager.sharedInstance().registerComponent(dl);
				accordion.addTab(catName, dl);
			}
		}

		public TestList() {
			setLayout(new BorderLayout());
			setIcon();
			createMenus();
			setupAccordeon();

			add(accordion, BorderLayout.CENTER);

			JPanel p = new JPanel(new BorderLayout());
			p.add(btRun, BorderLayout.CENTER);
			p.add(paneComments, BorderLayout.SOUTH);
			add(p, BorderLayout.SOUTH);
		}
	}

	public static void main(String[] argv) throws Exception {
		// TODO: launch a specific demo by giving the demo class name as
		// argument (mei)
		new DemoSelectorGUI();
	}
}
