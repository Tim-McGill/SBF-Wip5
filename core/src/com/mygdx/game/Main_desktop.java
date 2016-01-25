package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by tim on 2016-01-24.
 */
public class Main_desktop extends Game {
    boolean mobile;
    Game game;
    // sound
    Music SoundTrack;

    @Override
    public void create() {
        ///
        SoundTrack = Gdx.audio.newMusic(Gdx.files.internal("Adventure.mp3"));
        SoundTrack.setLooping(true);
//        SoundTrack.play();

        game = this;
        setScreen(new MainMenu(game,true));
    }
    public void render() {
        super.render();
    }
}