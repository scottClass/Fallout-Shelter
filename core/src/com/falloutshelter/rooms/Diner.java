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
public class Diner extends Room {

    public Diner(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public int collectResource() {
        //for now
        if (super.getConnections() == 1) {
            if (super.getLevel() == 1) {
                return 8;
            } else if (super.getLevel() == 2) {
                return 10;
            } else {
                return 12;
            }
        } else if (super.getConnections() == 2) {
            if(super.getLevel() == 1) {
                
            } else if (super.getLevel() == 2) {
                return 22;
            } else {
                return 26;
            }
        } else if (super.getConnections() == 3) {
            if(super.getLevel() == 1) {
                
            } else if (super.getLevel() == 2) {
                
            } else {
                return 40;
            }
        } 
        return 0;
    }

}
