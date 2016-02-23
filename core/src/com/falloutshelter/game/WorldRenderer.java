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
import com.falloutshelter.superclasses.Vault;
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

    private Vault vault;
    private Array<Rectangle> buildSpaces;
    private Array<Rectangle> selectBuildRect;
    private long startTime;
    private long secondsPassed;
    private long nextSave;
    private int caps;
    private BitmapFont font;
    private SpriteBatch batch;
    private Rectangle buildIconRect;
    private State currentFirstState;
    private BuildState currentBuildState;
    private boolean buttonPressed;
    private Dweller currentDwellerSelected;
    private Room currentRoomSelected;

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

        DINER, POWERG, WATERP, LIVINGQ, MEDBAY, SCIENCEL, ELEVATOR, NOTHING,
    }

    public WorldRenderer() {
        AssetManager.Load();

        vault = new Vault("Vault 101");

        currentFirstState = SELECT;
        currentBuildState = NOTHING;
        currentDwellerSelected = null;
        currentRoomSelected = null;

        caps = 500;

        buildSpaces = new Array<Rectangle>();
        selectBuildRect = new Array<Rectangle>();

        for (int i = 0; i < 6; i++) {
            selectBuildRect.add(new Rectangle((i * 133), Gdx.graphics.getHeight() - 400, 133, 200));
        }

        buildSpaces.add(new Rectangle(100, Gdx.graphics.getHeight() - 100, 100, 50));

        startTime = System.currentTimeMillis();



        font = new BitmapFont();
        batch = new SpriteBatch();
        font.setColor(Color.GREEN);

        secondsPassed = 0;
        nextSave = secondsPassed + 10;

        buildIconRect = new Rectangle(Gdx.graphics.getWidth() - 40, Gdx.graphics.getHeight() - 40, 30, 30);
        buttonPressed = false;
        //Load();
    }

    /**
     * Draws rooms and Dwellers in the vaults arrayLists.
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        CalculateLogic();

        batch.begin();
        font.draw(batch, secondsPassed + "", 10, 20);
        font.draw(batch, caps + "", 35, Gdx.graphics.getHeight() - 15);
        batch.draw(AssetManager.capIcon, 10, Gdx.graphics.getHeight() - 30, 20, 20);
        for (Dweller d : vault.getDwellers()) {
            batch.draw(AssetManager.test, d.getX(), d.getY(), d.getWidth(), d.getHeight());
        }
        for (Room r : vault.getRooms()) {
            //put room specific images here
            //Uncomment when different imnages are available
//            if(r.getRoomName().equals("livingquaters")) {
//                batch.draw(AssetManager.livingQuarters, r.getX(), r.getY(), r.getWidth(), r.getHeight());
//            } else if (r.getRoomName().equals("diner")) {
//                batch.draw(AssetManager.diner, r.getX(), r.getY(), r.getWidth(), r.getHeight());
//            } else if (r.getRoomName().equals("powergenerator")) {
//                batch.draw(AssetManager.powerGenerator, r.getX(), r.getY(), r.getWidth(), r.getHeight());
//            } else if (r.getRoomName().equals("waterpurification")) {
//                batch.draw(AssetManager.waterPurification, r.getX(), r.getY(), r.getWidth(), r.getHeight());
//            } else if (r.getRoomName().equals("sciencelab")) {
//                batch.draw(AssetManager.scienceLab, r.getX(), r.getY(), r.getWidth(), r.getHeight());
//            } else if (r.getRoomName().equals("medbay")) {
//                batch.draw(AssetManager.medbay, r.getX(), r.getY(), r.getWidth(), r.getHeight());
//            }
            batch.draw(AssetManager.test, r.getX(), r.getY(), r.getWidth(), r.getHeight());
        }
        if (currentFirstState == SELECT) {
            batch.draw(AssetManager.buildIcon, buildIconRect.getX(), buildIconRect.getY(), buildIconRect.getWidth(), buildIconRect.getHeight());
        } else if (currentFirstState == BUILD) {
            batch.draw(AssetManager.cancelBuildIcon, buildIconRect.getX(), buildIconRect.getY(), buildIconRect.getWidth(), buildIconRect.getHeight());
            if (currentBuildState != NOTHING) {
                for (Rectangle r : buildSpaces) {
                    batch.draw(AssetManager.buildSpace, r.getX(), r.getY(), r.getWidth(), r.getHeight());
                }
            } else {
                int i = 0;
                for (Rectangle r : selectBuildRect) {
                    batch.draw(AssetManager.buildSpace, r.getX(), r.getY(), r.getWidth(), r.getHeight());
                    switch (i) {
                        case 0:
                            font.draw(batch, "Living \nQuarters", (r.getX() + (r.getWidth() / 2)) - 25, r.getY() + (r.getHeight() / 2));
                            break;
                        case 1:
                            font.draw(batch, "Diner", (r.getX() + (r.getWidth() / 2)) - 25, r.getY() + (r.getHeight() / 2));
                            break;
                        case 2:
                            font.draw(batch, "Power \nGenerator", (r.getX() + (r.getWidth() / 2)) - 25, r.getY() + (r.getHeight() / 2));
                            break;
                        case 3:
                            font.draw(batch, "Water \nPurification", (r.getX() + (r.getWidth() / 2)) - 25, r.getY() + (r.getHeight() / 2));
                            break;
                        case 4:
                            font.draw(batch, "Science \nLab", (r.getX() + (r.getWidth() / 2)) - 25, r.getY() + (r.getHeight() / 2));
                            break;
                        case 5:
                            font.draw(batch, "Medbay", (r.getX() + (r.getWidth() / 2)) - 25, r.getY() + (r.getHeight() / 2));
                            break;
                    }
                    i++;
                }
            }
        }
        batch.end();
    }

    /**
     * Does necessary math and logic outside of render method
     */
    public void CalculateLogic() {
        secondsPassed = (System.currentTimeMillis() - startTime) / 1000;
        for (Room r : vault.getRooms()) {
            if (r.getToCollect() <= secondsPassed) {
                if (r.getRoomName().equals("diner")) {
                    r.ChangeCanCollect();
                    r.addToCollect(r.getToCollect());
                } else if (r.getRoomName().equals("medbay")) {
                    r.ChangeCanCollect();
                    r.addToCollect(r.getToCollect());
                } else if (r.getRoomName().equals("livingquaters")) {
                    r.collectResource();
                    r.addToCollect(r.getToCollect());
                } else if (r.getRoomName().equals("powergenerator")) {
                    r.ChangeCanCollect();
                    r.addToCollect(r.getToCollect());
                } else if (r.getRoomName().equals("sciencelab")) {
                    r.ChangeCanCollect();
                    r.addToCollect(r.getToCollect());
                } else if (r.getRoomName().equals("waterpurification")) {
                    r.ChangeCanCollect();
                    r.addToCollect(r.getToCollect());
                }
            }
        }
        if (Gdx.input.isKeyJustPressed(Keys.L)) {
            //Load();
        } else if (Gdx.input.isKeyJustPressed(Keys.R)) {
            vault.addDweller(new Dweller(20, 20, 20, 50));
            System.out.println(vault.getDwellers().get(vault.getNumDwellers() - 1));
        } else if (Gdx.input.isKeyJustPressed(Keys.C)) {
//            try {
//                clearSave();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            for (Room r : vault.getRooms()) {
                System.out.println(r.getRoomName());
            }
        } else if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            for (Dweller d : vault.getDwellers()) {
                System.out.println(d);
            }
        } else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            buttonPressed = true;
        } else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && buttonPressed) {
            buttonPressed = false;
            int clickX = Gdx.input.getX();
            int clickY = (Gdx.graphics.getHeight() - Gdx.input.getY());
            Rectangle rect = new Rectangle(clickX, clickY, 5, 5);
            if (currentFirstState == BUILD) {
                if (rect.overlaps(buildIconRect)) {
                    currentFirstState = SELECT;
                    currentBuildState = NOTHING;
                    System.out.println(currentFirstState);
                }
                if (currentBuildState == NOTHING) {
                    for (Rectangle r : selectBuildRect) {
                        if (rect.overlaps(r)) {
                            for (int i = 0; i < 6; i++) {
                                if (r.getX() == (i * 133)) {
                                    switch (i) {
                                        case 0:
                                            currentBuildState = LIVINGQ;
                                            break;
                                        case 1:
                                            currentBuildState = DINER;
                                            break;
                                        case 2:
                                            currentBuildState = POWERG;
                                            break;
                                        case 3:
                                            currentBuildState = WATERP;
                                            break;
                                        case 4:
                                            currentBuildState = SCIENCEL;
                                            break;
                                        case 5:
                                            currentBuildState = MEDBAY;
                                            break;
                                    }
                                }
                            }
                            System.out.println(currentBuildState);
                            break;
                        }
                    }
                } else {
                    boolean clickedBuildSpace = false;
                    Rectangle temp = null;
                    for (Rectangle r : buildSpaces) {
                        if (rect.overlaps(r)) {
                            clickedBuildSpace = true;
                            temp = r;
                        }
                    }
                    if (clickedBuildSpace) {
                        boolean builtRoom = false;
                        if (currentBuildState == DINER) {
                            if (getCost("diner")) {
                                builtRoom = true;
                                vault.addRoom(new Diner(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
                                if (temp.getX() + 100 != Gdx.graphics.getWidth()) {
                                    buildSpaces.add(new Rectangle(temp.getX() + 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                if (temp.getX() - 100 != 0) {
                                    buildSpaces.add(new Rectangle(temp.getX() - 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                currentFirstState = SELECT;
                                currentBuildState = NOTHING;
                            }
                        } else if (currentBuildState == POWERG) {
                            if (getCost("powergenerator")) {
                                builtRoom = true;
                                vault.addRoom(new PowerGenerator(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
                                if (temp.getX() + 100 != Gdx.graphics.getWidth()) {
                                    buildSpaces.add(new Rectangle(temp.getX() + 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                if (temp.getX() - 100 != 0) {
                                    buildSpaces.add(new Rectangle(temp.getX() - 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                currentFirstState = SELECT;
                                currentBuildState = NOTHING;
                            }
                        } else if (currentBuildState == WATERP) {
                            if (getCost("waterpurification")) {
                                builtRoom = true;
                                vault.addRoom(new WaterPurification(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
                                if (temp.getX() + 100 != Gdx.graphics.getWidth()) {
                                    buildSpaces.add(new Rectangle(temp.getX() + 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                if (temp.getX() - 100 != 0) {
                                    buildSpaces.add(new Rectangle(temp.getX() - 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                currentFirstState = SELECT;
                                currentBuildState = NOTHING;
                            }
                        } else if (currentBuildState == LIVINGQ) {
                            if (getCost("livingquarters")) {
                                builtRoom = true;
                                vault.addRoom(new LivingQuarters(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
                                if (temp.getX() + 100 != Gdx.graphics.getWidth()) {
                                    buildSpaces.add(new Rectangle(temp.getX() + 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                if (temp.getX() - 100 != 0) {
                                    buildSpaces.add(new Rectangle(temp.getX() - 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                currentFirstState = SELECT;
                                currentBuildState = NOTHING;
                            }
                        } else if (currentBuildState == MEDBAY) {
                            if (getCost("medbay")) {
                                builtRoom = true;
                                vault.addRoom(new Medbay(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
                                if (temp.getX() + 100 != Gdx.graphics.getWidth()) {
                                    buildSpaces.add(new Rectangle(temp.getX() + 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                if (temp.getX() - 100 != 0) {
                                    buildSpaces.add(new Rectangle(temp.getX() - 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                currentFirstState = SELECT;
                                currentBuildState = NOTHING;
                            }
                        } else if (currentBuildState == SCIENCEL) {
                            if (getCost("sciencelab")) {
                                builtRoom = true;
                                vault.addRoom(new ScienceLab(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight()));
                                if (temp.getX() + 100 != Gdx.graphics.getWidth()) {
                                    buildSpaces.add(new Rectangle(temp.getX() + 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                if (temp.getX() - 100 != 0) {
                                    buildSpaces.add(new Rectangle(temp.getX() - 100, temp.getY(), temp.getWidth(), temp.getHeight()));
                                }
                                currentFirstState = SELECT;
                                currentBuildState = NOTHING;
                            }
                        }
                        //Removes buildSpace after building a room
                        if (builtRoom) {
                            for (Rectangle r : buildSpaces) {
                                if (r == temp) {
                                    buildSpaces.removeValue(r, true);
                                }
                            }
                            //checks if any of the build spaces are in an area that already has a room
                            for (Rectangle r : buildSpaces) {
                                for (Room d : vault.getRooms()) {
                                    if (d.getRect().overlaps(r)) {
                                        buildSpaces.removeValue(r, true);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (currentFirstState == SELECT) {
                if (rect.overlaps(buildIconRect)) {
                    currentFirstState = BUILD;
                    System.out.println(currentFirstState);
                }
                for (Dweller d : vault.getDwellers()) {
                    if (rect.overlaps(d.getRect())) {
                        currentDwellerSelected = d;
                        currentRoomSelected = null;
                    }
                }
                boolean hasClicked = false;
                for (Room r : vault.getRooms()) {
                    if (rect.overlaps(r.getRect())) {
                        if (r.getCanCollect()) {
                            if (r.getRoomName().equals("diner")) {
                                vault.addFood(r.collectResource());
                            } else if (r.getRoomName().equals("medbay")) {
                                vault.addRadaway(r.collectResource());
                            } else if (r.getRoomName().equals("powergenerator")) {
                                vault.addEnergy(r.collectResource());
                            } else if (r.getRoomName().equals("sciencelab")) {
                                vault.addStimpak(r.collectResource());
                            } else if (r.getRoomName().equals("waterpurification")) {
                                vault.addWater(r.collectResource());
                            }
                        } else {
                            currentRoomSelected = r;
                            System.out.println("Selected a " + currentRoomSelected.getRoomName());
                            currentDwellerSelected = null;
                        }
                        hasClicked = true;
                    }
                }
                if(!hasClicked) {
                    currentRoomSelected = null;
                    System.out.println("Deselected");
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
            for (Room r : vault.getRooms()) {
                if (r.getRoomName().equals(desiredBuild)) {
                    n++;
                }
            }
            if ((100 + (25 * n)) > caps) {
                System.out.println("Not enough caps. Cost: " + (100 + (25 * n)));
                return false;
            } else {
                caps = caps - (100 + (25 * n));
                return true;
            }
        } else if (desiredBuild.equals("medbay") || desiredBuild.equals("sciencelab")) {
            int n = 0;
            for (Room r : vault.getRooms()) {
                if (r.getRoomName().equals(desiredBuild)) {
                    n++;
                }
            }
            if ((400 + (100 * n)) > caps) {
                System.out.println("Not enough caps. Cost: " + (400 + (100 * n)));
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
            caps = (Integer) save.readObject();
            vault = (Vault) save.readObject();
            buildSpaces = (Array<Rectangle>) save.readObject();
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
            save.writeObject(caps);
            save.writeObject(vault);
            save.writeObject(buildSpaces);

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
