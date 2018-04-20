package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class UiItemButtonStyle extends ButtonStyle {

	public static UiItemButtonStyle style = new UiItemButtonStyle();
	
	private UiItemButtonStyle() {
		up = new NinePatchDrawable(UiFactory.itemBackground);
		over = new NinePatchDrawable(UiFactory.itemHover);
	}
	
}
