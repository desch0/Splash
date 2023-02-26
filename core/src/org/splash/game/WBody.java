package org.splash.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static org.splash.game.util.Constants.PPM;

public class WBody {
    protected Body body;
    protected BodyDef def = new BodyDef();
    PolygonShape shape;
    protected float width, height;

    public WBody(World world) {

    }

    public void dispose() {
        shape.dispose();
    }
    public Body getBody() {
        return body;
    }


}