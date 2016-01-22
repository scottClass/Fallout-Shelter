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
public abstract class Entity {
    
    Rectangle rect;
    
    public Entity(float x, float y, float width, float height) {
        rect = new Rectangle(x, y, width, height);
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
    
    
}
