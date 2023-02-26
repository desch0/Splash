package org.splash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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


import java.util.ArrayList;

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

	private Sprite cardSprite;

	private ArrayList<DynamicBody> wrapList = new ArrayList<DynamicBody>();
	private DynamicBody player, card;
	private StaticBody  ground, ground2, leftWall, rightWall;


	private SpriteBatch batch;
	private Texture texture, cardTexture;
	StaticBody tryBody;
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();

		initFont();

		camera = new OrthographicCamera(50 * (windowWidth/windowHeight), 50);


		batch = new SpriteBatch();
		texture = new Texture("dove.png");
		cardTexture = new Texture("ace_of_clubs.png");

		world = new World(new Vector2(0, -10f), false);

		box2dr = new Box2DDebugRenderer();


		player = new DynamicBody(-400, 0,100, 100, world);
		card = new DynamicBody(-100, 1000, 200, 290, world);

		ground = new StaticBody(-100*10, 0, 100*20, 32, world);
		leftWall = new StaticBody(-100*30-30, 10*47, 32, 400, world);
		rightWall = new StaticBody(100*10, 10*47, 32, 400, world);
		ground2 = new StaticBody(-10000*10, -5000, 10000*20, 32, world);

		cardSprite = new Sprite(cardTexture);
		cardSprite.setSize(100/PPM, 145/PPM);

	}

	public void initFont() {
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("LiberationSans-Bold.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 5;
		fontParameter.color = Color.WHITE;
		font = fontGenerator.generateFont(fontParameter);
		font.setUseIntegerPositions(false);

	}
	public void printDebug() {
		String info = "Player body x: " + player.getBody().getPosition().x + "; y: "+player.getBody().getPosition().y+";\n";
		info += card.getBody().getPosition().x+"; "+ card.getBody().getPosition().x*PPM;
		font.draw(batch, info, (0),(0));
	}
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);

		Vector2 vector = new Vector2(player.getBody().getPosition().x, player.getBody().getPosition().y);
		camera.position.set(vector, 0);
		camera.update();


		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(cardTexture, card.getBody().getPosition().x - (200/PPM), card.getBody().getPosition().y-(290/PPM), 200/PPM*2, 290/PPM*2);
		printDebug();
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
			player.getBody().applyForceToCenter(0, 3000*16, true);
		}

		player.getBody().setLinearVelocity(horizontalForce*5, player.getBody().getLinearVelocity().y);
		player.getBody().applyForceToCenter(new Vector2(0, verticalForce*5), false);

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
	}
	@Override
	public void dispose () {
		texture.dispose();
		world.dispose();
		box2dr.dispose();
		batch.dispose();
	}


}