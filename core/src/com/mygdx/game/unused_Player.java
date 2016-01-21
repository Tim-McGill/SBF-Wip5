package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by tim on 12/10/2015.
 *
 */
// https://www.youtube.com/watch?v=DPIeERAm2ao
public class unused_Player extends Actor {
    int nPlayer=1;
    @Override
    public void act(float delta)
    {
        super.act(delta);
    }

    Sprite spPlayer =  new Sprite(new Texture(Gdx.files.internal("player"+nPlayer+".png")));
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(spPlayer,getX(),getY());

    }
}
