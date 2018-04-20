package assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {

	public static ShaderProgram hologramShader = new ShaderProgram(Gdx.files.internal("shaders/outline.vsh"), Gdx.files.internal("shaders/outline.fsh"));
	public static ShaderProgram redBlueShiftShader = new ShaderProgram(Gdx.files.internal("shaders/outline.vsh"), Gdx.files.internal("shaders/redBlueShift.fsh"));
	public static ShaderProgram scanlineShader = new ShaderProgram(Gdx.files.internal("shaders/outline.vsh"), Gdx.files.internal("shaders/scanline.fsh"));
	public static ShaderProgram filteringShader = new ShaderProgram(Gdx.files.internal("shaders/outline.vsh"), Gdx.files.internal("shaders/filtering.fsh"));
	static {
		System.out.println(hologramShader.isCompiled() ? "" : hologramShader.getLog());
		System.out.println(redBlueShiftShader.isCompiled() ? "" : redBlueShiftShader.getLog());
		System.out.println(scanlineShader.isCompiled() ? "" : scanlineShader.getLog());
		System.out.println(filteringShader.isCompiled() ? "" : filteringShader.getLog());
	}
}
