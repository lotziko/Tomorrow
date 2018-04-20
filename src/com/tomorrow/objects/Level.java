package com.tomorrow.objects;

import com.google.gson.annotations.Expose;
import com.tomorrow.game.Game;
import com.tomorrow.game.ObjectsLog;

import assets.Tiles;
import blueberry.engine.tiles.Tilelayer;
import blueberry.engine.tiles.Tilemap;

public class Level {

	@Expose
	protected Tilemap[] level;
	protected String name;
	private Tilelayer ground, wall, collide;
	private int levelLayerToRender = 0;
	
	public int getLevelLayerToRender() {
		return levelLayerToRender;
	}
	
	public void setLevelLayerToRender(int levelLayerToRender) {
		this.levelLayerToRender = levelLayerToRender;
		ground = level[levelLayerToRender].getLayer("ground");
		wall = level[levelLayerToRender].getLayer("wall");
		if (ObjectsLog.player != null)
		ObjectsLog.player.changeLevelLayer(levelLayerToRender);
	}
	
	public void setLevel(Tilemap... level) {
		this.level = level;
		setLevelLayerToRender(0);
	}
	
	public int getLevelLayersCount() {
		return level.length;
	}
	
	public void drawWall() {
		Game.render.drawTilelayer(wall, 0, 0);
	}
	
	public void load(String filename) {
		//this.level = Tiles.manager.loadTilemap(filename);
	}
	
	public void save(String filename) {
		//Tilemap.save(filename, level);
	}
	
	public void drawGround() {
		Game.render.drawTilelayer(ground, 0, 0);
	}
	
	public Tilelayer collideLayer(int levelLayer) {
		return level[levelLayer].getLayer("collide");
	}
	
	public Tilelayer objectsLayer(int levelLayer) {
		return level[levelLayer].getLayer("objects");
	}
	
}

