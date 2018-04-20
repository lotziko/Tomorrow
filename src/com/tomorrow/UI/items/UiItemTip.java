package com.tomorrow.UI.items;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class UiItemTip extends Window {

	public Table data;
	
	public UiItemTip() {
		super("", UiItemTipStyle.style);
		setSize(70, 90);
		setVisible(false);
		this.setTouchable(Touchable.disabled);
		data = new Table();
		add(data).expand().fill();
	}

}
