package com.tomorrow.objects.ship;

import com.google.gson.annotations.Expose;

public abstract class ShipModule {
	
	@Expose
	public int x, y, width, height, hp, ID;
	@Expose
	public int left = -1, right = -1, top = -1, bottom = -1;
	@Expose
	public String tilemapName;

	public Ship ship;
	
	public ShipModule insertLeft(ShipModule module) {
		module.x = x - module.width + 1;
		module.y = y;
		left = module.ID;
		module.right = this.ID;
		return module;
	}
	
	public ShipModule insertRight(ShipModule module) {
		module.x = x + width - 1;
		module.y = y;
		right = module.ID;
		module.left = this.ID;
		return module;
	}
	
	public ShipModule insertTop(ShipModule module) {
		module.x = x + (this.width - module.width)/2;
		module.y = y - module.height - 1;
		top = module.ID;
		module.bottom = this.ID;
		return module;
	}
	
	public ShipModule insertBottom(ShipModule module) {
		module.x = x + (this.width - module.width)/2;
		module.y = y + module.height + 1;
		bottom = module.ID;
		module.top = this.ID;
		return module;
	}
	
	public void step() {};
	
	public void setToRight() {
		tilemapName = tilemapName.replace("_", "Right_");
	}
	
	public void setToLeft() {
		tilemapName = tilemapName.replace("_", "Left_");
	}
	
	public void setToLeftRight() {
		tilemapName = tilemapName.replace("_", "LeftRight_");
	}
	
	public ShipModule(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	//public void damage(int damage) {
		/* TODO: correction to module type */
		//ship.damage(damage);
	//}
	
	public abstract void create();
	
	public abstract void create(boolean isPlayer);
	
	public ShipModule(int x, int y, int width, int height, Ship ship) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.ship = ship;
		ID = ship.modules.size();
	}
}
