package ch.hevs.gdx2d.demos.tilemap.advanced

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.maps.tiled.*
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import java.util.TreeMap

/**
 * Character walks on a small desert. (Tiled editor (http://www.mapeditor.org/)
 *
 * @author Alain Woeffray (woa)
 * @author Pierre-André Mudry (mui)
 * @author Marc Pignat (pim)
 */
class DemoTileAdvanced : PortableApplication() {

    // key management
    private val keyStatus = TreeMap<Int, Boolean>()

    // character
    private lateinit var hero: Hero

    // tiles management
    private lateinit var tiledMap: TiledMap
    private lateinit var tiledMapRenderer: TiledMapRenderer
    private lateinit var tiledLayer: TiledMapTileLayer
    private var zoom: Float = 0.toFloat()

    override fun onInit() {

        // Create hero
        hero = Hero(10, 20)

        // Set initial zoom
        zoom = 1f

        // init keys status
        keyStatus[Input.Keys.UP] = false
        keyStatus[Input.Keys.DOWN] = false
        keyStatus[Input.Keys.LEFT] = false
        keyStatus[Input.Keys.RIGHT] = false

        // create map
        tiledMap = TmxMapLoader().load("maps/desert.tmx")
        tiledMapRenderer = OrthogonalTiledMapRenderer(tiledMap)
        tiledLayer = tiledMap.layers.get(0) as TiledMapTileLayer
    }

    override fun onGraphicRender(g: GdxGraphics) {
        g.clear()

        // Hero activity
        manageHero()

        // Camera follows the hero
        g.zoom(zoom)
        g.moveCamera(hero.position.x, hero.position.y, tiledLayer.width * tiledLayer.tileWidth, tiledLayer.height * tiledLayer.tileHeight)

        // Render the tilemap
        tiledMapRenderer.setView(g.camera)
        tiledMapRenderer.render()

        // Draw the hero
        hero.animate(Gdx.graphics.deltaTime.toDouble())
        hero.draw(g)

        g.drawFPS()
        g.drawSchoolLogo()
    }

    /**
     * exemple : getTile(myPosition,0,1) get the tile over myPosition
     *
     * @param position
     * The position on map (not on screen)
     * @param offsetX
     * The number of cells at right of the given position.
     * @param offsetY
     * The number of cells over the given position.
     * @return The tile around the given position | null
     */
    private fun getTile(position: Vector2, offsetX: Int, offsetY: Int): TiledMapTile? {
        try {
            val x = (position.x / tiledLayer.tileWidth).toInt() + offsetX
            val y = (position.y / tiledLayer.tileHeight).toInt() + offsetY

            return tiledLayer.getCell(x, y).tile
        } catch (e: Exception) {

            return null
        }

    }

    /**
     * Get the "walkable" property of the given tile.
     *
     * @param tile
     * The tile to know the property
     * @return true if the property is set to "true", false otherwise
     */
    private fun isWalkable(tile: TiledMapTile?): Boolean {
        if (tile == null)
            return false

        val test = tile.properties.get("walkable")

        return java.lang.Boolean.parseBoolean(test.toString())
    }

    /**
     * Get the "speed" property of the given tile.
     *
     * @param tile
     * The tile to know the property
     * @return The value of the property
     */
    private fun getSpeed(tile: TiledMapTile): Float {

        val test = tile.properties.get("speed")

        return java.lang.Float.parseFloat(test.toString())
    }

    /**
     * Manage the movements of the hero using the keyboard.
     */
    private fun manageHero() {

        // Do nothing if hero is already moving
        if (!hero.isMoving) {

            // Compute direction and next cell
            var nextCell: TiledMapTile? = null
            var goalDirection: Hero.Direction = Hero.Direction.NULL

            if (keyStatus[Input.Keys.RIGHT]!!) {
                goalDirection = Hero.Direction.RIGHT
                nextCell = getTile(hero.position, 1, 0)
            } else if (keyStatus[Input.Keys.LEFT]!!) {
                goalDirection = Hero.Direction.LEFT
                nextCell = getTile(hero.position, -1, 0)
            } else if (keyStatus[Input.Keys.UP]!!) {
                goalDirection = Hero.Direction.UP
                nextCell = getTile(hero.position, 0, 1)
            } else if (keyStatus[Input.Keys.DOWN]!!) {
                goalDirection = Hero.Direction.DOWN
                nextCell = getTile(hero.position, 0, -1)
            }

            // Is the move valid ?
            if (isWalkable(nextCell)) {
                // Go
                hero.speed = getSpeed(nextCell!!)
                hero.go(goalDirection)
            } else {
                // Face the wall
                hero.turn(goalDirection)
            }
        }
    }

    // Manage keyboard events
    override fun onKeyUp(keycode: Int) {
        super.onKeyUp(keycode)

        keyStatus[keycode] = false
    }

    override fun onKeyDown(keycode: Int) {
        super.onKeyDown(keycode)

        when (keycode) {
            Input.Keys.Z -> {
                if (zoom.toDouble() == 1.0) {
                    zoom = .5f
                } else if (zoom.toDouble() == .5) {
                    zoom = 2f
                } else {
                    zoom = 1f
                }
                return
            }

            else -> {
            }
        }
        keyStatus[keycode] = true
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            DemoTileAdvanced()
        }
    }
}
