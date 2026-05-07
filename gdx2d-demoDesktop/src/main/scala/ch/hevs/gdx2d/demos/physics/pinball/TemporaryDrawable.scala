package ch.hevs.gdx2d.demos.physics.pinball

import ch.hevs.gdx2d.lib.GdxGraphics

trait TemporaryDrawable {
  def draw(g: GdxGraphics): Unit
  def isDone: Boolean
}
