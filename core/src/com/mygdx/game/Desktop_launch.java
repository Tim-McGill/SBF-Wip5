/**
 * desktop  Launcher, this contains the desktop controls, it also loads in the game
 */

package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
// got most of the code from here
// http://www.gamefromscratch.com/post/2014/05/01/LibGDX-Tutorial-11-Tiled-Maps-Part-2-Adding-a-character-sprite.aspx

public class Desktop_launch extends ApplicationAdapter implements InputProcessor {
    // vars and other things
  int nScreen =1;
    boolean arbInput[] = new boolean[6];// 4 directions 1 attack 1 pause
    // other files
    Main_mobile main;
    GameScreen gameScreen;

    // base code
    //______________________________________________________________________________________________
    @Override
    public void create() {
        // other files
        main = new Main_mobile();
        main.create();
        Gdx.input.setInputProcessor(this);

    }


    // input this does noting now
    //______________________________________________________________________________________________
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
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
       // gameScreen.input(arbInput);
        //gameScreen.game();
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT){
            arbInput[4] = false;//left
        }
        if (keycode == Input.Keys.RIGHT){
            arbInput[3] = false;// right
        }
        if(keycode == Input.Keys.UP) {
            arbInput[1] = false;// up
        }
        if(keycode == Input.Keys.DOWN) {
            arbInput[2] = false;//down
        }if (keycode ==Input.Keys.SPACE){
            arbInput[5] = false;
        }
        //gameScreen.input(arbInput);
        //game.game();
        return false;
    }
    //needed to use implements InputProcessor don't know why, but it's here
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
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
    public boolean keyTyped(char character) {
        return false;
    }
    //______________________________________________________________________________________________
}