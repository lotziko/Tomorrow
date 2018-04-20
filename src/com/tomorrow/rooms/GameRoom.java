package com.tomorrow.rooms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.tomorrow.UI.Map;
import com.tomorrow.UI.Tablet;
import com.tomorrow.UI.UiController;
import com.tomorrow.UI.UiTextButtonStyle;
import com.tomorrow.game.Game;
import com.tomorrow.game.ObjectsLog;
import com.tomorrow.objects.Background;
import com.tomorrow.objects.Level;
import com.tomorrow.objects.Player;
import com.tomorrow.objects.interactives.LayerDoor;
import com.tomorrow.objects.ship.Ship;
import com.tomorrow.proceduralGeneration.ProceduralGeneration;
import com.tomorrow.proceduralGeneration.ProceduralGeneration.LevelContainer;

import blueberry.engine.math.SimpleMath;
import blueberry.engine.objects.ObjectManager;
import blueberry.engine.tiles.Tile;
import blueberry.engine.tiles.Tilelayer;
import blueberry.engine.tiles.Tilemap;
import blueberry.engine.world.Room;

public class GameRoom extends Room {

	private boolean gotoMenu;

	@Override
	protected void fadedOut() {
		if (gotoMenu) {
			Room.gotoRoom(new MenuRoom().fadeIn());
			ObjectsLog.level.save(System.getProperty("user.dir") + "\\save.json");
			//Ship.save();
			ObjectsLog.player = null;
			ObjectsLog.level = null;
			Game.pause = false;
		}
		super.fadedOut();
	}

	@Override
	public void draw() {
		if (fadeAlpha > 0) {
			Game.render.drawRectangle(0, 0, Game.screenWidth, Game.screenHeight, new Color(0, 0, 0, fadeAlpha));
			//System.out.println(1);
		}
		
		super.draw();
	}
	
	public void load() {
		//load = true;
	}
	
	@Override
	public Room create() {
		ObjectManager.createObject(0, 0, Background.class);
		UiController.uiAlpha = 0f;
		UiController.uiIsEnabled = false;
		
		Table table = UiController.escapeMenu = new Table();
		table.setFillParent(true);
		table.align(Align.center);
		/*Не нужно добавлять его на сцену*/
		
		TextButton continueButton = new TextButton("continue", UiTextButtonStyle.style);
		continueButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				UiController.closeMenu();
				super.clicked(event, x, y);
			}

		});
		//table.add(continueButton).size(121, 13).row();
		
		TextButton quitButton = new TextButton("quit", UiTextButtonStyle.style);
		quitButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				fadeOut();
				gotoMenu = true;
				super.clicked(event, x, y);
			}

		});
		//table.add(quitButton).size(121, 13).row();
		
		Ship.playerShip = Ship.generate();
		LevelContainer level = ProceduralGeneration.manager.generate(25);
		Tilemap[] map = level.level;//Ship.playerShip.build(true);
		//Ship.playerShip.createPlayerField();
		//ShipModule module = Ship.playerShip.modules.get(0);
		
		for(int l = 0; l < 2; l++) {
			Tilelayer layerFirst = map[l].getLayer("objects");
			Tilelayer layerSecond = map[1 - l].getLayer("objects");
			for(int i = 0; i < map[l].getWidth(); i++) {
				for(int j = 0; j < map[l].getHeight(); j++) {
					Tile tileFirst = layerFirst.getTile(i, j);
					Tile tileSecond = layerSecond.getTile(i, j);
					if (tileFirst != null && tileSecond != null && tileFirst.getID() == 14005 && tileSecond.getID() == 14005) {
						LayerDoor door = ObjectManager.createObject(i * 8, j * 8, LayerDoor.class);
						door.setLevelLayer(l);
					}
				}
			}
		}
		//Ship.player.background(new NinePatchDrawable(UiFactory.leftShipTablePane));
		
		/*Ship.enemyShip = Ship.generateEnemy();
		Ship.enemyShip.build(false);
		Ship.enemyShip.createEnemyField();
		Ship.enemy.background(new NinePatchDrawable(UiFactory.rightShipTablePane));
		*/
		
		ObjectsLog.level = new Level();
		ObjectsLog.level.setLevel(map);
		width = map[0].getWidth() * 8;
		width = SimpleMath.clamp(width, Game.screenWidth, width);
		height = map[0].getHeight() * 8;
		height = SimpleMath.clamp(height, Game.screenHeight, height);
		ObjectsLog.player = ObjectManager.createObject(10, 0, Player.class);
		table.add(new Map(map));
		ObjectsLog.player.setPosition((level.firstRoom.getBounds().x + level.firstRoom.getBounds().width/2) * 8, (level.firstRoom.getBounds().y + level.firstRoom.getBounds().height) * 8 - 58);
		
		Tablet.initialize();
		
		return this;
	}
	
	public GameRoom(int width, int height) {
		super(width, height);
	}

}
