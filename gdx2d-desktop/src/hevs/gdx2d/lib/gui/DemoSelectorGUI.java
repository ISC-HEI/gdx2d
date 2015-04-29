package hevs.gdx2d.lib.gui;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.javaswingcomponents.accordion.JSCAccordion;
import com.javaswingcomponents.accordion.TabOrientation;
import com.javaswingcomponents.accordion.plaf.steel.SteelAccordionUI;
import hevs.gdx2d.lib.PortableApplication;
import hevs.gdx2d.lib.Version;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * A demo selector class, most of the code taken from Libgdx own demo selector.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 2.1
 */
@SuppressWarnings({"serial"})
public class DemoSelectorGUI extends JFrame {

	public DemoSelectorGUI() throws Exception {
		super("GDX2D demos " + Version.VERSION + " - mui, chn, mei 2012-2015");

		// Populate the window
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setContentPane(new TestList());

		pack();
		String os = System.getProperty("os.name").toLowerCase();

		// FIXME: Under windows we can set the window size
		// It seems to be a problem under Linux
		if (os.contains("win")) {
			setSize(500, 700);
		}

		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Display all available demos classed in categories.
	 */
	class TestList extends JPanel {

		private Map<String, DemoDescriptor> demosMap;

		/* GUI components */
		private JSCAccordion accordion = new JSCAccordion();
		private RunButton btRun = new RunButton();
		private JTextPane paneComments = new JTextPane();

		/* GUI preferences */
		private Preferences prefs;
		private String selectedDemoName = null;

		public TestList() {
			prefs = Preferences.userRoot().node(this.getClass().getName());
			setLayout(new BorderLayout());
			setIcon();
			createMenus();
			setupAccordeon();

			add(accordion, BorderLayout.CENTER);

			JPanel p = new JPanel();
			p.setBackground(new Color(0xF5F5F5));
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

			p.add(Box.createRigidArea(new Dimension(10, 0)));
			p.add(btRun);
			p.add(Box.createRigidArea(new Dimension(10, 0)));
			p.add(paneComments);
			add(p, BorderLayout.SOUTH);
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
			it.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
			it.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new AboutDialog(DemoSelectorGUI.this).setVisible();
				}
			});
			about.add(it);

			JMenu file = new JMenu("File");
			file.setMnemonic(KeyEvent.VK_F);
			JMenuItem q = new JMenuItem("Quit");
			q.setMnemonic(KeyEvent.VK_C);
			q.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
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
			accordion.setFocusable(false);

			// Remove padding
			SteelAccordionUI steelAccordionUI = (SteelAccordionUI) accordion.getUI();
			steelAccordionUI.setHorizontalBackgroundPadding(0);
			steelAccordionUI.setVerticalBackgroundPadding(0);
			steelAccordionUI.setHorizontalTabPadding(0);
			steelAccordionUI.setVerticalTabPadding(0);
			steelAccordionUI.setTabPadding(0);

			accordion.setVerticalAccordionTabRenderer(new TabRenderer());

			try {
				loadFromJson(); // Load tabs
			} catch (FileNotFoundException e) {
				System.err.println("Unable to read the Json demos file.");
				e.printStackTrace();
			}

			// Open the last selected tab (if any) or the first one
			int tabIdx = Integer.valueOf(prefs.get("selectedTabIdx", "0"));
			accordion.setSelectedIndex(tabIdx);
		}

		private void loadFromJson() throws FileNotFoundException {
			JsonReader r = new JsonReader();
			JsonValue demos = r.parse(getClass().getResourceAsStream("/lib/demosList.json"));

			demosMap = new LinkedHashMap<String, DemoDescriptor>();

			int demoCounter = 1;

			// Read all categories
			for (int i = 0; i < demos.size; i++) {
				JsonValue catDemos = demos.get(i);
				String catName = catDemos.name();

				List<String> l = new ArrayList<String>();
				// Read all demos in the category
				for (int j = 0; j < catDemos.size; j++) {
					JsonValue currentDemo = catDemos.get(j);
					final String name = String.format("%02d. %s", demoCounter, currentDemo.getString("name"));
					final String clazz = currentDemo.getString("class");
					final String descText = currentDemo.getString("desc");
					DemoDescriptor d = new DemoDescriptor(clazz, descText);
					demosMap.put(name, d);
					l.add(name);
					demoCounter++;
				}

				// Add the current tab with demo list
				DemoList dl = new DemoList(l.toArray(new String[l.size()]));
				ToolTipManager.sharedInstance().registerComponent(dl);
				accordion.addTab(catName, dl);
			}
		}

		class RunButton extends JButton implements ActionListener {

			public RunButton() {
				super("Run!");
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedDemoName == null)
					return; // Do nothing if no selection

				// Save the name and the tab demo index
				prefs.put("lastDemoName", selectedDemoName);
				prefs.put("selectedTabIdx", String.valueOf(accordion.getSelectedTab().getIndex()));

				try {
					// Loads the class based on its name
					Class<?> clazz = Class.forName("hevs.gdx2d.demos." + demosMap.get(selectedDemoName).clazz);
					final Constructor<?> constructor = clazz.getConstructor(boolean.class);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							try {
								PortableApplication app = (PortableApplication) constructor.newInstance(false);
								new GdxDialog(app, DemoSelectorGUI.this, selectedDemoName);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					});
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}

		class DemoList extends JList {

			public DemoList(String[] demos) {
				super(demos);

				final Dimension commentDimension = new Dimension(400, 55);
				paneComments.setMinimumSize(commentDimension);
				paneComments.setMaximumSize(commentDimension);
				paneComments.setPreferredSize(commentDimension);
				paneComments.setText("Welcome to gdx2d.\nRunning " + Version.print());
				paneComments.setBackground(new Color(0xF5F5F5));
				paneComments.setEditable(false);

				DefaultListSelectionModel m = new DefaultListSelectionModel();
				m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				m.setLeadAnchorNotificationEnabled(false);
				setSelectionModel(m);

				// Select the last chosen demo (if any)
				selectedDemoName = prefs.get("lastDemoName", "");
				setSelectedValue(selectedDemoName, true);
				setFocusable(true);
				requestFocus();

				addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						selectedDemoName = (String) getSelectedValue();
						paneComments.setText(demosMap.get(selectedDemoName).desc);
					}
				});

				addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent event) {
						if (event.getClickCount() == 2)
							btRun.doClick();
					}
				});

				// Add a Mouse Motion adapter to display a tooltip on list elements
				addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseMoved(MouseEvent e) {
						ListModel model = getModel();
						int index = locationToIndex(e.getPoint());
						if (index >= 0) {
							// Display the demo description as tooltip
							final String desc = demosMap.get(model.getElementAt(index)).desc;
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

		/**
		 * Load demos and categories from an external JSON file.
		 */
		class DemoDescriptor {
			final String clazz;
			final String desc;

			DemoDescriptor(String c, String d) {
				desc = d;
				clazz = c;
			}
		}
	}
}
