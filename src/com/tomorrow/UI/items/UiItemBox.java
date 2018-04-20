package com.tomorrow.UI.items;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class UiItemBox extends Table {

	public ItemTable itemTable;
	public ItemStorageWeightTable counter;
	
	public void updateContainers(ItemContainer container, ItemContainer target) {
		itemTable.changeContainers(container, target);
		counter.changeContainer(container);
	}
	
	public UiItemBox(ItemContainer container, ItemContainer target) {
		ScrollPane pane = new ItemScrollPane(itemTable = new ItemTable(container, target));
		add(pane).size(115, 115).row();
		
		Table weight = new Table();
		add(weight).right();
		
		weight.add(new ItemTableSeparator()).size(1f, 5f).center().row();
		counter = new ItemStorageWeightTable(container);
		weight.add(counter).center();
	}
	
}
