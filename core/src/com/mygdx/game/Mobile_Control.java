/**
 * Mobile Launcher, this contains the Mobile controls, it also loads in the game
 */
package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// got most of the code from here
// http://www.gamefromscratch.com/post/2014/05/01/LibGDX-Tutorial-11-Tiled-Maps-Part-2-Adding-a-character-sprite.aspx
public class Mobile_Control extends ApplicationAdapter implements InputProcessor,Screen {
    // vars and other things
    Texture imgBackground, imgNub;
    SpriteBatch batch;
    int nJoyStickX = -200, nJoyStickY = -200, nJoyStickNubX = -136, nJoyStickNubY = -136, nDx, nDy, nScreen =1, nInput=1,nPlayer;
    boolean arbInput[] = new boolean[6];// 4 directions 1 attack 1 pause
    Game game;
    GameScreen gameScreen;
    // other files
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(screenX);
        // move joystick into view
            if (nInput == 1) {
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    nJoyStickX = screenX - 100;
                    nJoyStickY = Gdx.graphics.getHeight() - screenY - 100;
                    nJoyStickNubX = nJoyStickX + 64;
                    nJoyStickNubY = nJoyStickY + 64;
                }
                if (screenX > Gdx.graphics.getWidth() / 2) {
                    arbInput[5] = true; //attack
                }
            }

        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (nInput==1) {
            nJoyStickX = -200;
            nJoyStickY = -200;
            nJoyStickNubX = nJoyStickX + 64;
            nJoyStickNubY = nJoyStickY + 64;
            nDx = nDy = 0;
            for (int i = 0; i < 5; i++) {
                arbInput[i] = false;
            }
            gameScreen.Mobile_intput(arbInput);
        } else if(nInput==2) {
            // thomas' touch around player code will go here.
            // we didn't have enough time to implement
        }
        render();
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // find and move the nub if it's on the left side of the screen
        // tried to use this http://www.bigerstaff.com/libgdx-touchpad-example/
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    nJoyStickNubX = screenX - 36;
                    nJoyStickNubY = Gdx.graphics.getHeight() - screenY - 36;
                    // limit the nub
                    if (nJoyStickNubX > nJoyStickX + 164) {
                        nJoyStickNubX = nJoyStickX + 154;
                    }
                    if (nJoyStickNubX + 36 < nJoyStickX) {
                        nJoyStickNubX = nJoyStickX - 36;
                    }
                    if (nJoyStickNubY > nJoyStickY + 164) {
                        nJoyStickNubY = nJoyStickY + 154;
                    }
                    if (nJoyStickNubY + 36 < nJoyStickY) {
                        nJoyStickNubY = nJoyStickY - 36;
                    }
                    // setting input booleans
                    nDx = ((nJoyStickNubX + 36) - (nJoyStickX + 100));
                    nDy = ((nJoyStickNubY + 36) - (nJoyStickY + 100));
                    if (nDy > 0) {
                        arbInput[1] = true;// up
                        arbInput[2] = false;
                    }
                    if (nDy < 0) {
                        arbInput[1] = false;
                        arbInput[2] = true;//down
                    }
                    if (nDx > 0) {
                        arbInput[3] = true;// right
                        arbInput[4] = false;
                    }
                    if (nDx < 0) {
                        arbInput[4] = true;//left
                        arbInput[3] = false;
                    }
                   gameScreen.Mobile_intput(arbInput);
                }
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
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public void show() {
        gameScreen = new GameScreen(game,nPlayer,true);
        gameScreen.show();
        // images
        batch = new SpriteBatch();
        imgBackground = new Texture("touchBackground.png");
        imgNub = new Texture("touchKnob.png");
        // input
        System.out.println("Android");
        Gdx.input.setInputProcessor(this);

    }
    @Override
    public void render(float delta) {
        gameScreen.render(delta);
        // joy stick and buttons
        batch.begin();
        batch.draw(imgBackground, nJoyStickX, nJoyStickY);
        batch.draw(imgNub, nJoyStickNubX, nJoyStickNubY);
        batch.end();
    }
    public Mobile_Control(Game game, int nPlayer_ ){
        this.game = game; ;
        nPlayer=nPlayer_;
    }
    @Override
    public void hide() {
    }
}