package ch.hevs.gdx2d.demos.tilemap.advanced;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.PortableApplication;

import java.util.Map;
import java.util.TreeMap;

/**
 * Character walks on a small desert. (Tiled editor (http://www.mapeditor.org/)
 *
 * @author Alain Woeffray (woa)
 * @author Pierre-André Mudry (mui)
 * @author Marc Pignat (pim)
 */
public class DemoTileAdvanced extends PortableApplication {

	// key management
	private Map<Integer, Boolean> keyStatus = new TreeMap<Integer, Boolean>();

	// character
	private Hero hero;

	// tiles management
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private TiledMapTileLayer tiledLayer;
	private float zoom;

	@Override
	public void onInit() {

		// Create hero
		hero = new Hero(10, 20);

		// Set initial zoom
		zoom = 1;

		// init keys status
		keyStatus.put(Input.Keys.UP, false);
		keyStatus.put(Input.Keys.DOWN, false);
		keyStatus.put(Input.Keys.LEFT, false);
		keyStatus.put(Input.Keys.RIGHT, false);

		// create map
		tiledMap = new TmxMapLoader().load("data/maps/desert.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		g.clear();

		// Hero activity
		manageHero();

		// Camera follows the hero
		g.zoom(zoom);
		g.moveCamera(hero.getPosition().x, hero.getPosition().y, tiledLayer.getWidth() * tiledLayer.getTileWidth(), tiledLayer.getHeight() * tiledLayer.getTileHeight());

		// Render the tilemap
		tiledMapRenderer.setView(g.getCamera());
		tiledMapRenderer.render();

		// Draw the hero
		hero.animate(Gdx.graphics.getDeltaTime());
		hero.draw(g);

		g.drawFPS();
		g.drawSchoolLogo();
	}

	/**
	 * exemple : getTile(myPosition,0,1) get the tile over myPosition
	 *
	 * @param position
	 *            The position on map (not on screen)
	 * @param offsetX
	 *            The number of cells at right of the given position.
	 * @param offsetY
	 *            The number of cells over the given position.
	 * @return The tile around the given position | null
	 */
	private TiledMapTile getTile(Vector2 position, int offsetX, int offsetY) {
		try {
			int x = (int) (position.x / tiledLayer.getTileWidth()) + offsetX;
			int y = (int) (position.y / tiledLayer.getTileHeight()) + offsetY;

			return tiledLayer.getCell(x, y).getTile();
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * Get the "walkable" property of the given tile.
	 *
	 * @param tile
	 *            The tile to know the property
	 * @return true if the property is set to "true", false otherwise
	 */
	private boolean isWalkable(TiledMapTile tile) {
		if (tile == null)
			return false;

		Object test = tile.getProperties().get("walkable");

		return Boolean.parseBoolean(test.toString());
	}

	/**
	 * Get the "speed" property of the given tile.
	 *
	 * @param tile
	 *            The tile to know the property
	 * @return The value of the property
	 */
	private float getSpeed(TiledMapTile tile) {

		Object test = tile.getProperties().get("speed");

		return Float.parseFloat(test.toString());
	}

	/**
	 * Manage the movements of the hero using the keyboard.
	 */
	private void manageHero() {

		// Do nothing if hero is already moving
		if (!hero.isMoving()) {

			// Compute direction and next cell
			TiledMapTile nextCell = null;
			Hero.Direction goalDirection = Hero.Direction.NULL;

			if (keyStatus.get(Input.Keys.RIGHT)) {
				goalDirection = Hero.Direction.RIGHT;
				nextCell = getTile(hero.getPosition(), 1, 0);
			} else if (keyStatus.get(Input.Keys.LEFT)) {
				goalDirection = Hero.Direction.LEFT;
				nextCell = getTile(hero.getPosition(), -1, 0);
			} else if (keyStatus.get(Input.Keys.UP)) {
				goalDirection = Hero.Direction.UP;
				nextCell = getTile(hero.getPosition(), 0, 1);
			} else if (keyStatus.get(Input.Keys.DOWN)) {
				goalDirection = Hero.Direction.DOWN;
				nextCell = getTile(hero.getPosition(), 0, -1);
			}

			// Is the move valid ?
			if (isWalkable(nextCell)) {
				// Go
				hero.setSpeed(getSpeed(nextCell));
				hero.go(goalDirection);
			} else {
				// Face the wall
				hero.turn(goalDirection);
			}
		}
	}

	// Manage keyboard events
	@Override
	public void onKeyUp(int keycode) {
		super.onKeyUp(keycode);

		keyStatus.put(keycode, false);
	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);

		switch (keycode) {
		case Input.Keys.Z:
			if (zoom == 1.0) {
				zoom = .5f;
			} else if (zoom == .5) {
				zoom = 2;
			} else {
				zoom = 1;
			}
			return;

		default:
			break;
		}
		keyStatus.put(keycode, true);
	}

	public static void main(String[] args) {
		new DemoTileAdvanced();
	}
}
