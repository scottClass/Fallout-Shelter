/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.falloutshelter.characters.Dweller;
import com.falloutshelter.superclasses.Room;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author scott
 */
public class WorldRenderer implements Screen {

    private Array<Room> rooms;
    private long startTime;
    private long secondsPassed;
    private long nextSave;
    private int energy, maxEnergy;
    private int food, maxFood;
    private int water, maxWater;
    private int numDwellers, maxDwellers;
    private int caps;
    private BitmapFont font;
    private SpriteBatch batch;
    private Array<Dweller> dwellers;

    public WorldRenderer() {
        numDwellers = 0;
        System.out.println();
        dwellers = new Array<Dweller>();
        rooms = new Array<Room>();
        startTime = System.currentTimeMillis();
        energy = 50;
        food = 50;
        water = 50;
        maxEnergy = 100;
        maxFood = 100;
        maxWater = 100;
        caps = 500;
        maxDwellers = 20;
        font = new BitmapFont();
        batch = new SpriteBatch();
        font.setColor(Color.GREEN);
        secondsPassed = 0;
        nextSave = secondsPassed + 10;
        //Load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        secondsPassed = (System.currentTimeMillis() - startTime) / 1000;
        if (Gdx.input.isKeyJustPressed(Keys.L)) {
            //Load();
        } else if (Gdx.input.isKeyJustPressed(Keys.C)) {
            try {
                clearSave();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (Gdx.input.isKeyJustPressed(Keys.R)) {
            dwellers.add(new Dweller(1, 1, 1, 1));
            System.out.println(dwellers.get(numDwellers));
            numDwellers++;
        } else if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            for (Dweller d : dwellers) {
                System.out.println(d);
            }
        }

        if (nextSave == secondsPassed) {
            //Save();
            //System.out.println("Saved");
            nextSave = secondsPassed + 10;
        }

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

    private void Load() {
        try {
            // Open file to read from, named SavedObj.sav.
            FileInputStream saveFile = new FileInputStream("SaveGame.sav");

            // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);

            energy = (Integer) save.readObject();
            System.out.println(energy);
            food = (Integer) save.readObject();
            System.out.println(food);
            water = (Integer) save.readObject();
            System.out.println(water);
            maxEnergy = (Integer) save.readObject();
            System.out.println(maxEnergy);
            maxFood = (Integer) save.readObject();
            System.out.println(maxFood);
            maxWater = (Integer) save.readObject();
            System.out.println(maxWater);
            caps = (Integer) save.readObject();
            System.out.println(caps);
            maxDwellers = (Integer) save.readObject();
            System.out.println(maxDwellers);
            dwellers = (Array<Dweller>) save.readObject();
            rooms = (Array<Room>) save.readObject();
            //Clost the save file
            save.close();
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    private void Save() {

        try {
            // Open a file to write to, named SavedObj.sav.
            FileOutputStream saveFile = new FileOutputStream("SaveGame.sav");
            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            // Now we do the save.
            save.writeObject(energy);
            save.writeObject(food);
            save.writeObject(water);
            save.writeObject(maxEnergy);
            save.writeObject(maxFood);
            save.writeObject(maxWater);
            save.writeObject(caps);
            save.writeObject(maxDwellers);
            save.writeObject(dwellers);
            save.writeObject(rooms);

            // Close the file.
            save.close();
            // This also closes saveFile.
        } catch (Exception exc) {
            exc.printStackTrace();
            // If there was an error, print the info.
        }

    }

    private void clearSave() throws FileNotFoundException, IOException {
        FileOutputStream saveFile = new FileOutputStream("SaveGame.sav");
        saveFile.close();
        System.out.println("Cleared");
    }
}
