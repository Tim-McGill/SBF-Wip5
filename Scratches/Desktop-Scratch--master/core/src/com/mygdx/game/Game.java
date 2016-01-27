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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by tim on 10/11/2015.
 */
public class Game extends ApplicationAdapter   {
    SpriteBatch batch;
    Sprite spMenu,spSplash;
    Texture imgMenu,imgSplash;
    int nWidth,nHight, nScreen, nMenux,nMenuy,nSplashX,nSplashY,nDx,nDy,nPx=1040,nPy=840;
    float fAspect;
    TiledMap tiledMap;
    OrthographicCamera camera;
    Viewport viewport;
    TiledMapRenderer tiledMapRenderer;
    boolean arbInput[] = new boolean[6];
    // other files

    // base code
    //http://www.gamefromscratch.com/post/2014/05/01/LibGDX-Tutorial-11-Tiled-Maps-Part-2-Adding-a-character-sprite.aspx
    // veiw port from here http://www.gamefromscratch.com/post/2014/12/09/LibGDX-Tutorial-Part-17-Viewports.aspx
    //______________________________________________________________________________________________
    @Override
    public void create() {

    }
    @Override
    public void render() {

    }


    public void setScreen (int nScreen_){
        nScreen = nScreen_;
    }
    //______________________________________________________________________________________________

    // code that does stuff
    //______________________________________________________________________________________________

    public void input(boolean arbInput_[]){
        for (int i=0; i<5; i++){
            arbInput[i]=arbInput_[i];
        }
    }

    public void game(){
        if (arbInput[1]){
            nDy=1;
            System.out.println("up");
        }  if (arbInput[2]){
            nDy=-1;
            System.out.println("down");;
        }
        if (arbInput[3]){
            nDx=1;
            System.out.println("right");;

        }  if (arbInput[4]){
            nDx=-1;
            System.out.println("left");;

        } if (arbInput[5]){
            System.out.println("attack");
        }
        if (!arbInput[1]&&!arbInput[2]){
            nDy=0;
            System.out.println("end y");
        }
        if (!arbInput[3]&&!arbInput[4]){
            nDx=0;
            System.out.println("end x");
        }

    }


}
