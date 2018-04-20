package com.tomorrow.objects.interactives;

import com.tomorrow.game.Game;
import com.tomorrow.game.ObjectsLog;

import assets.Sprites;
import blueberry.engine.sprites.Sprite;

public class LayerDoor extends Interactive {

	private Sprite sprite = Sprites.spriteLayerDoor;
	private int levelLayer;
	
	public void setLevelLayer(int levelLayer) {
		this.levelLayer = levelLayer;
	}
	
	@Override
	public void draw() {
		if (levelLayer == ObjectsLog.level.getLevelLayerToRender())
			Game.render.drawSprite(sprite, 0, x, y);
		super.draw();
	}
	
	public LayerDoor() {
		
	}
}
