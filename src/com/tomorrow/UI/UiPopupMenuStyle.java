package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.kotcrab.vis.ui.widget.PopupMenu.PopupMenuStyle;

public class UiPopupMenuStyle extends PopupMenuStyle {

	public static UiPopupMenuStyle style = new UiPopupMenuStyle();
	
	private UiPopupMenuStyle() {
		this.background = new NinePatchDrawable(UiFactory.uiWindowBounds);
		this.border = new NinePatchDrawable(UiFactory.uiWindowBounds);
	}
	
}
