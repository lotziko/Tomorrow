package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class UiImageButtonStyle extends ImageButtonStyle {

	public static UiImageButtonStyle style = new UiImageButtonStyle();
	
	private UiImageButtonStyle() {
		this.up = new NinePatchDrawable(UiFactory.shipModuleBounds);
		this.over = new NinePatchDrawable(UiFactory.shipModuleBoundsLight);
	}
	
}
