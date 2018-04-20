package com.tomorrow.UI.tabPane;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tomorrow.UI.UiController;

public class TabletTabPane extends Table {

	private Table tabs = new Table();
	private Tab lastClicked;
	private Table content = new Table();
	private ButtonGroup<Tab> tabsGroup = new ButtonGroup<Tab>();

	public void update() {
		tabs.clearChildren();
		tabs.getCells().clear();
		for(Tab tab : tabsGroup.getButtons()) {
			if (tab.isVisible()) {
				tabs.add(tab).size(13f).pad(1f);
			}
		}
		if(!tabs.getChildren().contains(tabsGroup.getChecked(), false)) {
			switchTab(0);
		}
	}
	
	public void addTab(final Tab tab) {
		tabs.add(tab).size(13f).pad(1f);
		tab.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				switchTab();
				super.clicked(event, x, y);
			}

		});
		tabsGroup.add(tab);
		if (tabsGroup.getButtons().size == 1) {
			switchTab();
		}
	}

	public void switchTab() {
		content.clearChildren();
		Tab activeTab = tabsGroup.getChecked();
		if (lastClicked != activeTab) {
			UiController.distort();
		}
		if (activeTab != null) {
			Table content = activeTab.getContent();
			if (content != null) {
				this.content.add(content).expand().fill();
			}
		}
		lastClicked = activeTab;
	}
	
	public void switchTab(int key) {
		content.clearChildren();
		Tab activeTab = tabsGroup.getButtons().get(key);
		if (lastClicked != activeTab) {
			UiController.distort();
		}
		activeTab.getClickListener().clicked(null, 0, 0);
		if (activeTab != null) {
			Table content = activeTab.getContent();
			if (content != null) {
				this.content.add(content).expand().fill();
			}
		}
		lastClicked = activeTab;
	}

	public TabletTabPane() {
		tabsGroup.setMaxCheckCount(1);
		tabsGroup.setMinCheckCount(1);
		add(tabs).center().row();
		add(content).expand().fill();
	}

}
