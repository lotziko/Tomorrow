package com.tomorrow.objects.ship.cannons;

import com.tomorrow.game.Game;

import assets.Sprites;
import blueberry.engine.objects.Object;
import blueberry.engine.objects.ObjectManager;
import blueberry.engine.sprites.Animation;
import blueberry.engine.sprites.Sprite;

public class MissileProjectile extends Object {

	private Sprite sprite = Sprites.spriteMissileProjectile;
	private Animation animation = new Animation(0.2f, sprite.getFrameCount());
	private float speed = 1;
	
	@Override
	public void step() {
		if (y < 0) {
			ObjectManager.destroyObject(this);
		}
		if (!Game.pause) {
			speed += 0.1f + speed * 0.05f;
			this.y -= speed;
		}
		super.step();
	}
	
	@Override
	public void drawBegin() {
		Game.render.drawSprite(sprite, (int)animation.getCurrentFrame(), x, y);
		super.draw();
	}
	
	@Override
	public void create() {
		animation.resume();
		super.create();
	}
}
