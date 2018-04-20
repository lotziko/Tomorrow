package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.tomorrow.game.Game;

public class UiLabelStyle extends LabelStyle {

	public static UiLabelStyle style = new UiLabelStyle();
	
	private UiLabelStyle() {
		this.font = Game.font;
	}
	
}
