package com.tomorrow.UI.tabPane;

import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.tomorrow.UI.UiFactory;

public class TabStyle extends ButtonStyle {

	public static TabStyle style = new TabStyle();
	
	private TabStyle() {
		this.over = new NinePatchDrawable(UiFactory.shipModuleBounds);
		this.up = new NinePatchDrawable(UiFactory.shipModuleBoundsLight);
		this.checked = new NinePatchDrawable(UiFactory.hpBar);
	}
	
}
