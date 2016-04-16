/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.superclasses;

import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author scott
 */
public abstract class Entity {
    
    private Rectangle rect;
    private int health, maxHealth;
    
    public Entity(float x, float y, float width, float height) {
        rect = new Rectangle(x, y, width, height);
        maxHealth = 20;
        health = maxHealth;
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
    
    public Rectangle getRect() {
        return rect;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public void Levelup() {
        maxHealth += 5;
        health = maxHealth;
    }
    
    
}
