/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.rooms;

import com.falloutshelter.superclasses.Room;

/**
 *
 * @author scott
 */
public class Diner extends Room {

    public Diner(float x, float y, float width, float height) {
        super(x, y, width, height, "agility");
    }

    @Override
    public int collectResource() {
        return (2 * super.getSize() * (super.getLevel() + 4)) - 2;
    }

    @Override
    public int getCost() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
