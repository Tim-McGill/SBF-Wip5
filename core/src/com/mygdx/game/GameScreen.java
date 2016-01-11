package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by tim on 2016-01-03.
 * tiled map with stage came from here: http://mymilkedeek.net/2014/03/30/libgdxs-scene2d-and-tiledmap/
 */
public class GameScreen implements Screen, InputProcessor {
    // vars
    SpriteBatch batch;
    Texture imgPlayer;
    //stage and game
    Game game;
    Stage stage;
    //tiled maps
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    TiledMapTileLayer collisionlayer;
    //camera and view ports
    OrthographicCamera camera;
    boolean arbInput[] = new boolean[6];
    boolean collisionX = false, collisionY = false;
    int nDx,nDy,nPx=320,nPy=2432;



    public GameScreen(Game game){
        this.game = game;
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

        imgPlayer = new Texture ("player1.png");


        batch = new SpriteBatch();
        stage = new Stage(new FillViewport(320,180,camera));
        stage.getViewport().setCamera(camera);
        stage.getViewport().apply();



        /**player actor
        Player player = new Player();
        MoveByAction move = new MoveByAction();
        move.setAmount(nDx, nDy);
        player.addAction(move);
        stage.addActor(player);
        **/
        // tiled map
        tiledMap = new TmxMapLoader().load("Testlevel.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Gdx.input.setInputProcessor(this);

        //player.setPosition(320,2432);
        camera.position.set(320,2432 , 0);


    }

    @Override
    public void render(float delta) {
        // camera and player
        camera.translate(nDx, nDy);
        collision();

        // render
        tiledMapRenderer.setView((OrthographicCamera) stage.getCamera());
        tiledMapRenderer.render();
        stage.getCamera().update();
       // stage.act();
        //stage.draw();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgPlayer, nPx, nPy);
        batch.end();


    }
    // this comes from here: https://www.youtube.com/watch?v=DOpqkaX9844
    public void collision (){
        TiledMapTileLayer collisionlayer = (TiledMapTileLayer)tiledMap.getLayers().get("walls and wall  caps");
        float noldX=nPx, noldY = nPy, tiledwidth = collisionlayer.getTileWidth(), tiledHeight = collisionlayer.getTileHeight();
        collisionX = new Boolean(false); collisionY = new Boolean(false);
        nPx=nPx+nDx;
        // x to the left
        if (nDx<0){
            // top left
            collisionX = collisionlayer.getCell((int)(nPx/tiledwidth),(int)(nPy+18/tiledHeight)).getTile().getProperties().containsKey("blocked");

            // mid left
            if (!collisionX)
            collisionX = collisionlayer.getCell((int)(nPx/tiledwidth),(int)((nPy+18)/2/tiledHeight)).getTile().getProperties().containsKey("blocked");
            // bottom left
            if (!collisionX)
            collisionX = collisionlayer.getCell((int)(nPx/tiledwidth),(int)(nPy/tiledHeight)).getTile().getProperties().containsKey("blocked");
        }
        // x to the right
        else if (nDx>0){
            //top right
            collisionX = collisionlayer.getCell((int)(nPx+11/tiledwidth),(int)(nPy+18/tiledHeight)).getTile().getProperties().containsKey("blocked");

            //mid right
            if (!collisionX)
                collisionX = collisionlayer.getCell((int)(nPx+11/tiledwidth),(int)((nPy+18)/2/tiledHeight)).getTile().getProperties().containsKey("blocked");

            //bottom right
            if (!collisionX)
                collisionX = collisionlayer.getCell((int)(nPx+11/tiledwidth),(int)(nPy/tiledHeight)).getTile().getProperties().containsKey("blocked");

        }
        //
        if (collisionX){
            System.out.println("XC");
            nPx=(int)noldX;
            nDx=0;
        }
        nPy=nPy+nDy;
        // y up
        if (nDx>0){
            // up left
                collisionY = collisionlayer.getCell((int)((nPx)/tiledwidth),(int)(nPy+18/tiledHeight)).getTile().getProperties().containsKey("blocked");
            // up mid
            if (!collisionY)
                collisionY = collisionlayer.getCell((int)((nPx+11)/2/tiledwidth),(int)(nPy+18/tiledHeight)).getTile().getProperties().containsKey("blocked");
            // up right
            if (!collisionY)
                collisionY = collisionlayer.getCell((int)((nPx+11)/tiledwidth),(int)(nPy+18/tiledHeight)).getTile().getProperties().containsKey("blocked");

        }
        // y down
        else if (nDx<0){
            // bottom left
            collisionY = collisionlayer.getCell((int)(nPx/tiledwidth),(int)(nPy/tiledHeight)).getTile().getProperties().containsKey("blocked");
            // bottom mid
            if(!collisionY){
                collisionY = collisionlayer.getCell((int)((nPx+11)/2/tiledwidth),(int)(nPy/tiledHeight)).getTile().getProperties().containsKey("blocked");
            }
            // bottom right
            if (!collisionY)
                collisionY = collisionlayer.getCell((int)((nPx+11)/tiledwidth),(int)(nPy/tiledHeight)).getTile().getProperties().containsKey("blocked");
        }

        if (collisionY){
            System.out.println("YC");
            nPy=(int)noldY;
            nDy=0;
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
}
