package org.splash.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static org.splash.game.util.Constants.PPM;

public class StaticBody {
    private Body body;
    private BodyDef def = new BodyDef();
    private PolygonShape shape;

    public StaticBody(float x, float y, float width, float height, World world) {
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = false;
        body = world.createBody(def);

        shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);

        body.createFixture(shape, 1.0f);
    }

    public void dispose() {
        shape.dispose();
    }
    public Body getBody() {
        return body;
    }


}
