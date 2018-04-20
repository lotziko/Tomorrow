package assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class UI {
	private static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/UI.atlas"));
	
	public static NinePatch getNinepatch(String name) {
		return atlas.createPatch(name);
	}
	
	public static TextureRegion getTextureRegion(String name) {
		return atlas.findRegion(name);
	}
	
	public static TextureRegion[] getTextureRegionFrames(String name) {
		Array<AtlasRegion> region = atlas.findRegions(name);
		TextureRegion[] array = new TextureRegion[region.size];
		for(int i = 0; i < region.size; i++) {
			array[i] = region.get(i);
		}
		return array;
	}
}
