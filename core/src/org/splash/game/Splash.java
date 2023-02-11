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

	private final float WORLD_TO_BOX = 32f;
	private float windowWidth;
	private float windowHeight;
	public static final float UNIT_SCALE=1f/16f;
	SpriteBatch batch;

	World world;
	private OrthographicCamera camera;
	private Box2DDebugRenderer box2dr;

	private Body player, roof, ground, ground2, earthGround;

	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(10*5 * (windowWidth/windowHeight), 10*5);

		world = new World(new Vector2(0, -9.807f), false);

		batch = new SpriteBatch();
		box2dr = new Box2DDebugRenderer();

		player = createBox(35, 35,32, 32, false);

		ground = createBox(-64*10, 0, 64*20, 32, true);
		ground2 = createBox(0, -1000, 64*20, 32, true);
		earthGround = createBox(-64*100, -5000, 64*200, 64, true);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);

		Vector2 vector = new Vector2(player.getPosition().x, player.getPosition().y);
		camera.position.set(vector, 0);
		camera.update();

		inputIpdate(Gdx.graphics.getDeltaTime());
		box2dr.render(world, camera.combined);

		world.step(1/60f, 6, 2);
	}

	private void inputIpdate(float delta) {
		int horizontalForce = 0;
		int verticalForce = 0;

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			horizontalForce -= 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			horizontalForce += 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			verticalForce += 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			verticalForce -= 1;
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			//player.applyForceToCenter(0, 300, true);
			player.applyForceToCenter(new Vector2(0, 3000), true);
		}

		player.setLinearVelocity(horizontalForce*100, player.getLinearVelocity().y);
		player.applyForceToCenter(new Vector2(0, verticalForce*5), false);
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
		if(isStatic) {
			def.type = BodyDef.BodyType.StaticBody;
		}
		else {
			def.type = BodyDef.BodyType.DynamicBody;
		}

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