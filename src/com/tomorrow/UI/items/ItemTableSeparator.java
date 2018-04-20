package com.tomorrow.UI.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tomorrow.UI.UiFactory;

public class ItemTableSeparator extends Widget {

	private Drawable texture = new TextureRegionDrawable(UiFactory.progressBar);
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		float x = getX();
		float y = getY();
		float width = getWidth();
		float height = getHeight();
		
		texture.draw(batch, x, y, width, height);
		
	}

}
