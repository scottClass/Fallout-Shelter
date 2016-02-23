/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.superclasses;

import com.badlogic.gdx.utils.Array;
import com.falloutshelter.characters.Dweller;
import java.io.Serializable;

/**
 *
 * @author johns6971
 */
public class Vault implements Serializable {
    
    private Array<Room> rooms;
    private Array<Dweller> dwellers;
    private Array<Integer> toCollect;
    
    private int energy, maxEnergy;
    private int food, maxFood;
    private int water, maxWater;
    private int numDwellers, maxDwellers;
    private int radAway, maxRadAway;
    private int stimpak, maxStimpak;
    
    
    public Vault() {
        rooms = new Array<Room>();
        dwellers = new Array<Dweller>();
        radAway = 0;
        maxRadAway = 15;
        stimpak = 0;
        maxStimpak = 20;

        numDwellers = 0;
        
        energy = 50;
        food = 50;
        water = 50;
        maxEnergy = 100;
        maxFood = 100;
        maxWater = 100;
        
        maxDwellers = 20;
    }
    
    public void addDweller(Dweller d) {
        dwellers.add(d);
        numDwellers++;
    }
    
    public void addRoom(Room r) {
        rooms.add(r);
    }
    
    public Array<Dweller> getDwellers() {
        return dwellers;
    }
    
    public Array<Room> getRooms() {
        return rooms;
    }
    
    public int getNumDwellers() {
        return numDwellers;
    }
    
    public void addEnergy(int a) {
        if(energy + a < maxEnergy) {
            energy += a;
        } else {
            energy = maxEnergy;
        }
    }
    
    public void addFood(int a) {
        if(food + a < maxFood) {
            food += a;
        } else {
            food = maxFood;
        }
    }
    
    public void addWater(int a) {
        if(water + a < maxWater) {
            water += a;
        } else {
            water = maxWater;
        }
    }
    
    public void addRadaway(int a) {
        if(radAway + a < maxRadAway) {
            radAway += a;
        } else {
            radAway = maxRadAway;
        }
    }
    
    public void addStimpak(int a) {
        if(stimpak + a < maxStimpak) {
            stimpak += a;
        } else {
            stimpak = maxStimpak;
        }
    }
}
