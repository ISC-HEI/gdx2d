package hevs.gdx2d.demos;

import hevs.gdx2d.lib.gui.DemoSelectorGUI;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * A demo selector class, most of the code taken from Libgdx own demo selector.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 2.0
 */
public class DemoSelector {
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

		new DemoSelectorGUI();
	}
}