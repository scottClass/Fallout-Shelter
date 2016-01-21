/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.falloutshelter.rooms.Room;

/**
 *
 * @author scott
 */
public class WorldRenderer implements Screen {
    
    Array<Room> rooms;
    
    public WorldRenderer() {
        rooms = new Array<Room>();
    }

    @Override
    public void render(float delta) {
        
    }
    
    @Override
    public void show() {
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
