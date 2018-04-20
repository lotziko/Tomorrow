package com.tomorrow.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tomorrow.UI.UiController;
import com.tomorrow.rooms.MenuRoom;

import blueberry.engine.objects.ObjectManager;
import blueberry.engine.sprites.Animation;
import blueberry.engine.world.Room;

public class WorldThread {

	public static void create() {
		//hideCursor();
		
		/* set Font */
		Game.render.setDefaultFont(Game.font = new BitmapFont(Gdx.files.internal("font/font.fnt"), false));
		Game.font.getData().markupEnabled = true;
		
		Room.setViews(Game.screenWidth, Game.screenHeight);
		Room.setCurrentRoom(new MenuRoom().create().fadeIn());
	}
	
	public static void step() {
		if (!Game.pause)
		Animation.step();
		UserInput.step();
		UiController.step();
		ObjectManager.step();
		Room.step();
		//if (!Game.pause)
		//Ship.step();
		Game.uiStage.act();
	}
	
	public static void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Game.render.drawBackground(0, 0, Game.screenWidth, Game.screenHeight, Color.DARK_GRAY);
		ObjectManager.drawBegin();
		if (ObjectsLog.level != null) {
			ObjectsLog.level.drawWall();
		}
		ObjectManager.draw();
		if (ObjectsLog.level != null) {
			ObjectsLog.level.drawGround();
		}
	}
	
	public static void hideCursor() {
		Cursor customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("cursor.png")), 0, 0);
		Gdx.graphics.setCursor(customCursor);
	}
	
}
