package ch.hevs.gdx2d.demos.selector

import ch.hevs.gdx2d.demos.simple.DemoCircles
import ch.hevs.gdx2d.desktop.Game2D
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.Version
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas
import com.javaswingcomponents.accordion.JSCAccordion
import com.javaswingcomponents.accordion.TabOrientation
import com.javaswingcomponents.accordion.plaf.steel.SteelAccordionUI

import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener
import java.awt.*
import java.awt.event.*
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * A demo selector class, most of the code taken from Libgdx own demo selector.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 2.1
 */
class DemoSelectorGUI @Throws(Exception::class)
constructor() : JFrame("Demos gdx2d " + Version.VERSION) {

    private val canvasList = ArrayList<LwjglAWTCanvas>()

    private val container = JPanel()

    init {

        // TODO: configuration this way is ugly...
        PortableApplication.CreateLwjglApplication = false

        isResizable = false
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane.layout = BorderLayout()
        componentOrientation = ComponentOrientation.LEFT_TO_RIGHT

        // Populate the window
        contentPane.add(TestList(), BorderLayout.WEST)

        // Add the starting demo
        val canvas = LwjglAWTCanvas(Game2D(DemoCircles()).withoutController())
        canvas.canvas.setSize(500, 500)
        container.add(canvas.canvas)
        canvasList.add(canvas)
        contentPane.add(container, BorderLayout.EAST)

        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }

    /**
     * Display all available demos classed in categories.
     */
    private inner class TestList internal constructor() : JPanel() {

        private lateinit var demosMap: MutableMap<String, SelectedDemos.DemoDescriptor>

        /* GUI components */
        private val accordion = JSCAccordion()
        private val btRun = RunButton()
        private val paneComments = JTextPane()

        /* GUI preferences */
        private var selectedDemoName : String? = null

        init {
            layout = BorderLayout()
            setIcon()
            createMenus()
            setupAccordeon()
            preferredSize = Dimension(500, 500)
            border = EmptyBorder(5, 5, 5, 5)

            add(accordion, BorderLayout.CENTER)

            val p = JPanel()
            p.background = Color(0xffffff)
            p.layout = BoxLayout(p, BoxLayout.X_AXIS)

            p.add(Box.createRigidArea(Dimension(10, 0)))
            p.add(btRun)
            p.add(Box.createRigidArea(Dimension(10, 0)))
            p.add(paneComments)
            add(p, BorderLayout.SOUTH)
        }

        private fun setIcon() {
            val icons = ArrayList<Image>()
            icons.add(ImageIcon(javaClass.getResource("/selector/icon16.png")).image)
            icons.add(ImageIcon(javaClass.getResource("/selector/icon32.png")).image)
            icons.add(ImageIcon(javaClass.getResource("/selector/icon64.png")).image)
          iconImages = icons
        }

        private fun createMenus() {
            val bar = JMenuBar()
            val about = JMenu("Help")
            val it = JMenuItem("About")
            it.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK)
            it.addActionListener { AboutDialog(this@DemoSelectorGUI).setVisible() }
            about.add(it)

            val file = JMenu("File")
            file.mnemonic = KeyEvent.VK_F
            val q = JMenuItem("Quit")
            q.mnemonic = KeyEvent.VK_C
            q.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK)
            q.addActionListener { System.exit(0) }
            file.add(q)

            bar.add(file)
            bar.add(Box.createHorizontalGlue())
            bar.add(about)
            jMenuBar = bar
        }

        private fun setupAccordeon() {
            accordion.tabOrientation = TabOrientation.VERTICAL
            accordion.tabHeight = 25
            accordion.isDrawShadow = false
            accordion.isFocusable = false

            // Remove padding
            val steelAccordionUI = accordion.ui as SteelAccordionUI
            steelAccordionUI.horizontalBackgroundPadding = 0
            steelAccordionUI.verticalBackgroundPadding = 0
            steelAccordionUI.horizontalTabPadding = 0
            steelAccordionUI.verticalTabPadding = 0
            steelAccordionUI.tabPadding = 0

            accordion.verticalAccordionTabRenderer = TabRenderer()

            registerDemos() // Load tabs

            // Open the last selected tab (if any) or the first one
            accordion.selectedIndex = 0
        }

        private fun registerDemos() {
            demosMap = LinkedHashMap()

            var demoCounter = 1

            // Read all categories
            for (catDemos in SelectedDemos.list) {
                val catName = catDemos.name

                val l = ArrayList<String>()
                // Read all demos in the category
                for (currentDemo in catDemos.descs) {
                    val name = String.format("%02d. %s", demoCounter, currentDemo.name)
                    val clazz = currentDemo.clazz
                    val descText = currentDemo.desc
                    val d = SelectedDemos.DemoDescriptor(name, clazz, descText)
                    demosMap[name] = d
                    l.add(name)
                    demoCounter++
                }

                // Add the current tab with demo list
                val dl = DemoList(l.toTypedArray<String>())
                ToolTipManager.sharedInstance().registerComponent(dl)
                accordion.addTab(catName, dl)
            }
        }

        internal inner class RunButton : JButton("Run!"), ActionListener {
            init {
                addActionListener(this)
            }

            override fun actionPerformed(e: ActionEvent) {
                // Stop the running canvas
                canvasList[0].stop()

                SwingUtilities.invokeLater {
                    try {
                        val g = Game2D(demosMap.get(selectedDemoName)!!.clazz.newInstance() as PortableApplication).withoutController()
                        // Create a new one
                        val canvas = LwjglAWTCanvas(g)
                        canvas.canvas.setSize(500, 500)

                        // Remove the old canvas from the list
                        canvasList.clear()
                        canvasList.add(0, canvas)
                        container.add(canvas.canvas)
                        container.remove(0)
                        pack()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        internal inner class DemoList(demos: Array<String>) : JList<String>(demos) {

            init {

                val commentDimension = Dimension(400, 55)
                paneComments.minimumSize = commentDimension
                paneComments.maximumSize = commentDimension
                paneComments.preferredSize = commentDimension
                paneComments.text = "Welcome to gdx2d.\nRunning " + Version.print()
                paneComments.background = Color(0xF5F5F5)
                paneComments.isEditable = false

                val m = DefaultListSelectionModel()
                m.selectionMode = ListSelectionModel.SINGLE_SELECTION
                m.isLeadAnchorNotificationEnabled = false
                selectionModel = m

                setSelectedValue(selectedDemoName, true)
                isFocusable = true
                requestFocus()

                addListSelectionListener {
                    selectedDemoName = selectedValue as String
                    paneComments.text = demosMap[selectedDemoName!!]!!.desc
                }

                addMouseListener(object : MouseAdapter() {
                    override fun mouseClicked(event: MouseEvent?) {
                        if (event!!.clickCount == 2)
                            btRun.doClick()
                    }
                })

                // Add a Mouse Motion adapter to display a tooltip on list elements
                addMouseMotionListener(object : MouseMotionAdapter() {
                    override fun mouseMoved(e: MouseEvent?) {
                        val model = model
                        val index = locationToIndex(e!!.point)
                        if (index >= 0) {
                            // Display the demo description as tooltip
                            val desc = demosMap.get(model.getElementAt(index))!!.desc
                            toolTipText = null
                            toolTipText = desc
                            paneComments.text = desc
                        }
                    }
                })

                addKeyListener(object : KeyAdapter() {
                    override fun keyPressed(e: KeyEvent?) {
                        if (e!!.keyCode == KeyEvent.VK_ENTER)
                            btRun.doClick()
                    }
                })
            }
        }
    }
}
