package hevs.gdx2d.lib.gui;

import com.javaswingcomponents.accordion.JSCAccordion;
import com.javaswingcomponents.accordion.tabrenderer.AccordionTabRenderer;
import com.javaswingcomponents.accordion.tabrenderer.GetTabComponentParameter;
import com.javaswingcomponents.framework.painters.configurationbound.LinearGradientColorPainter;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JLabel style to display the demos categories as a tab title.
 * <p/>
 * Code adapter from <a
 * href="http://java.dzone.com/articles/java-swing-components">this article</a>.
 *
 * @author Christopher Métrailler (mei)
 * @version 1.0
 */
public class TabRenderer extends JLabel implements AccordionTabRenderer {

	private static final long serialVersionUID = -2561448327221140228L;
	// Tab colors (blue theme)
	private final static Color LIGHT_BLUE = new Color(0x6287d2);
	private final static Color BLUE = new Color(0x224b9c);
	private final static Color DARK_BLUE = new Color(0x0f2044);
	// Gradient painter used to draw custom colors as background
	private LinearGradientColorPainter painter = new LinearGradientColorPainter();

	@Override
	public JComponent getTabComponent(GetTabComponentParameter parameters) {
		// Set the tab title and the custom style
		setText(parameters.tabText);

		setForeground(Color.WHITE);
		setFont(getFont().deriveFont(Font.BOLD, 12f));
		setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		return this; // return the custom JLabel
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Paint the tab background with a simple blue gradient.
		painter.setColorFractions(new float[]{0.0f, 1.0f});
		painter.setColors(new Color[]{LIGHT_BLUE, BLUE});
		final Rectangle r = new Rectangle(0, 0, getWidth(), getHeight());
		painter.paint((Graphics2D) g, r);

		// Draw a darker line as bottom border
		g.setColor(DARK_BLUE);
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

		super.paintComponent(g);
	}

	@Override
	public void setAccordion(JSCAccordion arg0) {
		// Not used
	}
}
