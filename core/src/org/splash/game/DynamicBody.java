package org.splash.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static org.splash.game.util.Constants.PPM;

public class DynamicBody {
    private Body body;
    private BodyDef def = new BodyDef();
    private PolygonShape shape;
    private float width, height;

    public DynamicBody(float x, float y, float width, float height, World world) {
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = false;
        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/PPM, height/PPM);

        body.createFixture(shape, 1.0f);

        shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        body.createFixture(shape, 1.0f);

        this.width = width;
        this.height = height;

    }

    public void dispose() {
        shape.dispose();
    }

    public Body getBody() {
        return body;
    }


}