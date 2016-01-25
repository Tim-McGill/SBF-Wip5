package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by tim on 12/4/2015.
 * our main game file
 * https://www.youtube.com/watch?v=DPIeERAm2ao
 */

public class Game2 extends ApplicationAdapter {
    Stage stage;

    //_____________main base code___________________________________________________________________
    @Override
    public void create(){
        stage = new Stage (new FillViewport(320,180));
        Player player = new Player();
        stage.addActor(player);
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render(){
       // stage.draw();
        System.out.println("game");

    }
    //______________________________________________________________________________________________

    //______________________________________________________________________________________________

    //______________________________________________________________________________________________
}
