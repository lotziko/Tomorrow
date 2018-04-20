package com.tomorrow.objects;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.tomorrow.UI.items.ItemContainer;
import com.tomorrow.game.Game;
import com.tomorrow.game.ObjectsLog;
import com.tomorrow.objects.interactives.Interactive;

import assets.Sprites;
import assets.Tiles;
import blueberry.engine.math.SimpleMath;
import blueberry.engine.objects.Object;
import blueberry.engine.sprites.Animation;
import blueberry.engine.tiles.Tilelayer;
import blueberry.engine.world.Room;

public class Player extends Object {

	private Animation moving;
	private Animation staying;
	private boolean isMoving;
	private ItemContainer items;
	private float direction = 1f;
	private static final int NORMAL = 0, LADDER = 1;
	@Expose
	private int hp = 10, levelLayer = 0, behavior = NORMAL;
	private Interactive closestInteractive;
	private Tilelayer collideLayer, objectsLayer;
	
	public void changeLevelLayer(int layer) {
		layer = SimpleMath.clamp(layer, 0, ObjectsLog.level.getLevelLayersCount() - 1);
		collideLayer = ObjectsLog.level.collideLayer(layer);
		objectsLayer = ObjectsLog.level.objectsLayer(layer);
	}
	
	public Interactive getClosestInteractive() {
		if (closestInteractive != null)
		if (SimpleMath.distance(ObjectsLog.player.closestInteractive.getX(), ObjectsLog.player.closestInteractive.getY(), ObjectsLog.player.getX(), ObjectsLog.player.getY()) < 70) {
			return closestInteractive;
		} else {
			return null;
		}
		return null;
	}
	
	public ItemContainer getItemContainer() {
		return items;
	}
	
	public void setItemContainer(ItemContainer items) {
		this.items = items;
	}
	
	public void changeHp(int difference) {
		hp += difference;
	}
	
	public Interactive findClosestInteractive() {
		List<Interactive> interactives = ObjectsLog.interactives;
		if (interactives.size() == 0) {
			return null;
		}
		Interactive closest = interactives.get(0);
		double distance = SimpleMath.distance(closest.getX(), closest.getY(), x, y), tempDist;
		for (Interactive interactive : interactives) {
			if ((tempDist = SimpleMath.distance(interactive.getX(), interactive.getY(), x, y)) < distance && Math.abs(interactive.getY() - y) < 100) {
				distance = tempDist;
				closest = interactive;
			}
		}
		return closest;
	}
	
	@Override
	public void move(float xDelta, float yDelta) {
		switch(behavior) {
		case NORMAL:
			if (xDelta != 0) {
				//wall
				if (!collideLayer.pointCollide(x + direction * 10, y + 20)) {
					x = SimpleMath.clamp(x + xDelta, 0, Room.getCurrentRoom().width);
					isMoving = true;
					closestInteractive = findClosestInteractive();
				}
				direction = SimpleMath.sign(xDelta);
				float slope = Tiles.manager.getTileSlopeYPos(x, y + 50, collideLayer);
				if (slope != -1) {
					y = slope - 50;
				} else {
					if (!collideLayer.pointCollide(x, y + 50)) {
						y++;
					} else if (collideLayer.pointCollide(x, y + 49)) {
						y--;
					}
				}
			}
			break;
		case LADDER:
			if (yDelta != 0) {
				
			}
			break;
		}
	}

	@Override
	public void create() {
		Room.getCurrentRoom().setViewPosition(x, y);
		changeLevelLayer(levelLayer);
		super.create();
	}

	@Override
	public void step() {
		Room.getCurrentRoom().setViewPosition(SimpleMath.lerp(Room.getCurrentRoom().getViewPositionX(), x, 0.05f), SimpleMath.lerp(Room.getCurrentRoom().getViewPositionY(), y - 56, 0.1f));
		super.step();
	}

	@Override
	public void draw() {
		
		if (isMoving) {
			Game.render.drawSpriteScale(Sprites.spritePlayerMove, (int)Math.floor(moving.getCurrentFrame()), x, y, direction, 1);
			isMoving = false;
		} else {
			Game.render.drawSpriteScale(Sprites.spritePlayerStay, (int)Math.floor(staying.getCurrentFrame()), x, y, direction, 1);
		}
		/*if (closestInteractive != null) {
			Game.render.drawText(x, y - 20, x + " " + closestInteractive.getX());
		}*/
		super.draw();
	}
	
	@Override
	public void destroy() {
		moving.dispose();
		staying.dispose();
	}
	
	public Player() {
		moving = new Animation(0.2f, Sprites.spritePlayerMove.getFrameCount());
		staying = new Animation(0.01f, Sprites.spritePlayerStay.getFrameCount());
		setDepth(1);
	}

}