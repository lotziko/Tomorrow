package com.tomorrow.UI.items;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.tomorrow.UI.UiFactory;
import com.tomorrow.UI.UiLabelStyle;

public class ItemStorageWeightTable extends Table {
	
	private Label text;
	private ItemContainer container;
	private UpdateListener listener;
	
	private void update() {
		if (container != null) {
			text.setText(container.getWeight() + "/" + container.getMaxWeight());
		} else {
			text.setText("null");
		}
	}
	
	public void changeContainer(ItemContainer container) {
		if (this.container != null) {
			this.container.removeListener(listener);
		}
		this.container = container;
		update();
		if (container != null) {
			this.container.addListener(listener = new UpdateListener() {
				
				@Override
				public void handle() {
					update();
				}
				
			});
		}
	}
	
	public ItemStorageWeightTable(ItemContainer container) {
		background(new NinePatchDrawable(UiFactory.uiWindowBounds));
		this.container = container;
		add(new Image(UiFactory.cratesIcon)).space(2f);
		text = new Label("null", UiLabelStyle.style);
		add(text);
		if (container != null) {
			update();
			container.addListener(listener = new UpdateListener() {

				@Override
				public void handle() {
					update();
				}
				
			});
		}
	}
	
}
