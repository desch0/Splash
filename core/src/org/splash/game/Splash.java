package org.splash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import java.util.ArrayList;
import cards.Hand;

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

	private ArrayList<WrapBody> wrapList = new ArrayList<WrapBody>();

	Hand hand;
	CardWrap[] cards;
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

		world = new World(new Vector2(0, -10f), false);
		box2dr = new Box2DDebugRenderer();


		generateNewHand();

	}

	private void generateNewHand() {
		hand = new Hand(7);
		cards = new CardWrap[7];
		for(int i=0; i<cards.length; i++) {
			cards[i] = new CardWrap(hand.get(i), -1000+350*i,0, world);
		}
	}

	public void initFont() {
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("LiberationSans-Bold.ttf"));
		fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.size = 5;
		fontParameter.color = Color.WHITE;
		font = fontGenerator.generateFont(fontParameter);
		font.setUseIntegerPositions(false);
	}
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 0);

		camera.position.set(new Vector2(cards[3].body.getBody().getPosition().x, cards[3].body.getBody().getPosition().y), 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(CardWrap card: cards) {
			card.render(batch);
		}
		batch.end();

		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			generateNewHand();
		}

		box2dr.render(world, camera.combined);
		world.step(1/60f, 6, 2);
	}



	@Override
	public void dispose () {
		texture.dispose();
		world.dispose();
		box2dr.dispose();
		batch.dispose();
	}


}