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
    Texture imgPlayer,imgDoor[]= new Texture[4];
    //stage and game
    Game game;
    Stage stage;
    //tiled maps
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    //camera and view ports
    OrthographicCamera camera;
    boolean arbInput[] = new boolean[6];
    boolean collisionX = false, collisionY = false;
    int nDx,nDy,nPx=320,nPy=2432, nLDx[]= new int[4],nLDy[]= new int[4],nLDp=0;



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
        for (int i=0; i<4; i++) {
            imgDoor[i]=new Texture("BaseFLW.png");
           // nLDx[i]=nLDy[i]=0;
        }
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
        objectplace();
        Gdx.input.setInputProcessor(this);

        //player.setPosition(320,2432);
        camera.position.set(320,2432 , 0);
    }

    @Override
    public void render(float delta) {
        Wallcollision();
        // camera and player
        camera.translate(nDx, nDy);

        // render
        tiledMapRenderer.setView((OrthographicCamera) stage.getCamera());
        tiledMapRenderer.render();
        stage.getCamera().update();
       // stage.act();
        //stage.draw();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgPlayer, nPx, nPy);
        for (int i=0; i<4;i++){
            batch.draw(imgDoor[i], nLDx[i],nLDy[i]);
        }
        batch.end();


    }
    // this comes from here: https://www.youtube.com/watch?v=DOpqkaX9844
    // and from http://www.gamefromscratch.com/post/2014/06/18/LibGDX-Tutorial-11-Tiled-Maps-Part-3-Using-Properties-and-Tile-Map-animations.aspx
    public void objectplace () {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                Object property = cell.getTile().getProperties().get("Door");
                if (property != null) {
                    nLDx[nLDp] = x * 32;
                    nLDy[nLDp] = y * 32;
                    nLDp++;

                } else {
                    System.out.println("no door at x " + x + " y " + y);
                    System.out.println("");
                }
            }
        }
    }
  public void Wallcollision()  {
      //setting the old values of x and y
      int noldX=nPx,noldY=nPy;
      TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
      // adding the new movement
      nPx=nPx+nDx;
      nPy=nPy+nDy;
      //moving right
      if (nDx>0){
          TiledMapTileLayer.Cell cell = layer.getCell((nPx+11)/32,nPy/32);
          Object property = cell.getTile().getProperties().get("blocked");
          if (property != null) {
            nPx=noldX;
            nPy=noldY;
              nDx=nDy=0;
          }
      } else if (nDx<0) {
          TiledMapTileLayer.Cell cell = layer.getCell(nPx/32,nPy/32);
          Object property = cell.getTile().getProperties().get("blocked");
          if (property != null) {
              nPx=noldX;
             // nPy=noldY;
              nDx=nDy=0;
          }
      }
      else if (nDy<0) {
          TiledMapTileLayer.Cell cell = layer.getCell(nPx / 32, nPy / 32);
          Object property = cell.getTile().getProperties().get("blocked");
          if (property != null) {
              //nPx = noldX;
              nPy=noldY;
              nDy=nDx = 0;
          }
      }  else if (nDy>0) {
          TiledMapTileLayer.Cell cell = layer.getCell(nPx / 32, nPy / 32);
          Object property = cell.getTile().getProperties().get("blocked");
          if (property != null) {
              //nPx = noldX;
              nPy = noldY;
              nDy=nDx = 0;
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
}
