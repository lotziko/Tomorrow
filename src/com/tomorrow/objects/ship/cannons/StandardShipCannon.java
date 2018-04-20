package com.tomorrow.objects.ship.cannons;

import com.tomorrow.game.Game;

import assets.Sprites;
import blueberry.engine.sprites.Animation;
import blueberry.engine.sprites.Sprite;

public class StandardShipCannon extends GenericWeapon {

	private Sprite sprite = Sprites.spriteShipStandardCannon;
	private Animation animation = new Animation(0.2f, sprite.getFrameCount()) {

		@Override
		public void end() {
			pause();
			setCurrentFrame(0);
			super.end();
		}
		
	};
	
	public void fire() {
		animation.resume();
	}
	
	@Override
	public void draw() {
		Game.render.drawSprite(sprite, (int)animation.getCurrentFrame(), x, y);
		super.draw();
	}

	@Override
	public void create() {
		super.create();
	}

}
