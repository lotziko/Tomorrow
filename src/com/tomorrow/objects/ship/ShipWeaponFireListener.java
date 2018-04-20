package com.tomorrow.objects.ship;

public class ShipWeaponFireListener {

	protected boolean isRepeating;
	public int shootingTimer = 50, currentShootingTimer = 0;
	
	public boolean isRepeating() {
		return isRepeating;
	}
	
	public boolean fire() {return false;}
	
	public ShipWeaponFireListener(boolean isRepeating) {
		this.isRepeating = isRepeating;
	}
	
}
