package com.tomorrow.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import blueberry.engine.tiles.Tilelayer;
import blueberry.engine.tiles.Tilemap;

public class Map extends Widget {

	private Tilemap[] level;
	private static ShapeRenderer render = new ShapeRenderer();
	private int currLayer = 0;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		render.setProjectionMatrix(batch.getProjectionMatrix());
		render.setTransformMatrix(batch.getTransformMatrix());
		render.begin(ShapeType.Point);
		if (level != null) {
			render.setColor(Color.WHITE);
			//for(Tilemap map : level) {
				Tilelayer collide = level[currLayer].getLayer("ground");
				for(int i = 0; i < level[currLayer].getWidth(); i++) {
					int height = level[currLayer].getHeight();
					for(int j = 0; j < height; j++) {
						if (collide.getTile(i, j) != null) {
							render.point(i, height - j, 0);
						}
					}
				}
				currLayer = 1 - currLayer;
				Color color = render.getColor();
				render.setColor(color.r, color.g, color.b, 0.5f);
			//}
		}
		render.end();
		super.draw(batch, parentAlpha);
	}
	
	public Map(Tilemap[] level) {
		this.level = level;
	}

}
