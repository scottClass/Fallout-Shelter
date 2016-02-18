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
        super(x, y, width, height, "charisma", "livingquaters");
    }

    @Override
    public int collectResource() {
        Array<Dweller> temp;
        temp = super.getAssignedDwellers();
        Dweller female = null;
        Dweller male = null;
        for (Dweller d : temp) {
            if (d.getGender().equals("female")) {
                if (female != null) {
                    if (female.getCharisma() < d.getCharisma()) {
                        female = d;
                    }
                } else {
                    female = d;
                }
            } else if (d.getGender().equals("male")) {
                if (male != null) {
                    if (male.getCharisma() < d.getCharisma()) {
                        male = d;
                    }
                } else {
                    male = d;
                }
            }
        }
        if (male != null && female != null && !female.getAge() && !male.getAge()) {
            female.getPregnant();
        }
        return 0;
    }

}
