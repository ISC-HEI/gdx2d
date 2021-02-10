package ch.hevs.gdx2d.demos.selector

import ch.hevs.gdx2d.lib.Version

import javax.swing.*
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.net.URI

/**
 * About dialog for the demo selector.
 *
 * @author Christopher Métrailler (mei)
 * @version 1.0
 */
internal class AboutDialog
/**
 * Create the about dialog with clickable links.
 *
 * @param parent the parent component
 */
(private val parent: Component) {
    private val icon = ImageIcon(javaClass.getResource("/selector/icon64.png"))
    private val aboutPanel = JPanel()

    init {

        aboutPanel.preferredSize = Dimension(320, 200)
        aboutPanel.layout = BoxLayout(aboutPanel, BoxLayout.Y_AXIS)
        aboutPanel.add(JLabel(INFO))

        val webLabel = JLabel("Made for the ")
        addUrl(webLabel, "http://inf1.begincoding.net", "inf1 course")
        aboutPanel.add(webLabel)

        val gitLabel = JLabel()
        addUrl(gitLabel, "https://hevs-isi.github.io/gdx2d/", "https://hevs-isi.github.io/gdx2d/")
        aboutPanel.add(gitLabel)

        val version = String.format("<html><br><pre>gdx2d-core v%s, libgdx v%s<br>%s</pre></html>", Version.VERSION, com.badlogic.gdx.Version.VERSION, Version.COPY)
        val vLabel = JLabel(version)
      vLabel.background = Color.WHITE
        aboutPanel.add(vLabel)
    }

    /**
     * Show the about dialog as a [JOptionPane].
     */
    fun setVisible() {
        JOptionPane.showMessageDialog(parent, aboutPanel, TITLE, JOptionPane.INFORMATION_MESSAGE, icon)
    }

    companion object {

        private val TITLE = "About this application"

        private val INFO = ("<HTML><BODY>"
                + "<b>Gdx2d demos selector.</b><br><br>"
                + "Pierre-Andr&eacute; Mudry<br>"
                + "Christopher M&eacute;trailler<br><br></BODY></HTML>")

        private fun addUrl(website: JLabel, url: String, text: String) {
            website.text = String.format("<html>%s<a href=\"%s\">%s</a></html>", website.text, url, text)
            website.cursor = Cursor(Cursor.HAND_CURSOR)
            website.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    try {
                        Desktop.getDesktop().browse(URI(url))
                    } catch (ex: Exception) {
                        System.err.println("Cannot open $url")
                        ex.printStackTrace()
                    }

                }
            })
        }
    }
}
