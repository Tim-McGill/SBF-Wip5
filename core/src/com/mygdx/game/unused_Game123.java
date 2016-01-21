package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * our main game
 * Created by tim on 10/11/2015.
 *
 */

/** Notes
 *  keys 0-4 are base keys 5-10 are red keys for nKx,nKy
 */
public class unused_Game123 extends ApplicationAdapter   {
    // vars
    SpriteBatch batch;
    Sprite spMenu;
    Texture imgEnemy,imgPlayer,imgBaseKey[]=new Texture[5], imgRedKey[]=new Texture[5],imgBaseWall[]=new Texture[5],imgRedwall[]=new Texture[5],imgNum[]= new Texture[3],imgHud;
    int  nScreen,nDx,nDy,nPx,nPy,nHx=390,nHy=510,nViewH,nViewW=320,nEx=550,nEy=600,nHealth =10, nAttack=5, nNx[]= new int[3], nNy[]= new int[3],nWx[]= new int[2], nWy[]= new int[2],nKx[]= new int[10], nKy[]= new int[10], nKn[]= new int[5];
    double dAspect,dViewH;
    TiledMap tiledMap;
    OrthographicCamera camera;
    Viewport viewport;
    TiledMapRenderer tiledMapRenderer;
    Rectangle recKey[]= new Rectangle[10];
    Rectangle recEnemy[]= new Rectangle[10];
    boolean arbInput[] = new boolean[6];
    // base code
    //http://www.gamefromscratch.com/post/2014/05/01/LibGDX-Tutorial-11-Tiled-Maps-Part-2-Adding-a-character-sprite.aspx
    // veiw port from here http://www.gamefromscratch.com/post/2014/12/09/LibGDX-Tutorial-Part-17-Viewports.aspx
    //______________________________________________________________________________________________
    @Override
    public void create() {
        // get screen size and set aspect
        dAspect = 1280/720;
        dViewH = (nViewW/dAspect);
        nViewH = (int) dViewH;
        // images
        batch = new SpriteBatch();
        spMenu =  new Sprite(new Texture(Gdx.files.internal("mainmenu.png")));
        spMenu.setSize(320, 180);
        imgEnemy = new Texture("player.png");
        spMenu.setPosition(340, 510);
        imgPlayer = new Texture("player.png");
        for (int i=0; i<5; i++) {
            imgBaseKey[i]=new Texture("BaseKey.png");
            imgRedKey[i]=new Texture("RedKey.png");
            imgBaseWall[i]=new Texture("BaseFLW.png");
            imgRedwall[i]=new Texture("RedFLW.png");
            nKn[i]=0;
        } for (int i=0; i<10; i++){
            nKy[i]=nKx[i]=-100000;
            //  recKey[i].setSize(6,11);
            // recKey[i].setPosition(nKx[i],nKy[i]);
        }
        nWx[0]=480;
        nWy[0]=832;
        imgNum[0]= new Texture("Num 0.png");
        imgNum[1]= new Texture("Num 0.png");
        imgNum[2]= new Texture("Num 0.png");
        imgHud = new Texture("BASE HUD.png");
        // tiled map
        tiledMap = new TmxMapLoader().load("MyMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        // viewport
         camera = new OrthographicCamera();
         viewport = new FillViewport(320, 180, camera);
         nPx = (int)500+(int)(camera.viewportWidth)/2;
         nPy = (int)600+(int)(camera.viewportHeight)/2;
         viewport.apply();
        camera.position.set(500, 600, 0);

    }
    @Override
    public void render() {
        // wall and door tests
        if(nWy[0]>0) {
            if (nPx > nWx[0] && nPx + 11 < nWx[0] + 35 && nPy == nWy[0]) {
                if (nKn[1] == 0) {
                    nDy = 0;
                    nHy--;
                    nPy = nWy[0] - 1;
                    camera.translate(0, -1);
                    System.out.println("hit door");
                } else if (nKn[1] > 0) {
                    nKn[1]--;
                    for (int w = 0; w < 2; w++) {
                        if (nKn[1] == w) {
                            imgNum[0] = new Texture("Num " + nKn[w] + ".png");
                        }
                        nWy[0] = -10000;
                    }

                }
            }
        }
            // camera and tiled
            camera.translate(nDx, nDy);
            nPx = nPx + nDx;
            nPy = nPy + nDy;
            nHx = nHx + nDx;
            nHy = nHy + nDy;
            for (int i = 0; i < 2; i++) {
                nNx[i] = nNx[i] + nDx;
                nNy[i] = nNy[i] + nDy;
            }
            camera.update();
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(imgBaseWall[0], nWx[0], nWy[0]);
            batch.draw(imgEnemy, nEx, nEy);
            batch.draw(imgPlayer, nPx, nPy);
            batch.draw(imgHud, nHx, nHy);
            batch.draw(imgNum[0], nHx + 24, nHy);
            batch.draw(imgNum[1], nHx + 48, nHy);
            batch.draw(imgNum[2], nHx + 71, nHy);
            batch.draw(imgBaseKey[0], nKx[0], nKy[0]);
            spMenu.draw(batch);
            batch.end();
    }
    @Override
    // this does not do anything
    public void resize(int nWidth, int nHeight){
        dAspect = nWidth/nHeight;
        nViewH = (int)(nViewW/dAspect);
        viewport.update(nViewW,nViewH);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    public void setScreen (int nScreen_){
        nScreen = nScreen_;
    }
    //______________________________________________________________________________________________

    // code that does stuff
    //______________________________________________________________________________________________

    public void MenuMove(){
        spMenu.setPosition(-10000, 0);
        nScreen=3;
        //mobile_launch.setScreen(nScreen);
    }
    public void input(boolean arbInput_[]){
        for (int i=0; i<6; i++){
            arbInput[i]=arbInput_[i];
        }
    }
    // main game code
    public void game(){
        //input handling
        //__________________________________________________________________________________________
        if (arbInput[1]){
            nDy=1;
        }  if (arbInput[2]){
            nDy=-1;
        }
        if (arbInput[3]){
            nDx=1;
        }  if (arbInput[4]){
            nDx=-1;

        } if (arbInput[5]){

        }
        if (!arbInput[1]&&!arbInput[2]){
            nDy=0;
            //System.out.println("end y");
        }
        if (!arbInput[3]&&!arbInput[4]){
            nDx=0;
            //
            // System.out.println("end x");
        }
        //__________________________________________________________________________________________
        //remove enemy drop key
        if (nHealth==0){
            if (nEx!=-100000){
                nKx[0]=nEx;
                nKy[0]=nEy;
            }
            nEx=nEy=-100000;
            imgEnemy.dispose();
        }
        // hit tests
        //pick up keys
        for(int i=0; i<10; i++){
            if(nKx[i]>=nPx&&nKx[i]+6<=nPx+11&&nKy[i]>=nPy&&nKy[i]+11<=nPy+18){
                nKx[i]=nKy[i]=-100000;
                if (i<5){
                    nKn[1]++;
                    for (int w=0; w<2; w++){
                        if(nKn[1]==w){
                            imgNum[0]= new Texture("Num "+nKn[w]+".png");
                        }
                    }
                } if (i>=5){
                    nKn[2]++;
                    for (int w=0; w<2; w++){
                        if(nKn[2]==w){
                            imgNum[1]= new Texture("Num "+nKn[w]+".png");
                        }
                    }
                }
            }
        }
        // end pick up keys
    }
}
