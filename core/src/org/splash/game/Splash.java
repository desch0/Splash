package org.splash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;

public class Splash extends ApplicationAdapter {


	private float windowWidth;
	private float windowHeight;
	public static final float UNIT_SCALE=1f/16f;
	SpriteBatch batch;
	World world;

	private OrthographicCamera camera;
	private Box2DDebugRenderer box2drenderer;

	private Ball ball;
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		world = new World(new Vector2(0, 0), true);
		box2drenderer = new Box2DDebugRenderer();
		ball = new Ball(10, 10, world);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		camera = new OrthographicCamera();
		camera.translate(0, 0);
		camera.update();
		camera.setToOrtho(false, 100, 100);

		box2drenderer.render(world, camera.combined);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		ball.draw(batch);
		batch.end();

		world.step(1/60f, 0, 0);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}