package ch.hevs.gdx2d.demos

import ch.hevs.gdx2d.demos.selector.DemoSelectorGUI

import javax.swing.*
import javax.swing.UIManager.LookAndFeelInfo

/**
 * A demo selector class, most of the code taken from Libgdx own demo selector.
 *
 * @author Pierre-André Mudry (mui)
 * @author Christopher Métrailler (mei)
 * @version 2.0
 */

fun main(argv: Array<String>) {
    try {
        for (info in UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus" == info.name) {
                UIManager.setLookAndFeel(info.className)
                break
            }
        }
    } catch (e: Exception) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
    }

    DemoSelectorGUI()
}
