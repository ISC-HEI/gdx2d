package ch.hevs.gdx2d.demos.physics.pinball

import java.util.LinkedList

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array


class Renderer {
    fun draw(g: GdxGraphics, world: World, decorations: LinkedList<TemporaryDrawable>) {
        val bodies = Array<Body>(world.bodyCount)
        world.getBodies(bodies)

        // Draw physical objects
        for (i in 0 until bodies.size) {
            val o = bodies.get(i).userData

            if (o is DrawableObject) {
                o.draw(g)
            }
        }

        // Draw decorations
        val toRemove = LinkedList<TemporaryDrawable>()
        for (d in decorations) {
            d.draw(g)
            if (d.isDone) {
                toRemove.add(d)
            }
        }

        // Remove finished decorations
        decorations.removeAll(toRemove)
    }
}
