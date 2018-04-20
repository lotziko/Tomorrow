package com.tomorrow.objects.ship;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.tomorrow.UI.items.Item;
import com.tomorrow.UI.items.ItemContainer;
import com.tomorrow.UI.items.ItemFactory;
import com.tomorrow.objects.ship.modules.CommandShipModule;
import com.tomorrow.objects.ship.modules.ReactorShipModule;
import com.tomorrow.objects.ship.modules.StorageShipModule;
import com.tomorrow.objects.ship.modules.WeaponControlShipModule;
import com.tomorrow.objects.ship.modules.WorkshopShipModule;

import assets.Tiles;
import blueberry.engine.tiles.Tilelayer;
import blueberry.engine.tiles.Tilemap;

public class Ship {

	//public static ShipGameField player, enemy;
	public static Ship playerShip;//, enemyShip;
	//public static ShipTargetCross cross;
	//public HpBar hpBar;
	@Expose
	public List<ShipModule> modules = new ArrayList<ShipModule>();
	@Expose
	public int width, height, hp = 60, maxHp = 60;
	@Expose
	private ItemContainer storage = new ItemContainer(30);

	public ItemContainer getStorage() {
		return storage;
	}
	
	/* Временное решение, переделай под listener */

	/*public static void step() {
		if (playerShip != null) {
			for (ShipModule module : playerShip.modules) {
				module.step();
			}
		}
		if (enemyShip != null) {
			for (ShipModule module : enemyShip.modules) {
				module.step();
			}
		}
		if (player != null) {
			player.step();
		}
		if (enemy != null) {
			enemy.step();
		}
	}*/

	/*public static void load() {
		playerShip = JsonManager.fromJsonFile(System.getProperty("user.dir") + "/playerShip.json", Ship.class);
		enemyShip = JsonManager.fromJsonFile(System.getProperty("user.dir") + "/enemyShip.json", Ship.class);
		playerShip.createPlayerField();
		enemyShip.createEnemyField();
	}*/
	/*
	public static void save() {
		JsonManager.toJsonFile(System.getProperty("user.dir") + "/playerShip.json", playerShip);
		JsonManager.toJsonFile(System.getProperty("user.dir") + "/enemyShip.json", enemyShip);
	}*/
	
	public Tilemap build(boolean isPlayer) {

		Tilelayer ground = new Tilelayer("ground", width, height, 8, 8, null);
		Tilelayer wall = new Tilelayer("wall", width, height, 8, 8, null);
		Tilelayer objects = new Tilelayer("objects", width, height, 8, 8, null);
		Tilelayer collide = new Tilelayer("collide", width, height, 8, 8, null);

		Tilemap map = Tilemap.create(width, height, 8, 8, ground, wall, objects, collide);

		/*for (ShipModule shipModule : modules) {
			if (shipModule instanceof StandardShipModule)
				map.getLayer("ground").fillRectangle(shipModule.x - 1, shipModule.y - 1, shipModule.x + shipModule.width + 1, shipModule.y + shipModule.height + 1, Tiles.manager.getTile(10003));
		}*/

		for (ShipModule shipModule : modules) {
			if (shipModule instanceof StandardShipModule)
				map.insertTiles(Tiles.manager.loadTilemap(shipModule.tilemapName), shipModule.x, shipModule.y);
			shipModule.create(isPlayer);
		}
		ShipModule left = getLeftModule();
		ground.setTile(left.x - 11 - 2, left.y + left.height + 1, Tiles.manager.getTile(13602));
		ShipModule right = getRightModule();
		ground.setTile(right.x + right.width - 8 - 1, right.y + right.height, Tiles.manager.getTile(13601));

		return map;
	}

	public ShipModule getLeftModule() {
		if (modules.size() > 0) {
			ShipModule left = modules.get(0);
			for (ShipModule shipModule : modules) {
				if (shipModule.x < left.x) {
					left = shipModule;
				}
			}
			return left;
		}
		return null;
	}

	public ShipModule getRightModule() {
		if (modules.size() > 0) {
			ShipModule right = modules.get(0);
			for (ShipModule shipModule : modules) {
				if (shipModule.x + shipModule.width > right.x + right.width) {
					right = shipModule;
				}
			}
			return right;
		}
		return null;
	}
	/*
	public void createPlayerField() {
		player = new ShipGameField();
		player.align(Align.topLeft);

		hpBar = new HpBar(hp, maxHp);
		player.add(hpBar).size(maxHp * 2 - 1, 8).pad(1f).padBottom(5f).row();
		
		int xPos = (212 - this.width) / 2;
		int yPos = (226 - this.height) / 2;

		for (ShipModule shipModule : modules) {
			if (shipModule instanceof StandardShipModule) {
				Module module = player.placeModule(new Rectangle(xPos + shipModule.x, yPos + shipModule.y, shipModule.width, shipModule.height));
				module.setShipModule(shipModule);
			} else if (shipModule instanceof StandardShipWeapon) {

				final Cannon cannon = player.placeCannon(new Rectangle(xPos + shipModule.x, yPos + shipModule.y, shipModule.width, shipModule.height), Sprites.spriteShipWeaponIcons, ((StandardShipWeapon) shipModule).type, 1);
				cannon.setShipModule(shipModule);

				final ShipWeaponButton button = new ShipWeaponButton(shipModule);
				button.addListener(new ClickListener() {

					@Override
					public void clicked(InputEvent event, float x, float y) {

						if (cross == null && enemy != null && playerShip.hp > 0) {
							button.getWeaponModule().clearListeners();
							if (button.target != null) {
								button.target.isMarked = false;
								button.target = null;
							}
							cross = new ShipTargetCross() {

								@Override
								public void clicked() {
									final Module module;
									if ((module = enemy.getModuleOnCoordinates(Input.getUiMouseX() - enemy.getX(), Input.getUiMouseY() - enemy.getY())) != null) {
										final boolean CTRL = Input.isKeyDown(Keys.CONTROL_LEFT);
										if (button.getWeaponModule().isLoaded() && !CTRL) {
											player.fire(cannon, enemy, module);
											button.target = module;
											button.fire();
										} else {
											button.target = module;
											module.isMarked = true;
											button.getWeaponModule().addListener(new ShipWeaponFireListener(CTRL) {

												@Override
												public boolean fire() {
													if (playerShip.hp > 0 && enemyShip.hp > 0) {
														if (!isRepeating) {
															module.isMarked = false;
														}
														player.fire(cannon, enemy, module);
														return true;
													} else {
														button.getWeaponModule().clearListeners();
														return false;
													}
												}

											});
										}

									}
									ObjectManager.destroyObject(cross);
									cross = null;
									super.clicked();
								}

							};
							cross.button = button;
						}

						super.clicked(event, x, y);
					}

				});
				player.add(button).size(29, 21).pad(1f).left().row();
			}
		}
		
		ShipModule left = playerShip.getLeftModule();
		player.placeDecoration(left, 1, 1, Sprites.spriteShipUiDecorations, 1, -1);
		ShipModule right = playerShip.getRightModule();
		player.placeDecoration(right, right.width - 1, 1, Sprites.spriteShipUiDecorations, 0, 1);
	}
	*/
	/*
	public void createEnemyField() {
		enemy = new ShipGameField();
		enemy.align(Align.topRight);

		hpBar = new HpBar(hp, maxHp);
		enemy.add(hpBar).size(maxHp * 2 - 1, 8).pad(1f).padBottom(5f).row();
		
		int xPos = (212 - this.width) / 2;
		int yPos = (226 - this.height) / 2;

		for (ShipModule shipModule : modules) {
			if (shipModule instanceof StandardShipModule) {
				Module module = enemy.placeModule(new Rectangle(xPos + shipModule.x, yPos + shipModule.y, shipModule.width, shipModule.height));
				module.setShipModule(shipModule);
			} else if (shipModule instanceof StandardShipWeapon) {

				final Cannon cannon = enemy.placeCannon(new Rectangle(xPos + shipModule.x, yPos + shipModule.y, shipModule.width, shipModule.height), Sprites.spriteShipWeaponIcons, ((StandardShipWeapon) shipModule).type, -1);
				cannon.setShipModule(shipModule);

				final StandardShipWeapon weapon = (StandardShipWeapon) shipModule;
				weapon.addListener(new ShipWeaponFireListener(true) {

					@Override
					public boolean fire() {
						if (playerShip.hp > 0 && enemyShip.hp > 0) {
							enemy.fire(cannon, player, player.getRandomModule());
							return true;
						} else {
							weapon.clearListeners();
							return false;
						}
					}

				});
			}
		}

		ShipModule left = enemyShip.getLeftModule();
		enemy.placeDecoration(left, 1, 1, Sprites.spriteShipUiDecorations, 0, -1);
		ShipModule right = enemyShip.getRightModule();
		enemy.placeDecoration(right, right.width - 1, 1, Sprites.spriteShipUiDecorations, 1, 1);
	}
	*/
	public static Ship generate() {
		Ship ship = new Ship();
		ship.storage.addItem(ItemFactory.createItem(Item.MEDKIT, 5));
		ShipModule module;
		
		ship.modules.add(module = new ReactorShipModule(0, 0, 26, 12, ship));
		ship.modules.add(module = module.insertRight(new StorageShipModule(0, 0, 26, 12, ship)));
		//ship.modules.add(module.insertTop(new CommandShipModule(0, 0, 26, 12, ship)));
		//ship.modules.add(module = module.insertRight(new WorkshopShipModule(0, 0, 26, 12, ship)));
		//ship.modules.add(module.insertTop(new StandardShipWeapon(3, 3, WeaponData.TYPE_SWARM_MISSILE_LAUNCHER, ship)));
		//ship.modules.add(module = module.insertRight(new WeaponControlShipModule(0, 0, 26, 12, ship)));
		//ship.modules.add(module.insertTop(new StandardShipWeapon(3, 3, WeaponData.TYPE_CANNON, ship)));

		resize(ship, 25, 25);

		return ship;
	}
	/*
	public static Ship generateEnemy() {
		Ship ship = new Ship();
		ship.maxHp = ship.hp = Rand.getRandomRange(8, 18);
		int length = Rand.getRandomRange(4, 7);
		ShipModule module;
		ship.modules.add(module = new StandardShipModule(0, 0, 26, 12, ship));
		int cannons = 3;
		for (int i = 0; i < length - 1; i++) {
			ship.modules.add(module = module.insertRight(new StandardShipModule(0, 0, 26, 12, ship)));
			if (i == length / 2) {
				ship.modules.add(module.insertTop(new StandardShipModule(0, 0, 26, 12, ship)));
			} else if (Rand.getRandom(cannons) > 0) {
				ship.modules.add(module.insertTop(new StandardShipWeapon(3, 3, Rand.getRandom(3), ship)));
				cannons--;
			}
		}

		resize(ship, 25, 25);

		return ship;
	}
	*/
	
	private static Ship resize(Ship ship, int xOffset, int yOffset) {

		for (ShipModule shipModule : ship.modules) {
			int offset;
			if (shipModule.x < 0) {
				offset = -shipModule.x;
				for (ShipModule moduleIterate : ship.modules) {
					moduleIterate.x += offset;
				}
			}
			if (shipModule.y < 0) {
				offset = -shipModule.y;
				for (ShipModule moduleIterate : ship.modules) {
					moduleIterate.y += offset;
				}
			}
		}

		int width = 0, height = 0;

		for (ShipModule shipModule : ship.modules) {
			if (shipModule.x + shipModule.width > width) {
				width = shipModule.x + shipModule.width;
			}
			if (shipModule.y + shipModule.height > height) {
				height = shipModule.y + shipModule.height;
			}
		}

		ship.width = width + xOffset * 2;
		ship.height = height + yOffset * 2;

		for (ShipModule shipModule : ship.modules) {
			shipModule.x += xOffset;
			shipModule.y += yOffset;

			if (shipModule instanceof StandardShipModule)
				if (shipModule.left == -1 && shipModule.right != -1) {
					shipModule.setToLeft();
				} else if (shipModule.left != -1 && shipModule.right == -1) {
					shipModule.setToRight();
				} else if (shipModule.left == -1 && shipModule.right == -1) {
					shipModule.setToLeftRight();
				}
		}

		return ship;

	}
	 
	/*
	public void damage(int damage) {
		hp -= damage;
		if (hp <= 0) {
			for (ShipModule module : modules) {
				if (module instanceof StandardShipWeapon) {
					((StandardShipWeapon) module).clearListeners();
				}
			}
		}
		hpBar.setHp(hp);
	}
	*/

}