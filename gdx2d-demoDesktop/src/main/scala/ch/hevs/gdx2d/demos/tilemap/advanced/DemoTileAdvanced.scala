package ch.hevs.gdx2d.demos.tilemap.advanced

import scala.collection.mutable

import ch.hevs.gdx2d.desktop.DesktopApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.maps.tiled._
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2

/**
 * Character walks on a small desert. (Tiled editor (http://www.mapeditor.org/)
 *
 * @author Alain Woeffray (woa)
 * @author Pierre-André Mudry (mui)
 * @author Marc Pignat (pim)
 * @version 2.0
 */
class DemoTileAdvanced extends DesktopApplication {

  private val keyStatus = new mutable.HashMap[Int, Boolean]()

  private var hero: Hero = _
  private var tiledMap: TiledMap = _
  private var tiledMapRenderer: TiledMapRenderer = _
  private var tiledLayer: TiledMapTileLayer = _
  private var zoomFactor: Float = 1f

  override def onInit(): Unit = {
    hero = new Hero(10, 20)
    zoomFactor = 1f

    keyStatus.put(Input.Keys.UP, false)
    keyStatus.put(Input.Keys.DOWN, false)
    keyStatus.put(Input.Keys.LEFT, false)
    keyStatus.put(Input.Keys.RIGHT, false)

    tiledMap = new TmxMapLoader().load("maps/desert.tmx")
    tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap)
    tiledLayer = tiledMap.getLayers.get(0).asInstanceOf[TiledMapTileLayer]
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    manageHero()

    g.zoom(zoomFactor)
    g.moveCamera(hero.position.x, hero.position.y, tiledLayer.getWidth * tiledLayer.getTileWidth, tiledLayer.getHeight * tiledLayer.getTileHeight)

    tiledMapRenderer.setView(g.getCamera)
    tiledMapRenderer.render()

    hero.animate(Gdx.graphics.getDeltaTime.toDouble)
    hero.draw(g)

    g.drawFPS()
    g.drawSchoolLogo()
  }

  private def getTile(position: Vector2, offsetX: Int, offsetY: Int): TiledMapTile = {
    try {
      val x = (position.x / tiledLayer.getTileWidth).toInt + offsetX
      val y = (position.y / tiledLayer.getTileHeight).toInt + offsetY
      tiledLayer.getCell(x, y).getTile
    } catch {
      case _: Exception => null
    }
  }

  private def isWalkable(tile: TiledMapTile): Boolean = {
    if (tile == null) return false
    val test = tile.getProperties.get("walkable")
    if (test == null) return false
    java.lang.Boolean.parseBoolean(test.toString)
  }

  private def getSpeed(tile: TiledMapTile): Float = {
    val test = tile.getProperties.get("speed")
    if (test == null) return 1.0f
    test.toString.toFloat
  }

  private def manageHero(): Unit = {
    if (!hero.isMoving) {
      var nextCell: TiledMapTile = null
      var goalDirection: Hero.Direction.Value = Hero.Direction.NULL

      if (keyStatus.getOrElse(Input.Keys.RIGHT, false)) {
        goalDirection = Hero.Direction.RIGHT
        nextCell = getTile(hero.position, 1, 0)
      } else if (keyStatus.getOrElse(Input.Keys.LEFT, false)) {
        goalDirection = Hero.Direction.LEFT
        nextCell = getTile(hero.position, -1, 0)
      } else if (keyStatus.getOrElse(Input.Keys.UP, false)) {
        goalDirection = Hero.Direction.UP
        nextCell = getTile(hero.position, 0, 1)
      } else if (keyStatus.getOrElse(Input.Keys.DOWN, false)) {
        goalDirection = Hero.Direction.DOWN
        nextCell = getTile(hero.position, 0, -1)
      }

      if (goalDirection != Hero.Direction.NULL) {
        if (isWalkable(nextCell)) {
          hero.speed = getSpeed(nextCell)
          hero.go(goalDirection)
        } else {
          hero.turn(goalDirection)
        }
      }
    }
  }

  override def onKeyUp(keycode: Int): Unit = {
    super.onKeyUp(keycode)
    keyStatus.put(keycode, false)
  }

  override def onKeyDown(keycode: Int): Unit = {
    super.onKeyDown(keycode)
    keycode match {
      case Input.Keys.Z =>
        if (zoomFactor == 1.0f) zoomFactor = .5f
        else if (zoomFactor == .5f) zoomFactor = 2f
        else zoomFactor = 1f
      case _ =>
        keyStatus.put(keycode, true)
    }
  }

  override def onDispose(): Unit = {
    super.onDispose()
    tiledMap.dispose()
  }
}

object DemoTileAdvanced {
  def main(args: Array[String]): Unit = {
    new DemoTileAdvanced().launch()
  }
}
