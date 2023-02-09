package org.splash.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class Ball {
    private Texture img;
    private Body body;
    private final float radius = 100f;
    public Ball(float x, float y, World world) {

        body = createCircleBody(x, y, radius, world);
        img = new Texture("ball.png");
    }

    public void draw(SpriteBatch batch) {
        body.applyForce(1.0f, 0.0f, body.getPosition().x, body.getPosition().y, true);
        batch.draw(img, body.getPosition().x, body.getPosition().y, radius * Splash.UNIT_SCALE, radius*Splash.UNIT_SCALE);
    }

    private Body createCircleBody(float x, float y, float radius, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(5,10);
        Body body = world.createBody(def);

        CircleShape circle = new CircleShape();
        circle.setPosition(new Vector2(0, 0));
        circle.setRadius(radius);
        FixtureDef fixture = new FixtureDef();
        fixture.shape = circle;
        fixture.density = 5f;
        fixture.friction = 0.4f;
        fixture.restitution = 0.6f;

        body.setTransform(x, y, 0);

        return body;
    }
}