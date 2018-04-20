package com.tomorrow.UI.items.loot;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tomorrow.UI.items.ItemContainer;
import com.tomorrow.UI.items.ItemScrollPane;
import com.tomorrow.UI.items.ItemTable;
import com.tomorrow.game.ObjectsLog;

public class UiItemLootBox extends Table {

	private ItemTable container;
	
	public void updateContainer(ItemContainer container) {
		this.container.changeContainers(container, ObjectsLog.player.getItemContainer());
	}
	
	public UiItemLootBox(ItemContainer container) {
		ScrollPane pane = new ItemScrollPane(this.container = new ItemTable(container, ObjectsLog.player.getItemContainer()) {

			@Override
			public void update() {
				this.clearChildren();
				if (container != null)
				for(int i = container.size() - 1; i >= 0; i--) {
					this.add(new ItemLootField(container.getItem(i), container, target)).padBottom(0f).expandX().fillX().height(11f).row();
				}
			}
			
		});
		add(pane).size(115, 115).row();
	}
	
}
