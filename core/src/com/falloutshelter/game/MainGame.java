/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.game;

import com.badlogic.gdx.Screen;

/**
 *
 * @author scott
 */
public class MainGame implements Screen {

    WorldRenderer renderer;
    
    private float energy;
    private float food;
    private float water;
    
    public MainGame() {
        renderer = new WorldRenderer();
    }

    @Override
    public void render(float deltaTime) {
        renderer.render(deltaTime);
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
