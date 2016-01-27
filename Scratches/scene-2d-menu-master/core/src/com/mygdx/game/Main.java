package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;


/**
 * Created by tim on 12/3/2015.
 * this is the main files it loads and unloads all of our other files
 * think of it like a empty sound stage
 */
public class Main extends ApplicationAdapter{
    // other files
    Menu menu;
    // vars
    int nScreen=1;
    boolean  arbInput[] = new boolean[6];


    //----------------------------------------------------------------------------------------------
    public void input(boolean arbInput_[]){
        arbInput= arbInput_;
    }
    public void nScreen(int nScreen_){
        nScreen=nScreen_;
    }
    //----------------------------------------------------------------------------------------------


    @Override
    public void  create(){
        // other files
        menu = new Menu();
        menu.create();
    }

    @Override
    public void  render(){
        if (nScreen==1){
            menu.render();
        } if (nScreen==2){
        }

    }
}
