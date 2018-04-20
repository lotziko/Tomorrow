package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.kotcrab.vis.ui.widget.MenuItem.MenuItemStyle;
import com.tomorrow.game.Game;

public class UiMenuItemStyle extends MenuItemStyle {

	public static UiMenuItemStyle style = new UiMenuItemStyle();
	
	private UiMenuItemStyle() {
		this.font = Game.font;
		this.over = new NinePatchDrawable(UiFactory.shipModuleBoundsLight);
		this.up = new NinePatchDrawable(UiFactory.shipModuleBounds);
	}
	
}
