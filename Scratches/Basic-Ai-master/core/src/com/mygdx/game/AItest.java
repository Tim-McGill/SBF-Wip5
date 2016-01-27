package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by tim on 2016-01-04.
 *
 */
public class AItest implements Screen, InputProcessor {
    Game game;
    Stage stage;
    SpriteBatch batch;
    Texture imgPlayer, imgEnemy[] = new Texture[10];
    OrthographicCamera camera;
    int nPx=50, nPy=50,  nEx[]= new int[10], nEy[]= new int[10],nDx,nDy,nDex,nDey;
    boolean arbInput[] = new boolean[6];

    public AItest(Game game){
        this.game = game;
    }


    public void game() {
        //input handling
        if (arbInput[1]) {
            nDy = 2;
        }
        if (arbInput[2]) {
            nDy = -2;
        }
        if (arbInput[3]) {
            nDx = 2;
        }
        if (arbInput[4]) {
            nDx = -2;

        }
        if (arbInput[5]) {

        }
        if (!arbInput[1] && !arbInput[2]) {
            nDy = 0;
        }
        if (!arbInput[3] && !arbInput[4]) {
            nDx = 0;
        }
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();

        imgPlayer = new Texture("player1.png");
        for (int i=0; i<10; i++){
            imgEnemy[i] = new Texture("player3.png");
            nEx[i]=(nPx+60+3^i);
            nEy[i]=(nPy+70+4^i+9*i);

        }
        batch = new SpriteBatch();
        stage = new Stage(new FillViewport(320,180,camera));
        stage = new Stage(new FillViewport(320,180,camera));
        stage.getViewport().setCamera(camera);
        stage.getViewport().apply();
        Gdx.input.setInputProcessor(this);


    }

    @Override
    public void render(float delta) {
        AI();
        nPx=nPx+nDx;
        nPy=nPy+nDy;
        Gdx.gl.glClearColor(3, 3, 4, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().update();
        stage.draw();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgPlayer, nPx, nPy);
        for(int i=0; i<10; i++) {
            batch.draw(imgEnemy[i], nEx[i], nEy[i]);
        }
        batch.end();
    }

    public void AI(){
        for (int i=0; i<10; i++) {
            nDex = ((nPx) - (nEx[i]));
            nDey = ((nPy) - (nEy[i]));
            if (nDey > 0) {
                nEy[i]++;

            }
            if (nDey < 0) {
                nEy[i]--;

            }
            if (nDex > 0) {
                nEx[i]++;

            }
            if (nDex < 0) {
                nEx[i]--;

            }
            if (nDx > 100 || nDy > 100) {
            }
        }
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT){
            arbInput[4] = true;//left
        }
        if (keycode == Input.Keys.RIGHT){
            arbInput[3] = true;// right
        }
        if(keycode == Input.Keys.UP) {
            arbInput[1] = true;// up
        }
        if(keycode == Input.Keys.DOWN) {
            arbInput[2] = true;//down
        } if (keycode ==Input.Keys.SPACE){
            arbInput[5] = true;
            System.out.println("space");
        }
        game();
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            arbInput[4] = false;//left
        }
        if (keycode == Input.Keys.RIGHT) {
            arbInput[3] = false;// right
        }
        if (keycode == Input.Keys.UP) {
            arbInput[1] = false;// up
        }
        if (keycode == Input.Keys.DOWN) {
            arbInput[2] = false;//down
        }
        if (keycode == Input.Keys.SPACE) {
            arbInput[5] = false;
        }
        game();
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
