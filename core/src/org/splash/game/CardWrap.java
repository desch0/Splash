package org.splash.game;

import cards.Card;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import static org.splash.game.util.Constants.PPM;

public class CardWrap {

    private float width = 125;
    private float height = width*1.45f;

    Card card;

    StaticBody body;
    Texture texture;

    private String getFileName() {
        String rank;
        if(card.getValue()<11) rank = ""+(card.getValue());
        else rank = ""+(card.getRank());
        return ("cards/"+rank+"_of_"+card.getSuit()+".png").toLowerCase();
    }

    public CardWrap(Card card, float x, float y, World world) {
        this.card = card;
        body = new StaticBody(x, y, width, height, world);
        texture = new Texture(getFileName());
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, body.getBody().getPosition().x - (body.width/PPM),
                body.getBody().getPosition().y-(body.height/PPM),
                body.width/PPM*2, body.height/PPM*2);
    }

}
