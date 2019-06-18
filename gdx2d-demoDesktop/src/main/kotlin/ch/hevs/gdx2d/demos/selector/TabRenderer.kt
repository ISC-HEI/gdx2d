package ch.hevs.gdx2d.demos.selector

import com.javaswingcomponents.accordion.JSCAccordion
import com.javaswingcomponents.accordion.tabrenderer.AccordionTabRenderer
import com.javaswingcomponents.accordion.tabrenderer.GetTabComponentParameter
import com.javaswingcomponents.framework.painters.configurationbound.LinearGradientColorPainter

import javax.swing.*
import java.awt.*

/**
 * Custom JLabel style to display the demos categories as a tab title.
 *
 *
 * Code adapter from [this article](http://java.dzone.com/articles/java-swing-components).
 *
 * @author Christopher Métrailler (mei)
 * @version 1.0
 */
internal class TabRenderer : JLabel(), AccordionTabRenderer {
    // Gradient painter used to draw custom colors as background
    private val painter = LinearGradientColorPainter()

    override fun getTabComponent(parameters: GetTabComponentParameter): JComponent {
        // Set the tab title and the custom style
        text = parameters.tabText

        foreground = Color.WHITE
        font = font.deriveFont(Font.BOLD, 12f)
        border = BorderFactory.createEmptyBorder(0, 8, 0, 0)
        return this // return the custom JLabel
    }

    override fun paintComponent(g: Graphics) {
        // Paint the tab background with a simple blue gradient.
        painter.colorFractions = floatArrayOf(0.0f, 1.0f)
        painter.colors = arrayOf(LIGHT_BLUE, BLUE)
        val r = Rectangle(0, 0, width, height)
        painter.paint(g as Graphics2D, r)

        // Draw a darker line as bottom border
        g.setColor(DARK_BLUE)
        g.drawLine(0, height - 1, width, height - 1)

        super.paintComponent(g)
    }

    override fun setAccordion(arg0: JSCAccordion) {
        // Not used
    }

    companion object {

        private val serialVersionUID = -2561448327221140228L
        // Tab colors (blue theme)
        private val LIGHT_BLUE = Color(0x6287d2)
        private val BLUE = Color(0x224b9c)
        private val DARK_BLUE = Color(0x0f2044)
    }
}
