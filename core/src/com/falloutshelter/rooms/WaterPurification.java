/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.rooms;

/**
 *
 * @author scott
 */
public class WaterPurification extends Room {

    public WaterPurification(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public int collectResource() {
        //for now
        return 10;
    }
    
}
