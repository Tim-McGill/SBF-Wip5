package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


/**
 * Created by tim on 12/3/2015.

 */
public class Main extends Game {
    Game game;
    // sound
    Music SoundTrack;

    @Override
    public void create() {
        SoundTrack = Gdx.audio.newMusic(Gdx.files.internal("Adventure.mp3"));
        SoundTrack.setLooping(true);
        SoundTrack.play();

        game = this;
        setScreen(new MainMenu(game));
    }
    public void render() {
        super.render();
    }
}
