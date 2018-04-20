package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.tomorrow.game.Game;

public class UiWindowStyle extends WindowStyle {

	public static UiWindowStyle style = new UiWindowStyle();
	
	private UiWindowStyle() {
		this.titleFont = Game.font;
		this.background = new NinePatchDrawable(UiFactory.shipModuleBounds);
	}
	
}
