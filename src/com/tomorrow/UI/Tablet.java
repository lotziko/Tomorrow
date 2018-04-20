package com.tomorrow.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.tomorrow.UI.items.Item;
import com.tomorrow.UI.items.ItemContainer;
import com.tomorrow.UI.items.ItemFactory;
import com.tomorrow.UI.items.UiItemBox;
import com.tomorrow.UI.items.loot.UiItemLootBox;
import com.tomorrow.UI.tabPane.Tab;
import com.tomorrow.UI.tabPane.TabletTabPane;
import com.tomorrow.game.ObjectsLog;
import com.tomorrow.objects.interactives.ShipStorage;

public class Tablet {

	public static Tablet data;
	private UiItemBox storage, playerStorage;
	private UiItemLootBox loot;
	private TabletTabPane tabs;
	public Tab tabStorage, tabItems, tabLoot;
	
	public void open(boolean isInteractiveTriggered) {
		Object interactive = ObjectsLog.player.getClosestInteractive();
		tabStorage.setVisible(false);
		tabLoot.setVisible(false);
		tabItems.setVisible(true);
		setContainer(null);
		setLootContainer(null);
		
		if (interactive != null) {
			if (interactive instanceof ShipStorage) {
				tabStorage.setVisible(true);
				tabItems.setVisible(false);
				setContainer(((ShipStorage) interactive).getStorage());
				if (isInteractiveTriggered)
					UiController.openTabletMenuStorageItems();
			}
		}
		tabs.update();
	}
	
	public void setContainer(ItemContainer storage) {
		this.storage.updateContainers(storage, ObjectsLog.player.getItemContainer());
		this.playerStorage.updateContainers(ObjectsLog.player.getItemContainer(), storage);
	}
	
	public void setLootContainer(ItemContainer storage) {
		this.loot.updateContainer(storage);
	}
	
	private Tablet() {
		
		/* tablet */
		
		Table tablet = UiController.tabletMenu = new Table();
		tablet.setFillParent(true);
		tablet.align(Align.center);
		
		tabs = new TabletTabPane();
		tablet.add(tabs).padTop(7f).padBottom(0f).expand().fill();
		
		/*Table tabletBattle = UiController.tabletMenuBattle = new Table();
		tabletBattle.setFillParent(true);
		tabletBattle.align(Align.center);
		tabs.addTab(tabBattle = new Tab("battle").setContent(tabletBattle));*/
		
		Table tabletStorageItems = UiController.tabletMenuStorageItems = new Table();
		tabletStorageItems.setFillParent(true);
		tabletStorageItems.align(Align.center);
		tabs.addTab(tabStorage = new Tab("items").setContent(tabletStorageItems));
		tabStorage.setVisible(false);
		
		/* items storage */
		
		Table itemStorageTable = new Table();
		itemStorageTable.background(new NinePatchDrawable(UiFactory.tablePane));
		tabletStorageItems.add(itemStorageTable).pad(15f).pad(7f).expand().fill();
		
		ItemContainer containerPlayer = new ItemContainer(15);
		containerPlayer.addItem(ItemFactory.createItem(Item.SCRAP, 1));
		containerPlayer.addItem(ItemFactory.createItem(Item.MEDKIT, 1));
		ObjectsLog.player.setItemContainer(containerPlayer);
		
		itemStorageTable.add(playerStorage = new UiItemBox(containerPlayer, null)).pad(5f);
		itemStorageTable.add(storage = new UiItemBox(null, containerPlayer)).pad(5f);
		
		/* items look */
		
		Table tabletLookItems = UiController.tabletMenuLookItems = new Table();
		tabletLookItems.setFillParent(true);
		tabletLookItems.align(Align.center);
		tabs.addTab(tabItems = new Tab("items").setContent(tabletLookItems));
		
		Table itemLookTable = new Table();
		itemLookTable.background(new NinePatchDrawable(UiFactory.tablePane));
		tabletLookItems.add(itemLookTable).pad(15f).expand().fill();
		
		itemLookTable.add(new UiItemBox(containerPlayer, null)).pad(5f);
		
		/* items loot */
		
		Table tabletItems = UiController.tabletMenuLootItems = new Table();
		tabletItems.setFillParent(true);
		tabletItems.align(Align.center);
		tabs.addTab(tabLoot = new Tab("items").setContent(tabletItems));
		
		Table itemLootTable = new Table();
		itemLootTable.background(new NinePatchDrawable(UiFactory.tablePane));
		tabletItems.add(itemLootTable).pad(15f).expand().fill();
		itemLootTable.add(loot = new UiItemLootBox(null)).pad(5f);
		
		/* ships */
		
		/*if (Ship.player != null)
		tabletBattle.add(Ship.player).pad(15f).padRight(0f).expand().fill().width(Game.screenWidth/2 - 15);
		if (Ship.enemy != null)
		tabletBattle.add(Ship.enemy).pad(15f).padLeft(-1f).expand().fill().width(Game.screenWidth/2 - 15);*/
	}
	
	public static void initialize() {
		data = new Tablet();
	}
}
