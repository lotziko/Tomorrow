package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class ShipWeaponButton extends Button {

	/*public ShipGameField targetField;
	public Module target;
	private StandardShipWeapon weaponModule;
	private Drawable image;
	
	public StandardShipWeapon getWeaponModule() {
		return weaponModule;
	}
	
	public void fire() {
		if (weaponModule != null && weaponModule.ship.hp > 0 && target.getShipModule().ship.hp > 0) {
			this.weaponModule.fire();
		}
	}
	
	@Override
	public void draw(Batch batch, float a) {
		super.draw(batch, a);
		
		Drawable back = getStyle().up;
		TextureRegion bar = UiFactory.progressBar;
		
		image.draw(batch, getX(), getY(), getWidth(), getHeight() - 2);
		batch.draw(bar, getX() + back.getLeftWidth(), getY() + back.getBottomHeight(), weaponModule.getCurrentReloadTime()*(getWidth() - back.getLeftWidth() - back.getRightWidth())/weaponModule.getReloadTime(), 2);
	}
	
	public ShipWeaponButton(ShipModule weaponModule) {
		super(UiImageButtonStyle.style);
		this.weaponModule = (StandardShipWeapon)weaponModule;
		image = new TextureRegionDrawable(UiFactory.weaponIcons[(int) SimpleMath.clamp(this.weaponModule.type, 0, UiFactory.weaponIcons.length - 1)]);
	}*/

}
