package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by tim on 2016-01-03.
 */
public class Characterselect implements Screen {
    // vars
    OrthographicCamera camera;
    Viewport viewport;
    Sprite spMenuBackground,spPlayer;
    SpriteBatch batch;
    Stage stage;
    TextureAtlas taNewGame;
    Skin skNewGame;
    TextButton tbLeft,tbRight,tbContinue,tbBack;
    TextButton.TextButtonStyle tbsNewGame;
    BitmapFont font;
    int nSHeight, nSWidth,nPlayer=1;
    Game game;
    boolean mobile;


    public Characterselect(com.badlogic.gdx.Game game,boolean mobile_){
        this.game = game;
        mobile = mobile_;
    }
    @Override
    public void show() {
        //
        // BackGround Image
        spMenuBackground =  new Sprite(new Texture(Gdx.files.internal("_menu_Blur.png")));
        spMenuBackground.setSize(320, 180);
        // player image
        spPlayer = new Sprite(new Texture(Gdx.files.internal("player"+nPlayer+".png")));
        spPlayer.setSize(11,18);
        spPlayer.setPosition(160,90);
        //camera
        camera = new OrthographicCamera();
        // viewport
        viewport = new FillViewport(320, 180, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        font = new BitmapFont();
        //player = new unused_Player();
        font = new BitmapFont();
        //batch
        batch = new SpriteBatch();
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
        tbLeft = new TextButton("<", tbsNewGame);
        tbLeft.setSize(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight()/6);
        tbLeft.setPosition(Gdx.graphics.getWidth() / 18, (Gdx.graphics.getHeight() /5)*3 );
        tbLeft.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (nPlayer > 1) {
                    nPlayer--;
                   /// player.playerset(nPlayer);
                } else {
                    nPlayer = 4;
                    //player.playerset(nPlayer);
                }
                spPlayer = new Sprite(new Texture(Gdx.files.internal("player"+nPlayer+".png")));
                spPlayer.setPosition(160,90);
                return true;
            }
        });
        stage.addActor(tbLeft);
        //
        tbRight = new TextButton(">", tbsNewGame);
        tbRight.setSize(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight()/6);
        tbRight.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 18 - Gdx.graphics.getWidth() / 5, (Gdx.graphics.getHeight() /5)*3 );
        tbRight.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (nPlayer < 4) {
                    nPlayer++;
                   // player.playerset(nPlayer);
                } else {
                    nPlayer = 1;
                   // player.playerset(nPlayer);
                }
                spPlayer = new Sprite(new Texture(Gdx.files.internal("player"+nPlayer+".png")));
                spPlayer.setPosition(160,90);
                return true;
            }
        });
        stage.addActor(tbRight);
        //
        tbContinue = new TextButton("Continue", tbsNewGame);
        tbContinue.setSize(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight()/6);
        tbContinue.setPosition((Gdx.graphics.getWidth() / 3) * 2, 0);
        tbContinue.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(mobile){
                    game.setScreen(new Mobile_Control(game,nPlayer));
                }else {
                    game.setScreen(new GameScreen(game, nPlayer, mobile));
                }
                dispose();
                return true;
            }
        });
        stage.addActor(tbContinue);
        Gdx.input.setInputProcessor(stage);
        //
        tbBack = new TextButton("Back", tbsNewGame);
        tbBack.setSize(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight()/6);
        tbBack.setPosition((Gdx.graphics.getWidth() / 3)-Gdx.graphics.getWidth() / 5,0);
        tbBack.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenu(game,mobile));
                dispose();
                return true;
            }
        });
        stage.addActor(tbBack);

        // player
        unused_Player player = new unused_Player();
       // stage.addActor(player);
        player.setScale(10,10);
        player.setPosition(100,100);

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        stage.act();
        batch.begin();
        spMenuBackground.draw(batch);
        spPlayer.draw(batch);
        batch.end();
        stage.draw();

    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
