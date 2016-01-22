/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.characters;

import com.falloutshelter.superclasses.Entity;
import java.util.Random;

/**
 *
 * @author scott
 */
public class Dweller extends Entity {
    
    //The traits that make this dweller special
    private int[] specialArray;
    private String name;
    private boolean isFemale;
    
    public Dweller(float x, float y, float width, float height) {
        super(x, y, width, height);
        specialArray = new int[7];
        for(int i = 0; i < specialArray.length; i++) {
            specialArray[i] = randomInt();
        }
    }
    
    public int[] getSpecialTraits() {
        return specialArray;
    }

    /**
     * Determines the SPECIAL traits a dweller will start off with
     *
     * @return the random number
     */
    private static int randomInt() {
        Random rand = new Random();

        int n = rand.nextInt(4) + 1;
        return n;
    }
    
    private String randomFirstName() {
        Random rand = new Random();

        int n = rand.nextInt(10) + 1;
        return null;
    }
    
    private String randomLastName() {
        Random rand = new Random();

        int n = rand.nextInt(10) + 1;
        switch(n) {
            case 1: return "Blake";
            case 2: return "Tom";
        }
        return null;
    }
}
