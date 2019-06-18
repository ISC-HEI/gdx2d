package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.lib.interfaces.DrawableObject

interface TemporaryDrawable : DrawableObject {
    val isDone: Boolean
}
