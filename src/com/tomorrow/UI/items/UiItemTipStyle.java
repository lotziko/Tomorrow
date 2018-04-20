package com.tomorrow.UI.items;

import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.tomorrow.UI.UiFactory;
import com.tomorrow.game.Game;

public class UiItemTipStyle extends WindowStyle {

	public static UiItemTipStyle style = new UiItemTipStyle();
	
	private UiItemTipStyle() {
		this.background = new NinePatchDrawable(UiFactory.uiWindowBounds);
		this.titleFont = Game.font;
	}
	
}
