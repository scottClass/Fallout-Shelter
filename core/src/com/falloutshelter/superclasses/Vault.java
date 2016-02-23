/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.superclasses;

import com.badlogic.gdx.utils.Array;
import com.falloutshelter.characters.Dweller;

/**
 *
 * @author johns6971
 */
public class Vault {
    
    Array<Room> rooms;
    Array<Dweller> dwellers;
    
    public Vault() {
        rooms = new Array<Room>();
        dwellers = new Array<Dweller>();
    }
    
    public void addDweller(Dweller d) {
        dwellers.add(d);
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
}
