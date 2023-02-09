package org.splash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;

import static org.splash.game.util.Constants.PPM;

public class Splash extends ApplicationAdapter {

	private float windowWidth;
	private float windowHeight;
	public static final float UNIT_SCALE=1f/16f;
	SpriteBatch batch;

	World world;
	private OrthographicCamera camera;
	private Box2DDebugRenderer box2dr;

	private Body player, ground;

	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, windowWidth/2, windowHeight/2);

		world = new World(new Vector2(0, -9.5f), false);

		batch = new SpriteBatch();
		world = new World(new Vector2(0, 0), true);
		box2dr = new Box2DDebugRenderer();

		player = createBox(10, 20,32, 32, false);
		ground = createBox(0, 0, 64, 32, true);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);
		camera = new OrthographicCamera();
		camera.translate(0, 0);
		camera.update();
		camera.setToOrtho(false, 100, 100);
		update(Gdx.graphics.getDeltaTime());

		box2dr.render(world, camera.combined);//.scl(PPM));

		//batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.end();

		world.step(1/60f, 6, 2);
		inputIpdate(Gdx.graphics.getDeltaTime());
	}

	private void inputIpdate(float delta) {
		int horizontalForce = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			horizontalForce -= 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 1;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			player.applyForceToCenter(0, 300, false);
		}

		player.setLinearVelocity(horizontalForce*5, player.getLinearVelocity().y);
	}
	private void update(float delta) {
		Vector3 position = camera.position;
		position.x = player.getPosition().x*PPM;
		position.y = player.getPosition().y*PPM;
		camera.position.set(position);
		camera.update();
	}
	@Override
	public void dispose () {
		world.dispose();
		box2dr.dispose();
		batch.dispose();
	}

	public Body createBox(int x, int y, int width, int height, boolean isStatic) {
		Body pBody;
		BodyDef def = new BodyDef();
		if(isStatic)
			def.type = BodyDef.BodyType.DynamicBody;
		else
			def.type = BodyDef.BodyType.StaticBody;
		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = false;
		pBody = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/PPM, height/PPM);

		pBody.createFixture(shape, 1.0f);

		shape.dispose();

		return pBody;
	}


}