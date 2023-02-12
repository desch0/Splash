package org.splash.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static org.splash.game.util.Constants.PPM;

public class DynamicBody extends Body {
    private Body pBody;
    private BodyDef def = new BodyDef();

    public DynamicBody(float x, float y, float width, float height, World world) {
        super(world, 0);
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = false;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/PPM, height/PPM);

        pBody.createFixture(shape, 1.0f);

        shape.dispose();

    }


}