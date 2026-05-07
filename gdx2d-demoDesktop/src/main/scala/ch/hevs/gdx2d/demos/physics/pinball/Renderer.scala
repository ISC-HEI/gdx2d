package ch.hevs.gdx2d.demos.physics.pinball

import java.util.LinkedList

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import com.badlogic.gdx.physics.box2d.{Body, World}
import com.badlogic.gdx.utils.Array

class Renderer {
  def draw(g: GdxGraphics, world: World, decorations: LinkedList[TemporaryDrawable]): Unit = {
    val bodies = new Array[Body](world.getBodyCount)
    world.getBodies(bodies)

    // Draw physical objects
    for (i <- 0 until bodies.size) {
      val o = bodies.get(i).getUserData
      if (o.isInstanceOf[DrawableObject]) {
        o.asInstanceOf[DrawableObject].draw(g)
      }
    }

    // Draw decorations
    val toRemove = new LinkedList[TemporaryDrawable]()
    val it = decorations.iterator()
    while (it.hasNext) {
      val d = it.next()
      d.draw(g)
      if (d.isDone) {
        toRemove.add(d)
      }
    }

    decorations.removeAll(toRemove)
  }
}
