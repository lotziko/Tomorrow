package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class UiButtonStyle extends ButtonStyle {

	public static UiButtonStyle style = new UiButtonStyle();
	
	private UiButtonStyle() {
		this.up = new NinePatchDrawable(UiFactory.shipModuleBounds);
		this.over = new NinePatchDrawable(UiFactory.shipModuleBoundsLight);
	}
	
}
