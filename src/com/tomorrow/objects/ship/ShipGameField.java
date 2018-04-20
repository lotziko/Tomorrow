package com.tomorrow.objects.ship;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ShipGameField extends Table {

	/*private float mouseX, mouseY, yOffset;
	private Rectangle scissors = new Rectangle();
	private CopyOnWriteArrayList<Projectile> projectiles = new CopyOnWriteArrayList<Projectile>();
	private CopyOnWriteArrayList<Explosion> explosions = new CopyOnWriteArrayList<Explosion>();
	private CopyOnWriteArrayList<Decoration> decorations = new CopyOnWriteArrayList<Decoration>();
	private CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	private CopyOnWriteArrayList<TextMessage> messages = new CopyOnWriteArrayList<TextMessage>();

	public void fire(Cannon cannon, ShipGameField targetField, Module target) {
		switch (cannon.index) {
		case WeaponData.TYPE_CANNON:
			// for (int i = 0; i < 75; i+=25) {
			createProjectile(Projectile.CANNON, cannon.bounds.x - 2, cannon.bounds.y + 5, cannon.bounds.x + 300 * cannon.xScale, cannon.bounds.y - 1, 0, false);
			targetField.createProjectile(Projectile.CANNON, target.bounds.x - 200 * cannon.xScale, target.bounds.y - 5, target.bounds.x + target.bounds.width / 2, target.bounds.y + target.bounds.height / 2, 300, true);
			// }
			break;
		case WeaponData.TYPE_MORTAR:
			createProjectile(Projectile.CANNON, cannon.bounds.x + 6 * cannon.xScale, cannon.bounds.y, cannon.bounds.x + 300 * cannon.xScale, cannon.bounds.y - 305, 0, false);
			targetField.createProjectile(Projectile.CANNON, target.bounds.x - 200 * cannon.xScale, target.bounds.y - 200, target.bounds.x + target.bounds.width / 2, target.bounds.y + target.bounds.height / 2, 200, true);
			break;
		case WeaponData.TYPE_MISSILE_LAUNCHER:
			createProjectile(Projectile.MISSILE, cannon.bounds.x, cannon.bounds.y - 5, cannon.bounds.x, cannon.bounds.y - 300, 0, false);
			targetField.createProjectile(Projectile.MISSILE, target.bounds.x + target.bounds.width / 2, target.bounds.y - 200, target.bounds.x + target.bounds.width / 2, target.bounds.y + target.bounds.height / 2, 300, true);
			break;
		case WeaponData.TYPE_SWARM_MISSILE_LAUNCHER:
			for(int i = 0; i < 5; i++) {
				createProjectile(Projectile.SWARM_MISSILE, cannon.bounds.x, cannon.bounds.y - 5, cannon.bounds.x, cannon.bounds.y - 200, i * 25, false);
				int xOffset = i * 10 * (Math.floorDiv(i, 2) > 0 ? 1 : -1) + Rand.getRandomRange(-10, 10);
				targetField.createProjectile(Projectile.MISSILE, target.bounds.x + target.bounds.width / 2 + xOffset, target.bounds.y - 200, target.bounds.x + target.bounds.width / 2 + xOffset, target.bounds.y + target.bounds.height / 2, 300 + i * 25, true);
			}
			break;
		}
	}

	public Module getModuleOnCoordinates(float x, float y) {
		for (Module module : modules) {
			if (!(module instanceof Cannon) && module.bounds.contains(x, y)) {
				return module;
			}
		}
		return null;
	}

	public Module getModule(ShipModule shipModule) {
		for (Module module : modules) {
			if (module.shipModule.equals(shipModule)) {
				return module;
			}
		}
		return null;
	}
	
	public Module getRandomModule() {
		List<Module> modulesRandom = new ArrayList<Module>();
		for (Module module : modules) {
			if (!(module instanceof Cannon)) {
				modulesRandom.add(module);
			}
		}
		return modulesRandom.get(Rand.getRandom(modulesRandom.size()));
	}

	@Override
	public void draw(Batch batch, float a) {
		float x = getX();
		float y = getY();
		float width = getWidth();
		float height = getHeight();

		Drawable background = this.getBackground();
		//background.draw(batch, x, y, width, height);
		batch.flush();

		scissors.set(x + background.getLeftWidth(), y + background.getTopHeight(), width - background.getLeftWidth() - background.getRightWidth(), height - background.getTopHeight() - background.getBottomHeight());

		Matrix4 matrix = batch.getProjectionMatrix().cpy();
		
		batch.setProjectionMatrix(Game.batch.getProjectionMatrix());
		
		if (ScissorStack.pushScissors(scissors)) {

			for (Projectile projectile : projectiles) {
				projectile.draw(batch);
			}
			
			for (Decoration decoration : decorations) {
				decoration.draw(batch);
			}
			
			for (Module module : modules) {
				module.draw(batch);
			}
			
			for (Module module : modules) {
				module.drawEnd(batch);
			}

			for (Explosion explosion : explosions) {
				explosion.draw(batch);
			}
			
			for (TextMessage textMessage : messages) {
				textMessage.draw(batch);
			}
			
			batch.flush();
			ScissorStack.popScissors();
		}
		batch.setProjectionMatrix(matrix);
		this.drawChildren(batch, a);
	}

	public void step() {
		
		if (yOffset > 0) {
			yOffset -= yOffset/10;
			if (yOffset < 0.1) {
				yOffset = 0;
			}
		}
		
		boolean isSinking = (modules.get(0).shipModule.ship.hp <= 0);

		if (isSinking) {
			for (Module module : modules) {
				if (Rand.getRandom(8) == 0) {
					explosions.add(new Explosion((int) (module.bounds.x) + Rand.getRandomRange(-4, 4), (int) (module.bounds.y) + Rand.getRandomRange(-4, 4)));
				}
				module.bounds.y += Rand.getRandom(10) / 6;
			}
			for (Decoration decoration : decorations) {
				decoration.y += Rand.getRandom(10) / 6;
			}
		}

		for (Projectile projectile : projectiles) {
			projectile.step();
		}
		
		for (TextMessage message : messages) {
			message.step();
		}
	}

	public void setAnimationsState(boolean isPlaying) {
		if (isPlaying) {
			for (Projectile projectile : projectiles) {
				projectile.animation.resume();
			}
			for (Explosion explosion : explosions) {
				explosion.anim.resume();
			}
		} else {
			for (Projectile projectile : projectiles) {
				projectile.animation.pause();
			}
			for (Explosion explosion : explosions) {
				explosion.anim.pause();
			}
		}
	}

	@Override
	public void act(float arg0) {
		mouseX = Input.getUiMouseX() - ShipGameField.this.getX();
		mouseY = Input.getUiMouseY() - ShipGameField.this.getY();

		for (Module module : modules) {
			module.isHovered = false;
		}

		for (Module module : modules) {

			if (mouseX > module.bounds.x && mouseX < module.bounds.x + module.bounds.width && mouseY > module.bounds.y + yOffset && mouseY < module.bounds.y + module.bounds.height + yOffset) {
				module.isHovered = true;
				break;
			}
		}

		super.act(arg0);
	}

	public Module placeModule(Rectangle bounds) {
		return new Module(new Rectangle(bounds.x, (float) Math.ceil(bounds.y / bounds.height) * (bounds.height - 1) + (12 - bounds.height), bounds.width, bounds.height), this);
	}

	public class Module {

		public Rectangle bounds;
		private ShipModule shipModule;
		public boolean isHovered, isMarked = false;
		private NinePatch module = UiFactory.shipModuleBounds, moduleHover = UiFactory.shipModuleBoundsLight;
		private TextureRegion roomType;

		public ShipModule getShipModule() {
			return shipModule;
		}

		public void setShipModule(ShipModule shipModule) {
			this.shipModule = shipModule;
			if (shipModule instanceof StorageShipModule) {
				roomType = UiFactory.shipUiRoomType[0];
			} else if (shipModule instanceof ReactorShipModule) {
				roomType = UiFactory.shipUiRoomType[1];
			} else if (shipModule instanceof WorkshopShipModule) {
				roomType = UiFactory.shipUiRoomType[2];
			} else if (shipModule instanceof WeaponControlShipModule) {
				roomType = UiFactory.shipUiRoomType[3];
			} else if (shipModule instanceof CommandShipModule) {
				roomType = UiFactory.shipUiRoomType[4];
			}
			// TODO: Костыль /
			if (roomType != null && !roomType.isFlipY()) 
				roomType.flip(false, true);
		}

		public void draw(Batch batch) {
			if (roomType != null) {
				batch.draw(roomType, getX() + bounds.x + 1, getY() + bounds.y + 1 + yOffset, roomType.getRegionWidth(), roomType.getRegionHeight());
			}
			module.draw(batch, getX() + bounds.x, getY() + bounds.y + yOffset, bounds.width, bounds.height);
			if (isMarked) {
				batch.draw(UiFactory.shipModuleCross, getX() + bounds.x + (bounds.width - UiFactory.shipModuleCross.getRegionWidth()) / 2, getY() + bounds.y + (bounds.height - UiFactory.shipModuleCross.getRegionHeight()) / 2 + yOffset);
			}
		}

		public void drawEnd(Batch batch) {
			if (isHovered) {
				moduleHover.draw(batch, getX() + bounds.x, getY() + bounds.y + yOffset, bounds.width, bounds.height);
			}
		}

		public Module(Rectangle bounds, ShipGameField field) {
			this.bounds = bounds;
			modules.add(this);
		}

	}

	public Cannon placeCannon(Rectangle bounds, Sprite sprite, int index, int xScale) {
		return new Cannon(new Rectangle(bounds.x, (float) Math.ceil(bounds.y / bounds.height) * (bounds.height - 1) + 26, bounds.width, bounds.height), this, sprite, index, xScale);
	}

	public class Cannon extends Module {

		private Sprite sprite;
		private int xScale = 1, index;

		public Cannon(Rectangle bounds, ShipGameField field, Sprite sprite, int index, int xScale) {
			super(bounds, field);
			bounds.height += 9;
			this.sprite = sprite;
			this.index = index;
			this.xScale = xScale;
		}

		@Override
		public void draw(Batch batch) {
			if (sprite != null) {
				sprite.drawScale(batch, index, getX() + bounds.x, getY() + bounds.y + bounds.height + yOffset, xScale, 1);
			}
			// Game.render.drawTextureRegion(UiFactory.progressBar, bounds.x,
			// bounds.y, 1, 1);
		}

		@Override
		public void drawEnd(Batch batch) {

		}

	}

	private void createProjectile(int type, float x, float y, float targetX, float targetY, int delay, boolean doDamage) {

		Projectile projectile;
		switch (type) {
		case Projectile.CANNON:
			projectile = new ProjectileCannon();
			break;
		case Projectile.MISSILE:
			projectile = new ProjectileMissile();
			break;
		case Projectile.SWARM_MISSILE:
			projectile = new ProjectileSwarmMissile();
			break;
		default:
			projectile = new ProjectileCannon();
			break;
		}
		projectile.create(x, y, targetX, targetY, delay, doDamage);
		projectiles.add(projectile);
	}

	private class Projectile {

		public static final int CANNON = 0, MISSILE = 1, SWARM_MISSILE = 2;

		protected Sprite sprite;
		protected Animation animation;
		protected float x, y, targetX, targetY, delay, direction, speed = 1.5f;
		protected double hspeed, vspeed;
		protected boolean doDamage;

		public void step() {
			if (delay <= 0) {
				x += hspeed;
				y += vspeed;
				if (SimpleMath.distance(x, y, targetX, targetY) < 2) {
					if (doDamage) {
						destroy();
					}
					projectiles.remove(this);
				}
			} else {
				--delay;
			}
		}

		public void draw(Batch batch) {
			if (delay <= 0) {
				Game.render.drawSprite(batch, sprite, (int) animation.getCurrentFrame(), getX() + x, getY() + y, direction);
			}
		}

		protected void destroy() {

			// Реакция обычного корабля //
			
			yOffset = Rand.getRandomRange(1, 3);
			
			// Реакция корабля игрока на попадание //

			if (ShipGameField.this == Ship.player) {
				Room.getCurrentRoom().setViewPosition(Room.getCurrentRoom().getViewPositionX() - Rand.getRandomRange(4, 10) * Rand.choose(1, -1), Room.getCurrentRoom().getViewPositionY() - Rand.getRandomRange(4, 10) * Rand.choose(1, -1));
				UiController.distort((float)Math.floor((Ship.playerShip.maxHp - Ship.playerShip.hp)/5f));
			}
		}

		public void create(float x, float y, float targetX, float targetY, int delay, boolean doDamage) {
			this.x = x;// - bounds.x;
			this.y = y;// - bounds.y;
			this.targetX = targetX + Rand.getRandomRange(-3, 3);
			this.targetY = targetY + Rand.getRandomRange(-3, 3);
			this.delay = delay;
			this.doDamage = doDamage;
			direction = (float) SimpleMath.direction(this.x, this.y, this.targetX, this.targetY);
			hspeed = Math.cos(direction) * speed;
			vspeed = Math.sin(direction) * speed;
			direction = (float) Math.toDegrees(direction);
		}

		public Projectile() {

		}

	}

	private class ProjectileCannon extends Projectile {

		@Override
		protected void destroy() {
			if (getModuleOnCoordinates(x, y) != null && Rand.getRandom(2) > 0) {
				getModuleOnCoordinates(x, y).shipModule.damage(4);
				messages.add(new TextMessage(x, y, "4"));
				float dir = Rand.getRandomRange(0, 359);
				for (int i = 0; i < 3; i++) {
					explosions.add(new Explosion((int) (x + SimpleMath.lengthdirX(Rand.getRandomRange(2, 5), dir + 120 * i)), (int) (y + SimpleMath.lengthdirY(Rand.getRandomRange(2, 5), dir + 120 * i))));
				}
			} else {
				explosions.add(new MissExplosion((int) x, (int) y));
				messages.add(new TextMessage(x, y, "miss"));
			}
			super.destroy();
		}

		@Override
		public void create(float x, float y, float targetX, float targetY, int delay, boolean doDamage) {
			super.create(x, y, targetX, targetY, delay, doDamage);
			sprite = Sprites.spriteShipUiCannonProjectile;
			animation = new Animation(0.3f, Sprites.spriteShipUiCannonProjectile.getFrameCount());
		}

		public ProjectileCannon() {
			super();
		}

	}

	private class ProjectileMissile extends Projectile {

		@Override
		protected void destroy() {
			if (getModuleOnCoordinates(x, y) != null) {
				getModuleOnCoordinates(x, y).shipModule.damage(4);
				messages.add(new TextMessage(x, y, "4"));
				float dir = Rand.getRandomRange(0, 359);
				explosions.add(new Explosion((int) (x + SimpleMath.lengthdirX(5, dir)), (int) (y + SimpleMath.lengthdirY(5, dir))));
				explosions.add(new Explosion((int) (x + SimpleMath.lengthdirX(5, dir + 180)), (int) (y + SimpleMath.lengthdirY(5, dir + 180))));

				Module under;

				if ((under = getModuleOnCoordinates(x, y + 12)) != null) {
					under.shipModule.damage(2);
					messages.add(new TextMessage(x, y + 12, "2"));
					explosions.add(new Explosion((int) x, (int) y + 12));
				}

			} else {
				messages.add(new TextMessage(x, y, "miss"));
				explosions.add(new MissExplosion((int) x, (int) y));
			}
			super.destroy();
		}

		@Override
		public void create(float x, float y, float targetX, float targetY, int delay, boolean doDamage) {
			super.create(x, y, targetX, targetY, delay, doDamage);
			sprite = Sprites.spriteShipUiMissileProjectile;
			animation = new Animation(0.3f, Sprites.spriteShipUiMissileProjectile.getFrameCount());
		}

	}

	private class ProjectileSwarmMissile extends Projectile {

		@Override
		protected void destroy() {
			if (getModuleOnCoordinates(x, y) != null && Rand.getRandom(2) > 0) {
				getModuleOnCoordinates(x, y).shipModule.damage(1);
				messages.add(new TextMessage(x, y, "1"));
				explosions.add(new Explosion((int) x, (int) y));
			} else {
				messages.add(new TextMessage(x, y, "miss"));
				explosions.add(new MissExplosion((int) x, (int) y));
			}
		}

		@Override
		public void create(float x, float y, float targetX, float targetY, int delay, boolean doDamage) {
			super.create(x, y, targetX, targetY, delay, doDamage);
			sprite = Sprites.spriteShipUiMissileProjectile;
			animation = new Animation(0.3f, Sprites.spriteShipUiMissileProjectile.getFrameCount());
		}

	}

	public class Explosion {

		Sprite sprite = Sprites.spriteShipUiExplosion;
		Animation anim = new Animation(0.2f, Sprites.spriteShipUiExplosion.getFrameCount()) {

			@Override
			public void end() {
				explosions.remove(Explosion.this);
				super.end();
			}

		};
		private float x, y;

		public void draw(Batch batch) {
			Game.render.drawSprite(batch, sprite, (int) anim.getCurrentFrame(), getX() + x, getY() + y);
		}

		public Explosion(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	public class MissExplosion extends Explosion {

		public MissExplosion(int x, int y) {
			super(x, y);
			sprite = Sprites.spriteShipUiExplosion01;
			anim = new Animation(0.2f, Sprites.spriteShipUiExplosion01.getFrameCount()) {

				@Override
				public void end() {
					explosions.remove(MissExplosion.this);
				}

			};
		}

	}

	public class TextMessage {
		private float yDistance = 20, yOffset = 0;
		private float x, y;
		private String text;
		
		public void step() {
			if (yOffset < yDistance) {
				yOffset += 0.1;
			} else {
				messages.remove(this);
			}
		}
		
		public void draw(Batch batch) {
			Game.render.drawText(batch, getX() + x, getY() + y - yOffset, text);
		}
		
		public TextMessage(float x, float y, String text) {
			this.x = x;
			this.y = y;
			this.text = text;
		}
		
	}

	public Decoration placeDecoration(ShipModule shipModule, float xOffset, float yOffset, Sprite sprite, int index, float xScale) {

		for (Module module : modules) {
			if (module.shipModule.equals(shipModule)) {
				return new Decoration(module.bounds.x + xOffset, module.bounds.y + yOffset, sprite, index, xScale);
			}
		}
		return null;
	}

	public class Decoration {

		private float x, y, xScale;
		private Sprite sprite;
		private int index;

		public void draw(Batch batch) {
			sprite.drawScale(batch, index, getX() + x, getY() + y + yOffset, xScale, 1);
		}

		public Decoration(float x, float y, Sprite sprite, int index, float xScale) {
			this.x = x;
			this.y = y;
			this.sprite = sprite;
			this.index = index;
			this.xScale = xScale;
			decorations.add(this);
		}

	}
*/
}