package assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import blueberry.engine.tiles.Tile;
import blueberry.engine.tiles.Tilelayer;
import blueberry.engine.tiles.Tilemap;

public class Tiles {

	public static Tiles manager;
	private static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("tiles.atlas"));
	private static HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();

	public Tile getTile(int ID) {
		return tiles.get(ID);
	}
	
	/**
	 * 
	 * @param filename (from assets folder)
	 * @return
	 */
	
	public Tilemap loadTilemap(String filename) {
		return Tilemap.load(filename, tiles);
	}
	
	public static void initialize() {
		manager = new Tiles();
	}
	
	public static void dispose() {
		atlas.dispose();
	}
	
	public float getTileSlopeYPos(float x, float y, Tilelayer layer) {
		int xCellPos = Math.floorDiv((int)x, layer.getCellWidth());
		int yCellPos = Math.floorDiv((int)y, layer.getCellHeight());
		
		int xMod = Math.floorMod((int)x, layer.getCellWidth());
		int newY = Math.floorDiv((int)y, layer.getCellHeight())*8;
		
		Tile tile = layer.getTile(xCellPos, yCellPos);
		if (tile != null)
		switch (tile.getID()) {
		case 11601:
			return newY + 7 - xMod/4;
		case 11602:
			return newY + 5 - xMod/4;
		case 11603:
			return newY + 3 - xMod/4;
		case 11604:
			return newY + 1 - xMod/4;
			
		case 11605:
			return newY + 1 - (1 - xMod/4);
		case 11606:
			return newY + 3 - (1 - xMod/4);
		case 11607:
			return newY + 5 - (1 - xMod/4);
		case 11608:
			return newY + 7 - (1 - xMod/4);
		default:
			break;
		}
		
		return -1;
	}
	
	public List<Tile> getTiles(String tileset) {
		List<Tile> result = new ArrayList<Tile>();
		for(Tile tile : tiles.values()) {
			if (tile.getTileset().equals(tileset)) {
				result.add(tile);
			}
		}
		return result;
	}
	
	private Tiles() {
		for(AtlasRegion region : atlas.getRegions()) {
			String[] words = region.name.split("/");
			String name = words[words.length - 1];
			int ID = Integer.parseInt(name);
			String tileset = words[words.length - 2];
			region.flip(false, true);
			tiles.put(ID, new Tile(ID, region, tileset));
		}
	}
}
