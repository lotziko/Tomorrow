package com.tomorrow.objects;

import com.badlogic.gdx.Input.Buttons;
import com.tomorrow.UI.ShipWeaponButton;
import com.tomorrow.UI.UiFactory;
import com.tomorrow.game.Game;

import blueberry.engine.input.Input;
import blueberry.engine.objects.Object;
import blueberry.engine.objects.ObjectManager;

public class ShipTargetCross extends Object {

	public ShipWeaponButton button;
	
	@Override
	public void drawEnd() {
		if (Input.isMousePressed(Buttons.LEFT)) {
			clicked();
		}
		Game.render.drawTextureRegion(UiFactory.shipTargetCross, Input.getMouseX() - 3, Input.getMouseY() - 3, 7, 7);
		super.drawGUI();
	}
	
	public void clicked() {}
	
	public ShipTargetCross() {
		ObjectManager.createObject(0, 0, ShipTargetCross.class, this);
	}
	
}
