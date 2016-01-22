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
public class PowerGenerator extends Room {

    public PowerGenerator(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public int collectResource() {
        return 2 ^ (super.getSize() - 1) * 10 + 2 * (super.getSize() - 1) + super.getLevel();
    }

}
