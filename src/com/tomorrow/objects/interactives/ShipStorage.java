package com.tomorrow.objects.interactives;

import com.tomorrow.UI.items.ItemContainer;
import com.tomorrow.game.Game;
import com.tomorrow.game.ObjectsLog;

import assets.Shaders;
import assets.Sprites;

public class ShipStorage extends Interactive {

	private ItemContainer storage;
	
	/**
	 * @return the storage
	 */
	public ItemContainer getStorage() {
		return storage;
	}
	
	public void setStorage(ItemContainer storage) {
		this.storage = storage;
	}
	
	@Override
	public void draw() {
		if (ObjectsLog.player.getClosestInteractive() != null && ObjectsLog.player.getClosestInteractive().equals(this)) {
			Game.render.setShader(Shaders.hologramShader);
			Shaders.hologramShader.setUniformf("onePixel", Sprites.spriteShipStorageInteractive.getOnePixel());
			
			Game.render.drawSprite(Sprites.spriteShipStorageInteractive, 0, x, y);
			Game.render.resetShader();
		} else {
			Game.render.drawSprite(Sprites.spriteShipStorageInteractive, 0, x, y);
		}
		
		super.draw();
	}

	@Override
	public void create() {
		super.create();
	}

}
