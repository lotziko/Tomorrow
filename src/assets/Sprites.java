package assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import blueberry.engine.sprites.Sprite;

public class Sprites {
	private static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));
	public static Sprite spriteBackground = new Sprite(240, 135, "spriteBackground", atlas.findRegions("spriteBackground/"));
	public static Sprite spriteLayerDoor = new Sprite(24, 68, "spriteLayerDoor", atlas.findRegions("spriteLayerDoor/"));
	public static Sprite spriteMissileProjectile = new Sprite(12, 108, "spriteMissileProjectile", atlas.findRegions("spriteMissileProjectile/"));
	public static Sprite spritePlayerMove = new Sprite(14, 10, "spritePlayerMove", atlas.findRegions("spritePlayerMove/"));
	public static Sprite spritePlayerStay = new Sprite(13, 11, "spritePlayerStay", atlas.findRegions("spritePlayerStay/"));
	public static Sprite spriteRectangle1 = new Sprite(0, 0, "spriteRectangle1", atlas.findRegions("spriteRectangle1/"));
	public static Sprite spriteShipStandardCannon = new Sprite(48, 64, "spriteShipStandardCannon", atlas.findRegions("spriteShipStandardCannon/"));
	public static Sprite spriteShipStandardMissileLauncher = new Sprite(37, 64, "spriteShipStandardMissileLauncher", atlas.findRegions("spriteShipStandardMissileLauncher/"));
	public static Sprite spriteShipStorageInteractive = new Sprite(32, 62, "spriteShipStorageInteractive", atlas.findRegions("spriteShipStorageInteractive/"));
	public static Sprite spriteShipUiCannonProjectile = new Sprite(7, 2, "spriteShipUiCannonProjectile", atlas.findRegions("spriteShipUiCannonProjectile/"));
	public static Sprite spriteShipUiDecorations = new Sprite(13, 5, "spriteShipUiDecorations", atlas.findRegions("spriteShipUiDecorations/"));
	public static Sprite spriteShipUiExplosion = new Sprite(3, 4, "spriteShipUiExplosion", atlas.findRegions("spriteShipUiExplosion/"));
	public static Sprite spriteShipUiExplosion01 = new Sprite(4, 8, "spriteShipUiExplosion01", atlas.findRegions("spriteShipUiExplosion01/"));
	public static Sprite spriteShipUiMissileProjectile = new Sprite(19, 2, "spriteShipUiMissileProjectile", atlas.findRegions("spriteShipUiMissileProjectile/"));
	public static Sprite spriteShipWeaponIcons = new Sprite(9, 16, "spriteShipWeaponIcons", atlas.findRegions("spriteShipWeaponIcons/"));
}
