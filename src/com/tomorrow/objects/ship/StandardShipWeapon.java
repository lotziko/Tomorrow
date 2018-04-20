package com.tomorrow.objects.ship;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tomorrow.objects.ship.cannons.GenericWeapon;

import blueberry.engine.objects.ObjectManager;

public class StandardShipWeapon extends ShipModule {

	private GenericWeapon gun;
	public int type;
	private int reloadTime = 0, currentReloadTime = 0;

	private List<ShipWeaponFireListener> listeners = new CopyOnWriteArrayList<ShipWeaponFireListener>();

	public int getCurrentReloadTime() {
		return currentReloadTime;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void addListener(ShipWeaponFireListener listener) {
		listeners.add(listener);
	}

	public void clearListeners() {
		listeners.clear();
	}

	public boolean isLoaded() {
		return (currentReloadTime == reloadTime);
	}

	public GenericWeapon getWeapon() {
		return gun;
	}

	@Override
	public void step() {
		if (currentReloadTime < reloadTime) {
			++currentReloadTime;
		} else if (listeners.size() > 0) {
			fire();
		}
	}

	public void fire() {
		if (gun != null) {
			gun.fire();
		}
		if (listeners.size() > 0) {
			for (ShipWeaponFireListener listener : listeners) {
				if (listener.fire()) {
					currentReloadTime = 0;
				}
				if (!listener.isRepeating) {
					listeners.remove(listener);
				}
			}
		} else {
			currentReloadTime = 0;
		}
	}

	@Override
	public void create() {

	}

	@Override
	public void create(boolean isPlayer) {
		if (isPlayer) {
			/* TODO: switch weapon type */
			Class<? extends GenericWeapon> weapon = WeaponData.getCannonClass(type);
			
			if (weapon != null) {
				gun = ObjectManager.createObject((x + width / 2) * 8, (y + height) * 8, weapon);
			}
		}
	}

	public StandardShipWeapon(int width, int height, int type, Ship ship) {
		super(width, height);
		this.type = type;
		reloadTime = WeaponData.getReloadTime(type);
		this.ship = ship;
	}

}