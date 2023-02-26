package org.splash.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;


public class StaticBody extends WrapBody{

    public StaticBody(float x, float y, float width, float height, World world) {
        super(x, y, width,height, world, BodyDef.BodyType.StaticBody);
    }



}
