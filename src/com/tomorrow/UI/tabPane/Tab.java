package com.tomorrow.UI.tabPane;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import assets.UI;

public class Tab extends Button {
	private Table content;
	
	public Table getContent() {
		return content;
	}
	
	public Tab setContent(Table content) {
		this.content = content;
		return this;
	}
	
	public Tab(String styleName) {
		ButtonStyle style = new ButtonStyle();
		style.over = new TextureRegionDrawable(UI.getTextureRegion("buttons/" + styleName + "_over"));
		style.up = new TextureRegionDrawable(UI.getTextureRegion("buttons/" + styleName + "_up"));
		style.checked = new TextureRegionDrawable(UI.getTextureRegion("buttons/" + styleName + "_checked"));
		this.setStyle(style);
	}
	
}