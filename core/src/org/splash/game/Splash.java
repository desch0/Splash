package org.splash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


import static org.splash.game.util.Constants.PPM;

public class Splash extends ApplicationAdapter {


	private FreeTypeFontGenerator fontGenerator;
	private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
	BitmapFont font;

	private final float WORLD_TO_BOX = 32f;
	private float windowWidth;
	private float windowHeight;
	public static final float UNIT_SCALE=1f/16f;

	World world;
	private OrthographicCamera camera;
	private Box2DDebugRenderer box2dr;

	private Body player, box, ground, ground2, earthGround;

	private SpriteBatch batch;
	private Texture texture;
	StaticBody tryBody;
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		initFont();

		camera = new OrthographicCamera(10*5 * (windowWidth/windowHeight), 10*5);

		batch = new SpriteBatch();
		texture = new Texture("dove.png");

		world = new World(new Vector2(0, -10f), false);

		box2dr = new Box2DDebugRenderer();

		player = createBox(35, 35,64, 64, false);
		box = new DynamicBody(-300, 35, 64*2, 64*2, world);

		ground = new StaticBody(-64*10, 0, 64*20, 32, world);
		tryBody = new StaticBody(100, 500, 100, 100, world);
		earthGround = new StaticBody(-1500*64, -5000, 2*1500*64, 100, world);
		//tryBody = new StaticBody(-64*1500, -100, 64*1500, 100, world);

	}

	public void initFont() {
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("LiberationSans-Bold.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 20;
		fontParameter.color = Color.WHITE;
		font = fontGenerator.generateFont(fontParameter);
	}
	public void printDebug() {
		String info = "Player body x: " + player.getPosition().x + "; y: "+player.getPosition().y+";";
		font.draw(batch, info, windowWidth-400,windowHeight-20);
	}
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);

		Vector2 vector = new Vector2(player.getPosition().x, player.getPosition().y);
		camera.position.set(vector, 0);
		camera.update();

		batch.begin();
		batch.draw(texture, player.getPosition().x*PPM, player.getPosition().y*PPM);
		printDebug();
		//batch.draw(texture, player.getPosition().x*PPM+(texture.getWidth()/2), player.getPosition().y*PPM-(texture.getHeight()/2));
		batch.end();

		inputIpdate(Gdx.graphics.getDeltaTime());
		box2dr.render(world, camera.combined);

		world.step(1/60f, 6, 2);

	}

	private void inputIpdate(float delta) {
		int horizontalForce = 0;
		int verticalForce = 0;
		float speed = 1;

		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			speed *= 5;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			horizontalForce -= speed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			horizontalForce += speed;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			verticalForce += 1;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			verticalForce -= 1;
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			//player.applyForceToCenter(0, 300, true);
			player.applyForceToCenter(new Vector2(0, 300), true);
		}

		player.setLinearVelocity(horizontalForce*5, player.getLinearVelocity().y);
		player.applyForceToCenter(new Vector2(0, verticalForce*5), false);

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
	}
	@Override
	public void dispose () {
		texture.dispose();
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