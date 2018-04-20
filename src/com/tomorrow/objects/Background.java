package com.tomorrow.objects;

import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.tomorrow.game.Game;

import assets.Sprites;
import blueberry.engine.objects.Object;
import blueberry.engine.world.Room;

public class Background extends Object {

	private TiledDrawable drawable = new TiledDrawable(Sprites.spriteBackground.getFrames()[0]);
	private int yOrigin = Sprites.spriteBackground.getyOrigin() + 40;
	private int height = Sprites.spriteBackground.getFrames()[0].getRegionHeight();
	private int width = Sprites.spriteBackground.getFrames()[0].getRegionWidth();
	private float xCounter;
	
	@Override
	public void drawBegin() {
		if (!Game.pause)
		if (xCounter < width) {
			xCounter += 0.5;
		} else {
			xCounter = 0;
		}
		float xOffset = Room.getCurrentRoom().getWorldViewPositionX() * 0.2f - xCounter;
		drawable.draw(Game.batch, xOffset, Game.camera.position.y - yOrigin, Room.getCurrentRoom().width + width, height);
		super.drawBegin();
	}
	
}
