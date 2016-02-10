/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.falloutshelter.characters.Dweller;
import com.falloutshelter.rooms.Diner;
import com.falloutshelter.rooms.LivingQuarters;
import com.falloutshelter.rooms.PowerGenerator;
import com.falloutshelter.rooms.WaterPurification;
import static com.falloutshelter.game.WorldRenderer.BuildState.DINER;
import static com.falloutshelter.game.WorldRenderer.BuildState.LIVINGQ;
import static com.falloutshelter.game.WorldRenderer.BuildState.MEDBAY;
import static com.falloutshelter.game.WorldRenderer.BuildState.NOTHING;
import static com.falloutshelter.game.WorldRenderer.BuildState.POWERG;
import static com.falloutshelter.game.WorldRenderer.BuildState.SCIENCEL;
import static com.falloutshelter.game.WorldRenderer.BuildState.WATERP;
import static com.falloutshelter.game.WorldRenderer.State.BUILD;
import static com.falloutshelter.game.WorldRenderer.State.SELECT;
import com.falloutshelter.rooms.Medbay;
import com.falloutshelter.rooms.ScienceLab;
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
    private int radAway, maxRadAway;
    private int stimpak, maxStimpak;
    private int numRooms;
    private int caps;
    private BitmapFont font;
    private SpriteBatch batch;
    private Rectangle buildIconRect;
    private Array<Dweller> dwellers;
    private State currentFirstState;
    private BuildState currentBuildState;
    private boolean buttonDown;
    private Dweller currentSelected;

    /**
     * Selects between building mode and selecting mode
     */
    public enum State {

        BUILD, SELECT,
    }

    /**
     * When state is build these are the separate states for each room type
     */
    public enum BuildState {

        DINER, POWERG, WATERP, LIVINGQ, MEDBAY, SCIENCEL, NOTHING,
    }

    public WorldRenderer() {
        AssetManager.Load();
        currentFirstState = SELECT;
        currentBuildState = DINER;
        currentSelected = null;

        radAway = 0;
        maxRadAway = 15;
        stimpak = 0;
        maxStimpak = 20;

        numDwellers = 0;
        numRooms = 0;

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

        buildIconRect = new Rectangle(Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40, 30, 30);
        buttonDown = false;
        //Load();
    }

    /**
     * Draws rooms and Dwellers in the arrayLists.
     * @param delta 
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        CalculateLogic();

        batch.begin();
        font.draw(batch, secondsPassed + "", 10, 20);
        for (Dweller d : dwellers) {
            batch.draw(AssetManager.test, d.getX(), d.getY(), d.getWidth(), d.getHeight());
        }
        for (Room r : rooms) {
            batch.draw(AssetManager.test, r.getX(), r.getY(), r.getWidth(), r.getHeight());
        }
        batch.draw(AssetManager.buildIcon, buildIconRect.getX(), buildIconRect.getY(), buildIconRect.getWidth(), buildIconRect.getHeight());
        batch.draw(AssetManager.test, 50, 50, 100, 50);
        batch.end();
    }

    /**
     * Does necessary math and logic outside of render method
     */
    public void CalculateLogic() {
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
            dwellers.add(new Dweller(20, 20, 50, 50));
            System.out.println(dwellers.get(numDwellers));
            numDwellers++;
        } else if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            for (Dweller d : dwellers) {
                System.out.println(d);
            }
        } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            buttonDown = true;
        } else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buttonDown) {
            buttonDown = false;
            int clickX = Gdx.input.getX();
            int clickY = (Gdx.graphics.getHeight() - Gdx.input.getY());
            Rectangle rect = new Rectangle(clickX, clickY, 5, 5);
            if (currentFirstState == BUILD) {
                if (rect.overlaps(buildIconRect)) {
                    currentFirstState = SELECT;
                    currentBuildState = NOTHING;
                    System.out.println(currentFirstState);
                }
                if (currentBuildState == DINER) {
                    if (getCost("diner")) {
                        rooms.add(new Diner(clickX, clickY, 100, 50));
                        numRooms++;
                        System.out.println(caps);
                    }
                } else if (currentBuildState == POWERG) {
                    if (getCost("powergenerator")) {
                        rooms.add(new PowerGenerator(clickX, clickY, 100, 50));
                        numRooms++;
                        System.out.println(caps);
                    }
                } else if (currentBuildState == WATERP) {
                    if (getCost("waterpurification")) {
                        rooms.add(new WaterPurification(clickX, clickY, 100, 50));
                        numRooms++;
                        System.out.println(caps);
                    }
                } else if (currentBuildState == LIVINGQ) {
                    if (getCost("livingquarters")) {
                        rooms.add(new LivingQuarters(clickX, clickY, 100, 50));
                        numRooms++;
                        System.out.println(caps);
                    }
                } else if (currentBuildState == MEDBAY) {
                    if (getCost("medbay")) {
                        rooms.add(new Medbay(clickX, clickY, 100, 50));
                        numRooms++;
                        System.out.println(caps);
                    }
                } else if (currentBuildState == SCIENCEL) {
                    if (getCost("sciencelab")) {
                        rooms.add(new ScienceLab(clickX, clickY, 100, 50));
                        numRooms++;
                        System.out.println(caps);
                    }
                } else {
                    System.out.println("No build type selected");
                }
            } else if (currentFirstState == SELECT) {
                if (rect.overlaps(buildIconRect)) {
                    currentFirstState = BUILD;
                    System.out.println(currentFirstState);
                }
                for (Dweller d : dwellers) {
                    if (rect.overlaps(d.getRect())) {
                        currentSelected = d;
                    }
                }
            }
        }

        if (nextSave == secondsPassed) {
            //Save();
            //System.out.println("Saved");
            nextSave = secondsPassed + 10;
        }
    }

    /**
     * Determines what a room would cost factoring in how many others have been
     * built.
     *
     * @param desiredBuild the room that is to be built
     * @return whether or not the play can afford to build the room
     */
    public boolean getCost(String desiredBuild) {
        if (desiredBuild.equals("diner") || desiredBuild.equals("powergenerator")
                || desiredBuild.equals("waterpurification") || desiredBuild.equals("livingquarters")) {
            int n = 0;
            for (Room r : rooms) {
                if (r.getRoomName().equals(desiredBuild)) {
                    n++;
                }
            }
            if ((100 + (25 * n)) > caps) {
                return false;
            } else {
                caps = caps - (100 + (25 * n));
                return true;
            }
        } else if (desiredBuild.equals("medbay") || desiredBuild.equals("sciencelab")) {
            int n = 0;
            for (Room r : rooms) {
                if (r.getRoomName().equals(desiredBuild)) {
                    n++;
                }
            }
            if ((400 + (100 * n)) > caps) {
                return false;
            } else {
                caps = caps - (400 + (100 * n));
                return true;
            }
        }
        //If statement reaches here something went wrong
        return false;
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

    /**
     * Loads data from SaveGame.sav
     */
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

    /**
     * Saves data to SaveGame.sav
     */
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

    /**
     * Clears the save file (Testing purposes).
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void clearSave() throws FileNotFoundException, IOException {
        FileOutputStream saveFile = new FileOutputStream("SaveGame.sav");
        saveFile.close();
        System.out.println("Cleared");
    }
}
