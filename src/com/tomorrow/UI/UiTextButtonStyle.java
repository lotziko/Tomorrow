package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.tomorrow.game.Game;

public class UiTextButtonStyle extends TextButtonStyle {

	public static UiTextButtonStyle style = new UiTextButtonStyle();
	
	private UiTextButtonStyle() {
		this.over = new NinePatchDrawable(UiFactory.standardButtonHover);
		this.font = Game.font;
	}
	
}
