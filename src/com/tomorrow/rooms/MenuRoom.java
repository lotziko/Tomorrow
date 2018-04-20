package com.tomorrow.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.tomorrow.UI.UiController;
import com.tomorrow.UI.UiTextButtonStyle;
import com.tomorrow.game.Game;

import blueberry.engine.world.Room;

public class MenuRoom extends Room {

	private int action;
	private final int NEW_GAME = 0, CONTINUE = 1, QUIT = 2;

	@Override
	protected void fadedOut() {
		switch (action) {
		case NEW_GAME:
			Room.gotoRoom(new GameRoom(1200, 512).fadeIn());
			break;
		case CONTINUE:
			
			break;
		case QUIT:
			Gdx.app.exit();
			break;
		}
		super.fadedOut();
	}

	@Override
	public void draw() {
		if (fadeAlpha > 0)
			Game.render.drawRectangle(0, 0, Game.screenWidth, Game.screenHeight, new Color(0, 0, 0, fadeAlpha));
		super.draw();
	}

	@Override
	public Room create() {
		UiController.mainMenu = new Table();
		Table table = UiController.mainMenu;
		table.setFillParent(true);
		table.align(Align.center);
		
		//ScrollPane pane = new ScrollPane(new ItemTable(container), UiScrollpaneStyle.style);//= new ItemScrolltable(new ItemTable(container));
		//pane.setOverscroll(false, false);
		//pane.setBounds(0, 0, 100, 100);
		
		TextButton newGameButton = new TextButton("new game", UiTextButtonStyle.style);
		newGameButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				fadeOut();
				action = NEW_GAME;
				super.clicked(event, x, y);
			}
			
		});
		table.add(newGameButton).size(121, 13).row();
		
		TextButton continueButton = new TextButton("continue", UiTextButtonStyle.style);
		table.add(continueButton).size(121, 13).row();
		
		TextButton quitButton = new TextButton("quit", UiTextButtonStyle.style);
		quitButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				fadeOut();
				action = QUIT;
				super.clicked(event, x, y);
			}
			
		});
		table.add(quitButton).size(121, 13).row();
		
		UiController.openMainMenu();
		return this;
	}

	public MenuRoom() {
		super(480, 270);
	}

}
