package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class UiShipHpProgressBarStyle extends ProgressBarStyle {

	public static UiShipHpProgressBarStyle style = new UiShipHpProgressBarStyle();
	
	private UiShipHpProgressBarStyle() {
		this.background = new NinePatchDrawable(UiFactory.hpBar);
		this.knob = new TiledDrawable(UiFactory.hpIcon);
	}
	
}
