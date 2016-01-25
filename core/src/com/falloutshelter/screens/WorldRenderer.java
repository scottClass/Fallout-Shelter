/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.falloutshelter.characters.Dweller;
import com.falloutshelter.rooms.Diner;
import com.falloutshelter.superclasses.Room;

/**
 *
 * @author scott
 */
public class WorldRenderer implements Screen {
    
    Array<Room> rooms;
    long startTime;
    long secondsPassed;
    
    private float energy, maxEnergy;
    private float food, maxFood;
    private float water, maxWater;
    private int Numdwellers, maxDwellers;
    
    private BitmapFont font;
    private SpriteBatch batch;
    
    private Array<Dweller> dwellers;
    
    public WorldRenderer() {
        dwellers = new Array<Dweller>();
        dwellers.add(new Dweller(1, 1, 1, 1));
        rooms = new Array<Room>();
        startTime = System.currentTimeMillis();
        energy = 50;
        food = 50;
        water = 50;
        maxEnergy = 100;
        maxFood = 100;
        maxWater = 100;
        font = new BitmapFont();
        batch = new SpriteBatch();
        font.setColor(Color.GREEN);
        System.out.println(dwellers.get(0));
        }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        secondsPassed = (System.currentTimeMillis() - startTime) / 1000;
        
        batch.begin();
        font.draw(batch, secondsPassed + "", 10, 20);
        batch.end();
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
