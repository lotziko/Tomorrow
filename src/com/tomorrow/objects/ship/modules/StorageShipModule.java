package com.tomorrow.objects.ship.modules;

import com.tomorrow.objects.interactives.ShipStorage;
import com.tomorrow.objects.ship.Ship;
import com.tomorrow.objects.ship.StandardShipModule;

import blueberry.engine.objects.ObjectManager;

public class StorageShipModule extends StandardShipModule {

	@Override
	public void create(boolean isPlayer) {
		if (isPlayer) {
			ObjectManager.createObject((x + width/2) * 8, (y + height - 1) * 8, ShipStorage.class).setStorage(ship.getStorage());;
		}
		super.create();
	}

	public StorageShipModule(int x, int y, int width, int height, Ship ship) {
		super(x, y, width, height, ship);
	}

}
