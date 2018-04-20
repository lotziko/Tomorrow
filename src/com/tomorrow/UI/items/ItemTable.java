package com.tomorrow.UI.items;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ItemTable extends Table {

	protected ItemContainer container, target;
	protected UpdateListener listener;
	
	public void update() {
		
		this.clearChildren();
		if (container != null)
		for(int i = container.size() - 1; i >= 0; i--) {
			this.add(new ItemField(container.getItem(i), container, target)).padBottom(0f).expandX().fillX().height(11f).row();
		}
	}
	
	public void changeContainers(ItemContainer newContainer, ItemContainer newTargetContainer) {
		if (newContainer != null) {
			if (container != null) {
				container.removeListener(listener);
			}
			this.container = newContainer;
			container.addListener(listener = new UpdateListener() {

				@Override
				public void handle() {
					update();
					super.handle();
				}
				
			});
		} else {
			this.container = null;
		}
		if (newTargetContainer != null) {
			this.target = newTargetContainer;
		}
		update();
	}
	
	public ItemTable(ItemContainer container, ItemContainer target) {
		if (container != null) {
			this.container = container;
			
			/* triggered update action, transport items etc. */
			
			container.addListener(listener = new UpdateListener() {

				@Override
				public void handle() {
					update();
					super.handle();
				}
				
			});
			
		}
		this.target = target;
		update();
	}
	
	
}