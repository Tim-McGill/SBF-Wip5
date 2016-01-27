package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;;

/**
 * Created by tim on 12/3/2015.
 * this is the main menu screen for our game it will have buttons that do stuff.
 * most of this code is base of the deep Dark Taurock
 */
public class Menu extends ApplicationAdapter {
    // vars
    Sprite spMenuBackground;
    SpriteBatch batch;
    Stage stage;
    OrthographicCamera camera;
    Viewport viewport;
    TextureAtlas taNewGame;
    Skin skNewGame;
    TextButton tbNewGame,tbLoadGame,tbCredits;
    TextButton.TextButtonStyle tbsNewGame;
    BitmapFont font;
    int nSHeight, nSWidth;
    Main main;



    //
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        main = new Main();
        // BackGround Image
        spMenuBackground =  new Sprite(new Texture(Gdx.files.internal("background.png")));
        spMenuBackground.setSize(320, 180);
        //camera
        camera = new OrthographicCamera();
        // viewport
        viewport = new FillViewport(320, 180, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        //Stage
        stage = new Stage();
        nSHeight = Gdx.graphics.getHeight();
        nSWidth = Gdx.graphics.getWidth();
        //Buttons
        skNewGame = new Skin(); //setting up the button
        taNewGame = new TextureAtlas(Gdx.files.internal("MenuButton.atlas"));
        skNewGame.addRegions(taNewGame);
        tbsNewGame = new TextButton.TextButtonStyle();
        tbsNewGame.font = font;
        tbsNewGame.up = skNewGame.getDrawable("Button(UP)");
        tbsNewGame.down = skNewGame.getDrawable("Button(DOWN)");
        //tbsNewGame.checked = skNewGame.getDrawable("MenuButtonUp");
        //
        tbNewGame = new TextButton("New Game", tbsNewGame);
        tbNewGame.setSize(100, 50);
        tbNewGame.setPosition(110, 100);
        tbNewGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                main.nScreen(2);
                System.out.println("new game");
                return true;
            }
        });
        stage.addActor(tbNewGame);

    }
    //
    @Override
    public void render() {
        Gdx.input.setInputProcessor(stage);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        camera.update();
        spMenuBackground.draw(batch);
        batch.end();
        stage.draw();

    }
    // gets x and y of click and touch.
    public void Click(int nX, int nY){

    }

    // sets menu back to
    public void set(){
        spMenuBackground.setPosition(0,0);

    }
    public void rid(){
        spMenuBackground.getTexture().dispose();

    }
}
