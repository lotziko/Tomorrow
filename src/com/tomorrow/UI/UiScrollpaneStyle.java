package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class UiScrollpaneStyle extends ScrollPaneStyle {

	public static UiScrollpaneStyle style = new UiScrollpaneStyle();
	
	private UiScrollpaneStyle() {
		/*Не нужен - есть бэкграунд у панели*/
		this.background = new NinePatchDrawable(UiFactory.uiWindowBounds);
		this.vScrollKnob = new NinePatchDrawable(UiFactory.shipModuleBounds);
		this.hScrollKnob = new NinePatchDrawable(UiFactory.shipModuleBounds);
	}
	
}
