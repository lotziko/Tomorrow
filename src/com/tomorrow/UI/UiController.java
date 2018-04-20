package com.tomorrow.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tomorrow.UI.tabPane.TabletTabPane;
import com.tomorrow.game.Game;

public class UiController {

	public static OrthographicCamera camera = new OrthographicCamera(Game.screenWidth, Game.screenHeight);
	public static boolean uiIsEnabled = true, uiIsTransfering = true;
	public static Float uiAlpha = 0f, shade = 0f;
	public static Table mainMenu, escapeMenu, tabletMenu, tabletMenuBattle, tabletMenuLootItems, tabletMenuStorageItems, tabletMenuLookItems;
	public static final int /*TABLET_BATTLE = 0,*/ TABLET_ITEMS = 1;
	public static int currentTabletMenu = 0;

	public static void step() {
		if (uiIsEnabled) {
			/*1.5 нужно, чтобы UI не сразу исчезал*/
			if (uiAlpha < 1.5) {
				uiAlpha += 0.1f;
			} else {
				uiIsTransfering = false;
			}
		} else {
			if (uiAlpha > 0) {
				uiAlpha -= 0.1f;
			} else {
				uiIsTransfering = false;
			}
		}
		if (shade > 0) {
			shade -= 0.5f;
		}
	}
	
	public static void distort() {
		shade = 5f;
	}
	
	public static void distort(float power) {
		shade = power;
	}
	
	public static void clearUi() {
		Game.uiStage.clear();
	}
	
	public static void clearTablet() {
		tabletMenu.clearChildren();
	}

	public static void openMainMenu() {
		if (mainMenu != null) {
			clearUi();
			Game.uiStage.addActor(mainMenu);
			uiIsEnabled = true;
			uiIsTransfering = true;
		}
	}

	public static void openEscapeMenu() {
		if (escapeMenu != null) {
			if (!Game.uiStage.getActors().contains(escapeMenu, false)) {
				clearUi();
				Game.uiStage.addActor(escapeMenu);
			}
			uiIsEnabled = true;
			uiIsTransfering = true;
		}
		Game.pause = true;
	}

	public static void openTabletMenu() {
		if (tabletMenu != null) {
			if (!Game.uiStage.getActors().contains(tabletMenu, false)) {
				clearUi();
				Game.uiStage.addActor(tabletMenu);
			}
			uiIsEnabled = true;
			uiIsTransfering = true;
			TabletTabPane pane = (TabletTabPane) tabletMenu.getChildren().get(0);
			pane.switchTab(currentTabletMenu);
		}
	}

	/*public static void openTabletMenuBattle() {
		if (tabletMenuBattle != null) {
			TabletTabPane pane = (TabletTabPane) tabletMenu.getChildren().get(0);
			pane.switchTab(currentTabletMenu = TABLET_BATTLE);
		}
	}*/
	
	public static void openTabletMenuStorageItems() {
		if (tabletMenuStorageItems != null) {
			TabletTabPane pane = (TabletTabPane) tabletMenu.getChildren().get(0);
			pane.switchTab(currentTabletMenu = TABLET_ITEMS);
		}
	}
	
	public static void openTabletMenuLootItems() {
		if (tabletMenuLootItems != null) {
			TabletTabPane pane = (TabletTabPane) tabletMenu.getChildren().get(0);
			pane.switchTab(currentTabletMenu = TABLET_ITEMS);
		}
	}
	
	public static void closeMenu() {
		uiIsEnabled = false;
		uiIsTransfering = true;
		/*if (Ship.cross != null) {
			ObjectManager.destroyObject(Ship.cross);
			Ship.cross = null;
		}*/
		Game.pause = false;
	}
	
	static {
		camera.setToOrtho(true, Game.screenWidth, Game.screenHeight);
	}
	
}
