package com.tomorrow.objects.ship;

public class StandardShipModule extends ShipModule {

	public StandardShipModule(int x, int y, int width, int height, Ship ship) {
		super(x, y, width, height, ship);
		tilemapName = "tilemaps/tilemapStandardShipModule_001.json";
	}

	@Override
	public void create() {
		
	}

	@Override
	public void create(boolean isPlayer) {
		
	}

}
