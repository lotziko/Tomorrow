package com.tomorrow.objects.ship.cannons;

import com.tomorrow.game.Game;

import assets.Sprites;
import blueberry.engine.objects.ObjectManager;
import blueberry.engine.sprites.Sprite;

public class StandardShipMissileLauncher extends GenericWeapon {

	private Sprite sprite = Sprites.spriteShipStandardMissileLauncher;
	
	@Override
	public void draw() {
		Game.render.drawSprite(sprite, 0, x, y);
		super.draw();
	}
	
	@Override
	public void fire() {
		ObjectManager.createObject((int)x, (int)y + 70, MissileProjectile.class);
	}

}
