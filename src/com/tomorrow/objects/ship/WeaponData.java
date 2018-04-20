package com.tomorrow.objects.ship;

import com.tomorrow.objects.ship.cannons.GenericWeapon;
import com.tomorrow.objects.ship.cannons.StandardShipCannon;
import com.tomorrow.objects.ship.cannons.StandardShipMissileLauncher;

public class WeaponData {

	public static final int TYPE_CANNON = 0, TYPE_MORTAR = 1, TYPE_MISSILE_LAUNCHER = 2, TYPE_SWARM_MISSILE_LAUNCHER = 3;
	
	public static Class<? extends GenericWeapon> getCannonClass(int type) {
		switch (type) {
		case TYPE_CANNON:
			return StandardShipCannon.class;
		case TYPE_MISSILE_LAUNCHER:
			return StandardShipMissileLauncher.class;
		case TYPE_SWARM_MISSILE_LAUNCHER:
			return StandardShipMissileLauncher.class;
		default:
			return null;
		}
	}
	
	public static int getReloadTime(int type) {
		switch (type) {
		case TYPE_CANNON:
			return 450;
		case TYPE_MORTAR:
			return 650;
		case TYPE_MISSILE_LAUNCHER:
			return 1000;
		case TYPE_SWARM_MISSILE_LAUNCHER:
			return 1200;
		default:
			return 0;
		}
		
	}
	
}
