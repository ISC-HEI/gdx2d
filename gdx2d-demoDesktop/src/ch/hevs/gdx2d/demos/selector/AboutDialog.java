package ch.hevs.gdx2d.demos.selector;

import ch.hevs.gdx2d.lib.Version;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

/**
 * About dialog for the demo selector.
 *
 * @author Christopher Métrailler (mei)
 * @version 1.0
 */
class AboutDialog {
	private final Icon icon = new ImageIcon(getClass().getResource("/selector/icon64.png"));
	private Component parent;
	private JPanel aboutPanel = new JPanel();

	private final static String TITLE = "About this application";

	private final static String INFO = "<HTML><BODY>"
			+ "<b>Gdx2d demos selector.</b><br><br>"
			+ "Pierre-Andr&eacute; Mudry<br>"
			+ "Christopher M&eacute;trailler<br><br></BODY></HTML>";

	/**
	 * Create the about dialog with clickable links.
	 *
	 * @param parent the parent component
	 */
	AboutDialog(Component parent) {
		this.parent = parent;

		aboutPanel.setPreferredSize(new Dimension(320, 200));		
		aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
		aboutPanel.add(new JLabel(INFO));
		
		JLabel webLabel = new JLabel("Made for the ");
		addUrl(webLabel, "http://inf1.begincoding.net", "inf1 course");
		aboutPanel.add(webLabel);

		JLabel gitLabel = new JLabel();
		addUrl(gitLabel, "https://hevs-isi.github.io/gdx2d/", "https://hevs-isi.github.io/gdx2d/");
		aboutPanel.add(gitLabel);

		String version = String.format("<html><br><pre>gdx2d-core v%s, libgdx v%s<br>%s</pre></html>", Version.VERSION, com.badlogic.gdx.Version.VERSION, Version.COPY);
		JLabel vLabel = new JLabel(version);
		vLabel.setBackground(Color.WHITE);
		aboutPanel.add(vLabel);
	}

	/**
	 * Show the about dialog as a {@link JOptionPane}.
	 */
	void setVisible() {
		JOptionPane.showMessageDialog(parent, aboutPanel, TITLE, JOptionPane.INFORMATION_MESSAGE, icon);
	}

	private static void addUrl(JLabel website, final String url, String text) {
		website.setText(String.format("<html>%s<a href=\"%s\">%s</a></html>", website.getText(), url, text));
		website.setCursor(new Cursor(Cursor.HAND_CURSOR));
		website.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(url));
				} catch (Exception ex) {
					System.err.println("Cannot open " + url);
					ex.printStackTrace();
				}
			}
		});
	}
}
