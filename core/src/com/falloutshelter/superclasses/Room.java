/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.superclasses;

import com.badlogic.gdx.math.Rectangle;
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

    public Room(float x, float y, float width, float height) {
        rect = new Rectangle(x, y, width, height);
        level = 1;
        size = 1;
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
    
    public void levelUp() {
        if (level < 3) {
            level++;
        }
    }
    
    public int getSize() {
        return size;
    }
    
    public void upgradeSize() {
        size++;
    }
    
    

    public abstract int collectResource();
    
    public void setMaxCapacity(int cap) {
        maxCapacity = cap;
    }
    
    

    private int rushChance(int min, int max) {
        Random rand = new Random();

        int n = rand.nextInt(max) + min;
        return n;
    }
    
}
