package com.tomorrow.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tomorrow.game.Game;

import assets.Shaders;
import assets.UI;
import blueberry.engine.render.Buffer;

public class UiFactory {

	public static NinePatch tabletPane = UI.getNinepatch("tabletPane");
	public static NinePatch tablePane = UI.getNinepatch("tablePane");
	public static NinePatch standardButtonHover = UI.getNinepatch("standardButtonHover001");
	public static NinePatch shipModuleBounds = UI.getNinepatch("shipModuleBounds");
	public static NinePatch shipModuleBoundsLight = UI.getNinepatch("shipModuleBoundsLight");
	public static NinePatch uiWindowBounds = UI.getNinepatch("uiWindowBounds");
	public static NinePatch hpBar = UI.getNinepatch("hpBar");
	public static NinePatch leftShipTablePane = UI.getNinepatch("leftShipTablePane");
	public static NinePatch rightShipTablePane = UI.getNinepatch("rightShipTablePane");
	public static NinePatch itemBackground = UI.getNinepatch("itemBackground");
	public static NinePatch itemHover = UI.getNinepatch("itemHover");
	public static Color uiStandardTextColor = new Color(113/255f, 133/255f, 115/255f, 1f);
	public static TextureRegion cratesIcon = UI.getTextureRegion("cratesIcon");
	public static TextureRegion uiBackground = UI.getTextureRegion("uiBackground");
	public static TextureRegion shipModuleCross = UI.getTextureRegion("shipModuleCross");
	public static TextureRegion shipTargetCross = UI.getTextureRegion("shipTargetCross");
	public static TextureRegion progressBar = UI.getTextureRegion("progressBar");
	public static TextureRegion hpIcon = UI.getTextureRegion("hpIcon");
	public static TextureRegion hpSign = UI.getTextureRegion("hpSign");
	public static TextureRegion[] itemIcons = UI.getTextureRegionFrames("items/item");
	public static TextureRegion[] weaponIcons = UI.getTextureRegionFrames("shipWeapon");
	public static TextureRegion[] shipUiRoomType = UI.getTextureRegionFrames("shipUiRoomType");
	
	public static Buffer buffer = new Buffer(Game.screenWidth, Game.screenHeight);
	/*
	public static TextButton createStandardTextButton(float x, float y, float width, float height, String text, int align) {
		
		TextButton button = new TextButton((float)Math.floor(x), (float)Math.floor(y), width, height, null, standardButtonHover, Game.render.getDefaultFont(), text, align);
		button.setTextColor(uiStandardTextColor);
		return button;
	}
	
	public static TextButton createStandardTextButton(float width, float height, String text, int align) {
		
		TextButton button = new TextButton(0, 0, width, height, null, standardButtonHover, Game.render.getDefaultFont(), text, align);
		button.setTextColor(uiStandardTextColor);
		return button;
	}
	
	public static NinepatchImage createShipModuleBackground(float x, float y, float width, float height) {
		NinepatchImage image = new NinepatchImage(x, y, width, height, shipModuleBounds, Align.NULL);
		return image;
	}
	
	public static SpriteImage createShipCannonBackground(float x, float y) {
		SpriteImage image = new SpriteImage(x, y, Sprites.spriteShipWeaponIcons.getFrames()[0].getRegionWidth(), Sprites.spriteShipWeaponIcons.getFrames()[0].getRegionWidth(), Sprites.spriteShipWeaponIcons, Align.NULL);
		return image;
	}
	
	public static ProgressBar createHpBar(float x, float y, float width, float height) {
		ProgressBar bar = new ProgressBar(x, y, width, height, hpBar, hpIcon, Align.NULL);
		return bar;
	}
	*/
	public static void create() {
		//hpSign.flip(false, true);
		//cratesIcon.flip(false, true);
		/*for (TextureRegion textureRegion : weaponIcons) {
			textureRegion.flip(false, true);
		}
		for (TextureRegion textureRegion : shipUiRoomType) {
			textureRegion.flip(false, true);
		}*/
		leftShipTablePane.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		buffer.getBuffer().begin();
		Game.batch.begin();
		Game.render.bufferClear(Color.WHITE, 0);
		Game.render.setShader(Shaders.scanlineShader);
		Game.render.drawTextureRegion(UiFactory.uiBackground, 0, 0, Game.screenWidth, Game.screenHeight);
		Game.render.resetShader();
		Game.batch.end();
		buffer.getBuffer().end();
	}
	
}
