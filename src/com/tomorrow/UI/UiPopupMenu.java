package com.tomorrow.UI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class UiPopupMenu extends Table {

	private static final Vector2 tmpVector = new Vector2();
	private UiPopupMenuStyle style = UiPopupMenuStyle.style;
	private Table parent;
	private InputListener stageListener;
	
	public void addItem(Button item) {
		super.add(item).expandX().fillX().row();
		pack();
	}
	
	@Override
	protected void setStage (Stage stage) {
		super.setStage(stage);
		if (stage != null) stage.addListener(stageListener);
	}
	
	private void createListeners () {
		stageListener = new InputListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Vector2 beginPos = parent.localToStageCoordinates(new Vector2(0, 0));
				Vector2 endPos = parent.localToStageCoordinates(new Vector2(parent.getWidth(), parent.getHeight()));
				if (!(x > beginPos.x && x < endPos.x &&  y > beginPos.y && y < endPos.y))
				remove();
				return true;
			}
		};
	}
	
	public UiPopupMenu(Table parent) {
		this.parent = parent;
		createListeners();
		background(style.background);
	}
	
	public void showMenu (Stage stage, float x, float y) {
		setPosition(x, y - getHeight());
		if (stage.getHeight() - getY() > stage.getHeight()) setY(getY() + getHeight());
		stage.addActor(this);
	}
	
	public void showMenu (Stage stage, Actor actor) {
		Vector2 pos = actor.localToStageCoordinates(tmpVector.setZero());
		float menuY;
		if (pos.y - getHeight() <= 0) {
			menuY = pos.y + actor.getHeight() + getHeight();
		} else {
			menuY = pos.y;
		}
		showMenu(stage, pos.x, menuY);
	}
	
	public void showMenu (Stage stage, Actor actor, float xOffset, float yOffset) {
		Vector2 pos = actor.localToStageCoordinates(tmpVector.set(xOffset, yOffset));
		float menuY;
		if (pos.y - getHeight() <= 0) {
			menuY = pos.y + actor.getHeight() + getHeight();
		} else {
			menuY = pos.y;
		}
		showMenu(stage, pos.x, menuY);
	}
	
}
