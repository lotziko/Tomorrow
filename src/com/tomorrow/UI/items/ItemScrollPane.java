package com.tomorrow.UI.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Align;
import com.tomorrow.UI.UiScrollpaneStyle;

public class ItemScrollPane extends ScrollPane {

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		//Gdx.gl.glEnable(GL20.GL_BLEND);
		//batch.enableBlending();
		
		super.draw(batch, parentAlpha);
		//batch.disableBlending();
		//Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	public ItemScrollPane(ItemTable container) {
		super(container);
		container.align(Align.top);
		setStyle(UiScrollpaneStyle.style);
		setOverscroll(false, false);
		setFadeScrollBars(false);
	}

}
