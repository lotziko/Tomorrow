package com.tomorrow.proceduralGeneration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;

import assets.Tiles;
import blueberry.engine.random.Rand;
import blueberry.engine.tiles.Tile;
import blueberry.engine.tiles.Tilelayer;
import blueberry.engine.tiles.Tilemap;

public class ProceduralGeneration {

	public static ProceduralGeneration manager;

	private List<Tilemap> mapsToGenerate = new ArrayList<Tilemap>();
	private int bufferInt = 0;
	private Room bufferRoom = null;
	
	
	public List<Tilemap> getMapsToGenerate() {
		return mapsToGenerate;
	}

	public static void initialize() {
		manager = new ProceduralGeneration();
	}

	private ProceduralGeneration() {
		FileHandle folder = Gdx.files.internal("bin/tilemaps");
		FileHandle[] files = folder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.contains("tilemapGenerator");
			}

		});
		for (FileHandle file : files) {
			mapsToGenerate.add(Tiles.manager.loadTilemap("tilemaps/" + file.name()));
		}
	}
	
	public LevelContainer generate(int roomsCount) {
		
		LevelContainer levelContainer = new LevelContainer();
		List<Anchor> avalibleAnchors = new CopyOnWriteArrayList<Anchor>();
		final List<Room> rooms = new ArrayList<Room>();
		int currentLayer = 0;
	
		// int currentLayer = 0;
		while(roomsCount > 0) {
			// TODO: make it usable once

			// first room
			if (avalibleAnchors.size() == 0) {
				Tilemap randomMap = mapsToGenerate.get(Rand.getRandom(mapsToGenerate.size()));
				Room room = levelContainer.firstRoom = new Room(randomMap, 0, 0, 0);
				List<Anchor> mapAnchors = getAnchors(randomMap);
				room.setAnchors(mapAnchors);
				avalibleAnchors.addAll(mapAnchors);
				rooms.add(room);
				continue;
			} else {
				boolean finished = false;
				Collections.shuffle(avalibleAnchors);
				bufferInt = currentLayer;
				avalibleAnchors.sort(new Comparator<Anchor>() {

					@Override
					public int compare(Anchor o1, Anchor o2) {
						if (bufferRoom != null)
						for(Anchor anchor : bufferRoom.anchors) {
							if (o1.equals(anchor)) {
								return 1;
							}
						}
						return Integer.compare(getRoomByAnchor(rooms, o1).layer, bufferInt);
					}
					
				});
				for (Anchor anchor : avalibleAnchors) {
					if (anchor instanceof EntranceAnchor) {

						Room anchorRoom = getRoomByAnchor(rooms, anchor);
						List<Tilemap> randomList = new ArrayList<Tilemap>(mapsToGenerate);
						Collections.shuffle(randomList);
						for (Tilemap map : randomList) {
							List<Anchor> newRoomAnchors = getAnchors(map);
							for (Anchor newAnchor : newRoomAnchors) {
								if (newAnchor instanceof EntranceAnchor) {
									int layer = anchorRoom.layer;
									Room room = new Room(map, (int) anchorRoom.bounds.x + anchor.x - newAnchor.x, (int) anchorRoom.bounds.y + anchor.y - newAnchor.y, 1 - layer);
									boolean fit = true;
									for (Room roomToCheck : rooms) {
										if (roomToCheck != anchorRoom && room.collide(roomToCheck)) {
											fit = false;
											break;
										}
									}
									if (!fit)
										break;
									newRoomAnchors.remove(newAnchor);
									room.setAnchors(newRoomAnchors);
									avalibleAnchors.addAll(newRoomAnchors);
									avalibleAnchors.remove(anchor);
									if (anchorRoom != null)
										anchorRoom.anchors.remove(anchor);
									rooms.add(room);
									bufferRoom = room;
									roomsCount--;
									finished = true;
									break;
								}
								if (finished)
									break;
							}
							if (finished)
								break;
						}
					} else if (anchor instanceof DoorAnchor) {
						Room anchorRoom = getRoomByAnchor(rooms, anchor);
						List<Tilemap> randomList = new ArrayList<Tilemap>(mapsToGenerate);
						Collections.shuffle(randomList);
						for (Tilemap map : randomList) {
							List<Anchor> newRoomAnchors = getAnchors(map);
							List<Anchor> oppositeAnchors = ((DoorAnchor) anchor).getOppositeAnchors(newRoomAnchors);
							for (Anchor oppositeAnchor : oppositeAnchors) {
								int layer = anchorRoom.layer;
								Room room = new Room(map, (int) anchorRoom.bounds.x + anchor.x - oppositeAnchor.x, (int) anchorRoom.bounds.y + anchor.y - oppositeAnchor.y, layer);
								// collide with other rooms
								boolean fit = true;
								for (Room roomToCheck : rooms) {
									if (roomToCheck != anchorRoom && room.collide(roomToCheck)) {
										fit = false;
										break;
									}
								}
								if (!fit)
									break;
								newRoomAnchors.remove(oppositeAnchor);
								room.setAnchors(newRoomAnchors);
								avalibleAnchors.addAll(newRoomAnchors);
								avalibleAnchors.remove(anchor);
								if (anchorRoom != null)
									anchorRoom.anchors.remove(anchor);
								rooms.add(room);
								bufferRoom = room;
								currentLayer = 1 - currentLayer;
								roomsCount--;
								finished = true;
								break;
							}
							if (finished)
								break;
						}
						if (finished)
							break;
					}
				}
			}
		}

		levelContainer.level = generateTilemap(rooms);
		return levelContainer;
	}

	private Tilemap[] generateTilemap(List<Room> rooms) {
		int left = 0, top = 0, width = 0, height = 0;
		for (Room room : rooms) {
			if (room.bounds.x < left) {
				left = (int) room.bounds.x;
			}
			if (room.bounds.y < top) {
				top = (int) room.bounds.y;
			}
		}
		for (Room room : rooms) {
			room.bounds.x -= left;
			room.bounds.y -= top;
			if (room.bounds.x + room.bounds.width > width) {
				width = (int) (room.bounds.x + room.bounds.width);
			}
			if (room.bounds.y + room.bounds.height > height) {
				height = (int) (room.bounds.y + room.bounds.height);
			}
		}

		int layers = 2;
		Tilemap[] result = new Tilemap[layers];
		for (int l = 0; l < layers; l++) {
			Tilelayer ground = new Tilelayer("ground", width, height, 8, 8, null);
			Tilelayer wall = new Tilelayer("wall", width, height, 8, 8, null);
			Tilelayer objects = new Tilelayer("objects", width, height, 8, 8, null);
			Tilelayer collide = new Tilelayer("collide", width, height, 8, 8, null);
			Tilemap map = Tilemap.create(width, height, 8, 8, ground, wall, objects, collide);
			map.getLayer("ground").fillRectangle(0, 0, width, height, Tiles.manager.getTile(10003));
			map.getLayer("collide").fillRectangle(0, 0, width, height, Tiles.manager.getTile(11609));
			for (Room room : rooms) {
				if (room.layer == l) {
					map.insertTiles(room.map, (int) room.bounds.x, (int) room.bounds.y);
				}
			}
			/* random ground */
			List<Tile> groundTiles = Tiles.manager.getTiles("tilesetTerrain");
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Tile tile = ground.getTile(i, j);
					if (tile != null && tile.getID() == 14801) {
						ground.setTile(i, j, groundTiles.get(Rand.getRandom(groundTiles.size())));
					}
				}
			}
			result[l] = map;
		}
		return result;
	}

	private Room getRoomByAnchor(List<Room> rooms, Anchor anchor) {
		for (Room room : rooms) {
			if (room.anchors.contains(anchor)) {
				return room;
			}
		}
		return null;
	}

	private List<Anchor> getAnchors(Tilemap tilemap) {
		Tilelayer objects = tilemap.getLayer("objects");
		if (objects == null) {
			return null;
		}
		List<Anchor> anchors = new ArrayList<Anchor>();
		for (int i = 0; i < objects.getWidth(); i++) {
			for (int j = 0; j < objects.getHeight(); j++) {
				Tile tile = objects.getTile(i, j);
				if (tile == null)
					continue;
				int tileID = tile.getID();
				if (tileID >= 14001 && tileID <= 14004) {
					anchors.add(new DoorAnchor(i, j, tileID - 14001));
				} else
					switch (tileID) {
					case 14005:
						anchors.add(new EntranceAnchor(i, j));
						break;
					default:
						break;
					}
			}
		}
		return anchors;
	}

	// temp public to debug
	public class Room {
		private Tilemap map;
		private Rectangle bounds;
		private int layer;
		private List<Anchor> anchors = new ArrayList<Anchor>();

		public Rectangle getBounds() {
			return bounds;
		}
		
		public void setAnchors(List<Anchor> anchors) {
			this.anchors = anchors;
		}

		public boolean collide(Room room) {
			// boolean result = true;
			/*
			 * for(int i = -1; i <= 1; i++) { for(int j = -1; j <= 1; j++) { if
			 * (Math.abs(i) + Math.abs(j) == 1) { if (!bounds.overlaps(new
			 * Rectangle(room.bounds.x, room.bounds.y, room.bounds.width,
			 * room.bounds.height)) || room.layer != this.layer) { result =
			 * false; break; } } } }
			 */
			return (room.layer == this.layer && bounds.overlaps(room.bounds));
		}

		public Room(Tilemap map, int x, int y, int layer) {
			this.map = map;
			this.bounds = new Rectangle(x, y, map.getWidth(), map.getHeight());
			this.layer = layer;
		}
	}

	public class LevelContainer {
		public Tilemap[] level;
		public Room firstRoom;
	}
	
	class Anchor {
		protected int x, y;

		public Anchor(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	class DoorAnchor extends Anchor {

		public static final int LEFT = 0, RIGHT = 1, TOP = 2, BOTTOM = 3;
		protected int side;

		public List<Anchor> getOppositeAnchors(List<Anchor> anchors) {
			List<Anchor> result = new ArrayList<Anchor>(anchors);
			result.removeIf(new Predicate<Anchor>() {
				@Override
				public boolean test(Anchor p) {
					if (p instanceof EntranceAnchor)
						return true;
					DoorAnchor d = (DoorAnchor) p;
					return d.side == side || d.side == LEFT && side != RIGHT || d.side == RIGHT && side != LEFT || d.side == TOP && side != BOTTOM || d.side == BOTTOM && side != TOP;
				}
			});
			return result;
		}

		public DoorAnchor(int x, int y, int side) {
			super(x, y);
			this.side = side;
		}

	}

	class EntranceAnchor extends Anchor {

		public EntranceAnchor(int x, int y) {
			super(x, y);
		}

	}

}
