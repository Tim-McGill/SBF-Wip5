package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

/**
 * Created by tim on 2016-01-03.
 * tiled map with stage came from here: http://mymilkedeek.net/2014/03/30/libgdxs-scene2d-and-tiledmap/
 *
 *  every thing is in its own object/method. I will have a list and description in a future up date
 */
public class GameScreen implements Screen, InputProcessor {
    // vars
    SpriteBatch batch;
    Sprite imgPlayer,imgDoor[]= new Sprite[4],imgKey[]= new Sprite[2],imgEnemies[]= new Sprite[91];

    //stage and game
    Game game;
    Stage stage;
    //tiled maps
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    ShapeRenderer shapeRenderer;
    //camera and view ports
    OrthographicCamera camera;
    boolean arbInput[] = new boolean[6], arbAgro[]= new boolean[91],bMobile;
    int nDx,nDy,nPx=320,nPy=2432, nLDx[]= new int[4],nLDy[]= new int[4],nLDp=0,nEx[]=new int[91],nEy[]= new int[91],nKey=0,nPlayer=1;
    int nKeyx[]=new int[2], nKeyy[] = new int[2], nKeyp=0, nTelx, nTely,nDEx,nDEy,nHealth=100, nDelay[]= new int[91],nBossHealth=8;
    int nEHealth[] = new int[91];
    // end of vars sorry for the massive amounts of ints
    public GameScreen(Game game, int nPlayer_,boolean mobile_){
        this.game = game;
        nPlayer = nPlayer_;
        bMobile =mobile_;
    }
    public void Mobile_intput(boolean arbInput_[]){
        arbInput=arbInput_;
        game();
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
            playerAtack();
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
        imgPlayer = new Sprite(new Texture(Gdx.files.internal("player"+nPlayer+".png")));
        for (int i=0; i<91; i++) {
            imgEnemies[i]= new Sprite(new Texture(Gdx.files.internal("goblin.png")));
            imgEnemies[i].setScale(5);
            nEHealth[i]=2;
            nDelay[i]=0;
            if (i<4) {
                imgDoor[i] = new Sprite(new Texture(Gdx.files.internal("BaseFLW.png")));
                if (i < 2) {
                    imgKey[i] = new Sprite(new Texture(Gdx.files.internal("BaseKey.png")));
                }
            }
        }
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new FillViewport(320,180,camera));
        stage.getViewport().setCamera(camera);
        stage.getViewport().apply();

        // tiled map
        tiledMap = new TmxMapLoader().load("level1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        objectplace();
            Gdx.input.setInputProcessor(this);
        camera.position.set(nPx, nPy, 0);
    }

    @Override
    public void render(float delta) {
        Wallcollision();
        pickup();
        TelCheck();
        ai();
        // camera and player
        camera.translate(nDx, nDy);
        // render
        tiledMapRenderer.setView((OrthographicCamera) stage.getCamera());
        tiledMapRenderer.render();
        stage.getCamera().update();
        stage.draw();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriterender();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(nPx - 120, nPy - 90, nHealth, 10);
        shapeRenderer.rect(nEx[90],nEy[90],nBossHealth*10,10);
        shapeRenderer.end();
    }
    public void spriterender() {
        batch.begin();
        for (int i=0; i<91;i++) {
            batch.draw(imgEnemies[i],nEx[i],nEy[i]);
            if (i < 4) {
                batch.draw(imgDoor[i], nLDx[i], nLDy[i]);
                if (i < 2) {
                    batch.draw(imgKey[i], nKeyx[i], nKeyy[i]);
                }
            }
        }
        batch.draw(imgPlayer, nPx, nPy);
        batch.end();
    }
    // this comes from here: https://www.youtube.com/watch?v=DOpqkaX9844
    // and from http://www.gamefromscratch.com/post/2014/06/18/LibGDX-Tutorial-11-Tiled-Maps-Part-3-Using-Properties-and-Tile-Map-animations.aspx
    public void objectplace () {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        int nNumES=0;
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                Object doors = cell.getTile().getProperties().get("Door");
                Object Keys= cell.getTile().getProperties().get("keys");
                Object Tel_= cell.getTile().getProperties().get("Tel_");
                Object BasicEnemies= cell.getTile().getProperties().get("BE");
                Object Player = cell.getTile().getProperties().get("player");
                Object boss = cell.getTile().getProperties().get("boss");
                if (doors != null) {
                    nLDx[nLDp] = x * 32;
                    nLDy[nLDp] = y * 32;
                    nLDp++;
                }
                if(Keys != null){
                    nKeyx[nKeyp] = x * 32;
                    nKeyy[nKeyp] = y * 32;
                    nKeyp++;
                }
                if(Tel_ != null){
                     nTelx = x * 32;
                     nTely = y * 32;
                }
                if(BasicEnemies != null){
                    for(int i =0; i<10;i++) {
                        int nW= i + 10*(nNumES);
                        if(i<2){
                            nEx[nW]=(x * 32)+4*i;
                            nEy[nW]=(y * 32)+3*i;
                        } if(i<4&&2<i){
                            nEx[nW]=(x * 32)-10-3*i;
                            nEy[nW]=(y * 32)+2*i;
                        }if(i<7&&4<i){
                            nEx[nW]=(x * 32)+2*i;
                            nEy[nW]=(y * 32)+32+2*i;
                        }if(i<10&&7<i){
                            nEx[nW]=(x * 32)+2*i;
                            nEy[nW]=(y * 32)-2-2*i;
                        }
                    }
                    nNumES++;
                }
                if(Player!=null){
                    nPx=16+x*32;
                    nPy=8+y*32;
                }
                if(boss!=null){
                    nEx[90]=x*32;
                    nEy[90]=y*32;
                }
            }
        }
    }
  public void Wallcollision()  {
      //setting the old values of x and y
      int noldX=nPx,noldY=nPy;
      TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
      // adding the new movement
      DoorCheck(noldY);
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
              nPy=noldY;
              nDx=nDy=0;
          }
      } else if (nDy<0) {
          TiledMapTileLayer.Cell cell = layer.getCell(nPx / 32, nPy / 32);
          Object property = cell.getTile().getProperties().get("blocked");
          if (property != null) {
              nPx = noldX;
              nPy=noldY;
              nDy=nDx = 0;
          }
      }  else if (nDy>0) {
          TiledMapTileLayer.Cell cell = layer.getCell(nPx / 32, nPy / 32);
          Object property = cell.getTile().getProperties().get("blocked");
          if (property != null) {
              nPx = noldX;
              nPy = noldY;
              nDy=nDx= 0;
          }
      }
    }
    public void DoorCheck( int noldY){
        for(int i=0; i<4; i++){
            if(nPx>=nLDx[i]-5&&nPx<nLDx[i]+43&&nPy>=nLDy[i]&&nPy<nLDy[i]+10){
                KeyCheck(i);
                nPy = noldY-1;
                camera.translate(0,-1);
                nDy=0;
                //nDx=0;
            }
        }
    }

    public void KeyCheck(int i_) {
        if(nKey>0){
            nLDy[i_]=nLDx[i_]=-1000;
            nKey--;
        }
    }

    public void pickup(){
        for (int i=0; i<2; i++) {
            if(nKeyx[i]>=nPx&&nKeyx[i]+6<=nPx+11&&nKeyy[i]>=nPy&&nKeyy[i]+11<=nPy+18){
                nKeyx[i]=nKeyy[i]=-1000;
                nKey++;
                System.out.println("key");
            }
        }
    }

    public void TelCheck(){
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        TiledMapTileLayer.Cell cell = layer.getCell((nPx)/32,nPy/32);
        Object property = cell.getTile().getProperties().get("Tel");
        if (property != null){
            nPx=nTelx;
            nPy=nTely;
            camera.position.set(nTelx, nTely, 0);
        }
    }

    public void ai (){
        for (int i=0; i<91; i++) {
                int nDex = ((nPx) - (nEx[i]));
                int nDey = ((nPy) - (nEy[i]));
            if (Math.abs(nDex)<80&&Math.abs(nDey)<80){
                arbAgro[i]=true;
                aimove(nDey, nDex,i);
            } else if (arbAgro[i]){
                aimove(nDey, nDex,i);
            }
            }
        }
    public void aimove(int nDey, int nDex, int i){
        if (nDey > 0) {
            nDEy=1;
            aimovecheck(nDex, nDey, i);
            nEy[i]=nEy[i]+nDEy;

        }
        if (nDey < 0) {
            nDEy=-1;
            aimovecheck(nDex, nDey, i);
            nEy[i]=nEy[i]+nDEy;

        }
        if (nDex > 0) {
            nDEx=1;
            aimovecheck(nDex, nDey, i);
            nEx[i]=nEx[i]+nDEx;

        }
        if (nDex < 0) {
            nDEx=-1;
            aimovecheck(nDex, nDey, i);
            nEx[i]=nEx[i]+nDEx;

        }
        if (nDx > 220 || nDy > 220) {
            arbAgro[i]=false;
        }
        aiAtack(nDex,nDey,i);
    }
    public void aimovecheck(int nDex, int nDey, int i){
        int noldX=nEx[i],noldY=nEy[i];
        /// wall check
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        if (nDex>0){
            TiledMapTileLayer.Cell cell = layer.getCell(( nEx[i]+29)/32, nEy[i]/32);
            Object property = cell.getTile().getProperties().get("blocked");
            if (property != null) {
                nEx[i]=noldX;
                nEy[i]=noldY;
                nDEx=nDEy=0;

            }
        } else if (nDex<0) {
            TiledMapTileLayer.Cell cell = layer.getCell( nEx[i]/32, nEy[i]/32);
            Object property = cell.getTile().getProperties().get("blocked");
            if (property != null) {
                nEx[i]=noldX;
                nEy[i]=noldY;
                nDEx=nDEy=0;

            }
        } else if (nDey<0) {
            TiledMapTileLayer.Cell cell = layer.getCell( nEx[i] / 32,  nEy[i] / 32);
            Object property = cell.getTile().getProperties().get("blocked");
            if (property != null) {
                nEx[i]=noldX;
                nEy[i]=noldY;
                nDEx=nDEy=0;

            }
        }  else if (nDey>0) {
            TiledMapTileLayer.Cell cell = layer.getCell( nEx[i] / 32,  nEy[i] / 32);
            Object property = cell.getTile().getProperties().get("blocked");
            if (property != null) {
                nEx[i]=noldX;
                nEy[i]=noldY;
                nDEx=nDEy=0;
            }
        }
        // other enemy check
        for (int w=0; w<90; w++){
            if (w!=i){
                if (nDex > 0) {
                    if (nEx[i]+10>=nEx[w]&&nEy[i]>=nEy[w]&&nEy[i]<=nEy[w]){
                        nEx[i]=noldX;
                        nDEx=0;
                        break;
                    }
                } else if (nDex < 0) {
                    if (nEx[i]<=nEx[w]+10&&nEy[i]>=nEy[w]&&nEy[i]<=nEy[w]){
                        nEx[i]=noldX;
                       // nDEx=0;
                        break;
                    }
                } else if (nDey < 0) {
                    if (nEy[i]+10>=nEy[w]&&nEx[i]>=nEx[w]&&nEx[i]<=nEx[w]){
                        nEy[i]=noldY;
                        nDy=0;
                        break;
                    }
                } else if (nDey > 0) {
                    if (nEy[i]<=nEy[w]+10&&nEx[i]>=nEx[w]&&nEx[i]<=nEx[w]){
                        nEy[i]=noldY;
                        nDy=0;
                        break;
                    }
                }
            }
        }
    }
    public void aiAtack(int nDex, int nDey, int i) {
        for (int w = 0; w < 91; w++) {
            if(nDelay[w]>40) {
                nDelay[w]=0;
                if(w<90) {
                    if (nDex > 0) {
                        if (nDex <= 21 && nEy[w] >= nPy && nEy[w] <= nPy + 18) {
                            nHealth--;
                        }
                    } else if (nDex < 0) {
                        if (nDex >= -21 && nEy[w] >= nPy && nEy[w] <= nPy + 18) {
                            nHealth--;
                        }
                    } else if (nDey < 0) {
                        if (nDey >= -47 && nEx[w] >= nPx && nEx[w] <= nPx + 11) {
                            nHealth--;
                        }
                    } else if (nDey > 0) {
                        if (nDey <= 47 && nEx[w] >= nPx && nEx[w] <= nPx + 11) {
                            nHealth--;
                        }
                    }
                } else {
                    if (nDex > 0) {
                        if (nDex <= 41 && nEy[w] >= nPy && nEy[w] <= nPy + 19) {
                            nHealth=nHealth-8;
                        }
                    } else if (nDex < 0) {
                        if (nDex >= -41 && nEy[w] >= nPy && nEy[w] <= nPy + 19) {
                            nHealth=nHealth-8;
                        }
                    } else if (nDey < 0) {
                        if (nDey >= -67 && nEx[w] >= nPx && nEx[w] <= nPx + 12) {
                            nHealth=nHealth-8;
                        }
                    } else if (nDey > 0) {
                        if (nDey <= 67 && nEx[w] >= nPx && nEx[w] <= nPx + 12) {
                            nHealth=nHealth-4;
                        }
                    }
                }
            }
            nDelay[w]++;
        }
        HealthCheck();
    }
    public void playerAtack(){
        for (int w = 0; w < 91; w++) {
            int nDex = ((nPx) - (nEx[w]));
            int nDey = ((nPy) - (nEy[w]));
            if(w<90) {
                if (Math.abs(nDex) < 15 && Math.abs(nDey) < 25) {
                    nEHealth[w]--;
                    if(nEHealth[w]<1) {
                        nEy[w] = nEx[w] = 100;
                    }
                }
            }
            else{
                if (Math.abs(nDex) < 15 && Math.abs(nDey) < 25) {
                   nBossHealth--;
                };
            }
            }
        }
    public void HealthCheck(){
        if(nHealth<1){
           game.setScreen(new End(game,false,bMobile));
        } else if(nBossHealth<1){
            game.setScreen((new End(game,true,bMobile)));
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
