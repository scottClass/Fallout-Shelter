/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.rooms;

import com.badlogic.gdx.utils.Array;
import com.falloutshelter.characters.Dweller;
import com.falloutshelter.superclasses.Room;

/**
 *
 * @author scott
 */
public class LivingQuarters extends Room {

    public LivingQuarters(float x, float y, float width, float height) {
        super(x, y, width, height, "charisma");
        super.setBaseCost(100);
    }

    @Override
    public int collectResource() {
        Array<Dweller> temp;
        temp = super.getAssignedDwellers();
        Dweller female = null;
        Dweller male = null;
        for (Dweller d : temp) {
            if (d.getGender().equals("female")) {
                female = d;
            } else if (d.getGender().equals("male")) {
                male = d;
            }
        }
        if (male != null && female != null) {
            female.getPregnant();
        }
        return 0;
    }

    @Override
    public int getCost(int numBuilt) {
        return super.getBaseCost() + (numBuilt * 25);
    }

}
