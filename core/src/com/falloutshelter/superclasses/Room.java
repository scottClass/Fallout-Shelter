/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.superclasses;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.falloutshelter.characters.Dweller;
import java.util.Random;

/**
 *
 * @author scott
 */
public abstract class Room {

    private Rectangle rect;
    private int level;
    private int size;
    private int maxCapacity;
    private Array<Dweller> dwellers;
    private String requiredSkill;
    private String roomName;
    private int toCollect;
    private boolean canCollect;

    public Room(float x, float y, float width, float height, String requiredSkill, String roomName) {
        rect = new Rectangle(x, y, width, height);
        this.requiredSkill = requiredSkill;
        dwellers = new Array<Dweller>();
        level = 1;
        maxCapacity = 2;
        size = 1;
        this.requiredSkill = this.requiredSkill.toLowerCase();
        this.requiredSkill = this.requiredSkill.replaceAll(" ", "");
        this.roomName = roomName;
        canCollect = false;
    }

    public float getX() {
        return rect.x;
    }

    public float getY() {
        return rect.y;
    }

    public float getWidth() {
        return rect.width;
    }

    public float getHeight() {
        return rect.height;
    }

    public int getLevel() {
        return level;
    }
    
    public int getToCollect() {
        return toCollect;
    }
    
    public void ChangeCanCollect() {
        canCollect = true;
    }
    
    public boolean getCanCollect() {
        return canCollect;
    }

    public String getRoomName() {
        return roomName;
    }

    public void levelUp() {
        if (level < 3) {
            level++;
        }
    }

    public int getSize() {
        return size;
    }
    
    public Rectangle getRect() {
        return rect;
    }

    private void upgradeSize() {
        if (size != 3) {
            size++;
            rect.width *= 2;
        }
    }

    /**
     * Merge 2 rooms together.
     *
     * @param a room that will remain
     * @param b room that will be destroyed and will pass all dwellers to room a
     */
    public void MergeRooms(Room a, Room b) {
        //if rooms are same type of rooms
        if (a.getRoomName().equals(b.getRoomName())) {
            //checks that rooms are the same level
            if (a.getLevel() == b.getLevel()) {
                a.setMaxCapacity(a.getMaxCapacity() + 2);
                for (Dweller d : b.getAssignedDwellers()) {
                    a.assignDweller(d);
                }
                b = null;
                a.upgradeSize();
            }
        }
    }

    public abstract int collectResource();

    public void setMaxCapacity(int cap) {
        maxCapacity = cap;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Array<Dweller> getAssignedDwellers() {
        return dwellers;
    }

    public void assignDweller(Dweller d) {
        if (dwellers.size < maxCapacity) {
            dwellers.add(d);
        } else {
            Dweller lowestRanked = new Dweller(1, 1, 1, 1);
            boolean first = true;
            for (Dweller e : dwellers) {
                if (requiredSkill.equals("strength")) {
                    if (first) {
                        lowestRanked = e;
                    } else {
                        if (e.getStrength() < lowestRanked.getStrength()) {
                            lowestRanked = e;
                        }
                    }
                } else if (requiredSkill.equals("perception")) {
                    if (first) {
                        lowestRanked = e;
                    } else {
                        if (e.getPerception() < lowestRanked.getPerception()) {
                            lowestRanked = e;
                        }
                    }
                } else if (requiredSkill.equals("endurance")) {
                    if (first) {
                        lowestRanked = e;
                    } else {
                        if (e.getEndurance() < lowestRanked.getEndurance()) {
                            lowestRanked = e;
                        }
                    }
                } else if (requiredSkill.equals("charisma")) {
                    if (first) {
                        lowestRanked = e;
                    } else {
                        if (e.getCharisma() < lowestRanked.getCharisma()) {
                            lowestRanked = e;
                        }
                    }
                } else if (requiredSkill.equals("intelligence")) {
                    if (first) {
                        lowestRanked = e;
                    } else {
                        if (e.getIntelligence() < lowestRanked.getIntelligence()) {
                            lowestRanked = e;
                        }
                    }
                } else if (requiredSkill.equals("agility")) {
                    if (first) {
                        lowestRanked = e;
                    } else {
                        if (e.getAgility() < lowestRanked.getAgility()) {
                            lowestRanked = e;
                        }
                    }
                } else if (requiredSkill.equals("luck")) {
                    if (first) {
                        lowestRanked = e;
                    } else {
                        if (e.getLuck() < lowestRanked.getLuck()) {
                            lowestRanked = e;
                        }
                    }
                }
                first = false;
            }
            for (int i = 0; i < dwellers.size; i++) {
                if (dwellers.get(i) == lowestRanked) {
                    dwellers.removeIndex(i);
                    dwellers.add(d);
                }
            }
        }
    }

    private int rushChance(int min, int max) {
        Random rand = new Random();

        int n = rand.nextInt(max) + min;
        return n;
    }
}
