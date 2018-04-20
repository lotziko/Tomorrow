package com.tomorrow.game;

import com.badlogic.gdx.Input.Keys;
import com.tomorrow.UI.Tablet;
import com.tomorrow.UI.UiController;
import com.tomorrow.objects.interactives.Interactive;
import com.tomorrow.objects.interactives.LayerDoor;

import blueberry.engine.input.Input;
import blueberry.engine.render.Screenshot;

public class UserInput {

	public static boolean isNetwork, moveLeft, moveRight;
	private static int RIGHT_KEY = Keys.D, LEFT_KEY = Keys.A, ESCAPE_KEY = Keys.ESCAPE, OPEN_TABLET = Keys.F,
			INTERACT = Keys.E, /*PAUSE_BATTLE = Keys.SPACE,*/ SCREENSHOT = Keys.F12;

	public static void step() {
		if (isNetwork) {

			/* Code for a server */

		} else {
			if (ObjectsLog.player != null) {
				if (!UiController.uiIsEnabled) {
					moveRight = Input.isKeyDown(RIGHT_KEY);
					moveLeft = Input.isKeyDown(LEFT_KEY);
					ObjectsLog.player.move((moveRight ? 2f : 0f) - (moveLeft ? 2f : 0f), 0f);
				} else {
					/*if (UiController.currentTabletMenu == UiController.TABLET_BATTLE) {
						if (Input.isKeyPressed(PAUSE_BATTLE)) {
							Game.pause = !Game.pause;
						}
					}*/
				}
				if (Input.isKeyPressed(ESCAPE_KEY) && !UiController.uiIsTransfering) {
					UiController.distort();

					if (UiController.uiIsEnabled) {
						UiController.closeMenu();
					} else {
						UiController.openEscapeMenu();
					}
				} else if (Input.isKeyPressed(OPEN_TABLET) && !UiController.uiIsTransfering) {
					UiController.distort();
					if (UiController.uiIsEnabled) {
						UiController.closeMenu();
					} else {
						UiController.openTabletMenu();
						Tablet.data.open(false);
						// UiController.openTabletMenuBattle();
					}
				} else if (Input.isKeyPressed(INTERACT)) {
					if (!UiController.uiIsEnabled) {
						Interactive interactive;
						if ((interactive = ObjectsLog.player.getClosestInteractive()) != null) {
							if (interactive instanceof LayerDoor) {
								ObjectsLog.level.setLevelLayerToRender(1 - ObjectsLog.level.getLevelLayerToRender());
							} else {
								UiController.distort();
								UiController.openTabletMenu();
								Tablet.data.open(true);
							}
						}
						/* TODO: switch interactive type */
						
						//
						/*if (interactive instanceof ShipStorage) {
							UiController.openTabletMenuStorageItems();
							Tablet.data.setLootContainer(((ShipStorage) interactive).getStorage());
							Tablet.data.
						} else {
							Tablet.data.setContainer(null);
						}*/
					}
				}
			}
			if (Input.isKeyPressed(SCREENSHOT)) {
				Screenshot.data.take();
			}
		}
	}

}
