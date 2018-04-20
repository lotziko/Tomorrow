package com.tomorrow.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.tomorrow.UI.UiController;
import com.tomorrow.UI.UiFactory;
import com.tomorrow.proceduralGeneration.ProceduralGeneration;

import assets.Shaders;
import assets.Tiles;
import blueberry.engine.input.Input;
import blueberry.engine.objects.ObjectManager;
import blueberry.engine.render.Buffer;
import blueberry.engine.render.Render;
import blueberry.engine.world.Room;

public class Game extends ApplicationAdapter {

	public static OrthographicCamera camera;
	public static Render render;
	public static Stage uiStage;
	public static BitmapFont font;
	public static SpriteBatch batch;
	public static int screenWidth = 480, screenHeight = 270;
	public static boolean pause;
	Matrix4 matrix;
	FrameBuffer level;
	Buffer firstUiBuffer, secondUiBuffer;
	ShapeRenderer renderer;

	@Override
	public void create() {
		
		VisUI.load();
		
		OrthographicCamera temp = new OrthographicCamera();
		temp.setToOrtho(true, 1366, 768);
		matrix = temp.combined;

		batch = new SpriteBatch();
		batch.enableBlending();

		camera = new OrthographicCamera(screenWidth, screenHeight);
		camera.setToOrtho(true, screenWidth, screenHeight);
		camera.projection.setToOrtho2D(0, 0, 1366, 768);
		batch.setProjectionMatrix(camera.combined);
		// camera.zoom = 1.5f;
		Input.setCamera(camera, UiController.camera);
		renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(camera.combined);
		renderer.setTransformMatrix(batch.getTransformMatrix());

		level = new FrameBuffer(Format.RGBA8888, 1920, 1080, false);

		render = new Render(batch, renderer, level, camera);

		Tiles.initialize();

		OrthographicCamera uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, screenWidth, screenHeight);
		uiCamera.projection.setToOrtho2D(0, 0, screenWidth, screenHeight);
		Viewport viewport = new FitViewport(screenWidth, screenHeight, uiCamera);
		uiStage = new Stage();
		uiStage.setViewport(viewport);
		uiStage.getBatch().enableBlending();
		
		Gdx.input.setInputProcessor(uiStage);

		firstUiBuffer = new Buffer(screenWidth, screenHeight);
		secondUiBuffer = new Buffer(screenWidth, screenHeight);

		/* draw UI pane background */
		UiFactory.create();
		ProceduralGeneration.initialize();
		WorldThread.create();

		/* Не очень понятный костыль */

		Game.render.setShader(Shaders.hologramShader);
		Game.render.resetShader();
	}

	@Override
	public void render() {
		WorldThread.step();

		camera.position.set(Room.getCurrentRoom().x, Room.getCurrentRoom().y, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glDepthMask(false);
		
		level.begin();
		batch.begin();
		WorldThread.draw();

		if (UiController.uiAlpha > 0) {
			batch.setProjectionMatrix(UiController.camera.combined);
			render.bufferDraw(UiFactory.buffer, 0, 0, UiController.uiAlpha);
			
			render.bufferBegin(firstUiBuffer);
			render.bufferClear(Color.BLACK, 0);
			uiStage.getViewport().update(screenWidth, screenHeight);
			uiStage.draw();
			uiStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			render.bufferEnd();
			
			render.bufferBegin(secondUiBuffer);
			render.bufferClear(Color.BLACK, 0);
			render.setShader(Shaders.redBlueShiftShader);
			Shaders.redBlueShiftShader.setUniformf("offset", UiController.shade);
			/* TODO: разберись с этим, кривой размер пикселей */
			Shaders.redBlueShiftShader.setUniformf("scaleFactor", 0.003f);
			//batch.disableBlending();
			render.bufferDraw(firstUiBuffer, 0, 0);
			//batch.enableBlending();
			render.resetShader();
			render.bufferEnd();
			
			render.bufferDraw(secondUiBuffer, Room.getCurrentRoom().getWorldViewPositionX(), Room.getCurrentRoom().getWorldViewPositionY(), UiController.uiAlpha);
			
		}
		batch.setProjectionMatrix(camera.combined);
		ObjectManager.drawEnd();
		
		Room.getCurrentRoom().draw();
		batch.end();
		level.end();

		batch.setProjectionMatrix(matrix);
		batch.begin();
		//render.setShader(Shaders.filteringShader);
		//Shaders.filteringShader.setUniformf("sourceSize", new Vector2(1920, 1080));
		//Shaders.filteringShader.setUniformf("targetSize", new Vector2(1366, 768));
		batch.draw(level.getColorBufferTexture(), 0, 0, 1366, 768);
		//render.resetShader();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		uiStage.getViewport().update(width, height);
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
		level.dispose();
		uiStage.dispose();
		Tiles.dispose();
		VisUI.dispose();
	}
}
