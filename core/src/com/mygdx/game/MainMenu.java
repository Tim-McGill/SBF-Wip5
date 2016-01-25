package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
public class MainMenu implements Screen {
    // vars
    OrthographicCamera camera;
    Viewport viewport;
    Sprite spMenuBackground;
    SpriteBatch batch;
    Stage stage;
    TextureAtlas taNewGame;
    Skin skNewGame;
    TextButton tbNewGame,tbContinue,tbCredits;
    TextButton.TextButtonStyle tbsNewGame;
    BitmapFont font;
    int nSHeight, nSWidth;
    Game game;
    boolean mobile;


    public MainMenu(Game game,boolean mobile_){
        this.game = game;
        mobile = mobile_;
    }
    @Override
    public void show() {
        // BackGround Image
        spMenuBackground =  new Sprite(new Texture(Gdx.files.internal("_menu.png")));
        spMenuBackground.setSize(320, 180);
        //camera
        camera = new OrthographicCamera();
        // viewport
        viewport = new FillViewport(320, 180, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
        tbNewGame = new TextButton("New Game", tbsNewGame);
        tbNewGame.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 6);
        tbNewGame.setPosition(Gdx.graphics.getWidth() / 2 - (Gdx.graphics.getWidth() / 3) / 2, Gdx.graphics.getHeight() / 2 - Gdx.graphics.getHeight() / 6);
        tbNewGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Characterselect(game,mobile));
                return true;
            }
        });
        stage.addActor(tbNewGame);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        stage.act();
        batch.begin();
        spMenuBackground.draw(batch);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
