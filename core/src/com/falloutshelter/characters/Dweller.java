/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.characters;

import com.falloutshelter.superclasses.Entity;
import com.falloutshelter.superclasses.Room;
import java.util.Random;

/**
 *
 * @author scott
 */
public class Dweller extends Entity {

    //The traits that make this dweller special
    private int[] specialArray;
    private boolean isFemale;
    private boolean isPregnant;
    private String firstName;
    private String lastName;
    private Room assignedRoom;
    private int happiness;
    

    public Dweller(float x, float y, float width, float height) {
        super(x, y, width, height);
        specialArray = new int[7];
        for (int i = 0; i < specialArray.length; i++) {
            specialArray[i] = randomInt(1, 4);
        }
        int n = randomInt(1, 2);

        if (n == 1) {
            isFemale = false;
        } else {
            isFemale = true;
        }
        isPregnant = false;
        firstName = randomFirstName();
        lastName = randomLastName();
        assignedRoom = null;
    }
    
    public void addHappiness(int toAdd) {
        if (happiness + toAdd <= 100) {
            happiness += toAdd;
        } else {
            happiness = 100;
        }
    }
    
    public Room getAssignedRoom() {
        return assignedRoom;
    }
    
    public void changeAssignedRoom(Room newRoom) {
        assignedRoom = newRoom;
    }

    public int[] getSpecialTraits() {
        return specialArray;
    }

    public String getGender() {
        if (isFemale) {
            return "female";
        } else {
            return "male";
        }
    }
    
    public boolean getIfPregnant() {
        return isPregnant;
    }
    
    public void getPregnant() {
        isPregnant = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public int getStrength() {
        return specialArray[0];
    }
    
    public int getPerception() {
        return specialArray[1];
    }
    
    public int getEndurance() {
        return specialArray[2];
    }
    
    public int getCharisma() {
        return specialArray[3];
    }
    
    public int getIntelligence() {
        return specialArray[4];
    }
    
    public int getAgility() {
        return specialArray[5];
    }
    
    public int getLuck() {
        return specialArray[6];
    }

    /**
     * Generates a random number.
     *
     * @return the random number
     */
    private int randomInt(int min, int max) {
        Random rand = new Random();

        int n = rand.nextInt(max) + min;
        return n;
    }

    /**
     * Generates a random number and uses that number to pick a name.
     *
     * @return a random first name.
     */
    private String randomFirstName() {
        Random rand = new Random();

        int n = rand.nextInt(10) + 1;
        if (!isFemale) {
            switch (n) {
                case 1:
                    return "Jordan";
                case 2:
                    return "Tom";
                case 3:
                    return "Scott";
                case 4:
                    return "Eric";
                case 5:
                    return "Ted";
                case 6:
                    return "Johnny";
                case 7:
                    return "Alex";
                case 8:
                    return "Tim";
                case 9:
                    return "Dmitry";
                case 10:
                    return "Yuri";
            }
        } else {
            switch (n) {
                case 1:
                    return "Rhiannon";
                case 2:
                    return "Nicole";
                case 3:
                    return "Rebecca";
                case 4:
                    return "Clare";
                case 5:
                    return "Chelsey";
                case 6:
                    return "Kelly";
                case 7:
                    return "Raven";
                case 8:
                    return "Hitomi";
                case 9:
                    return "Alex";
                case 10:
                    return "Anne";
            }
        }
        //if method makes it to here something fucked up
        System.out.println("Something went wrong");
        return null;
    }

    /**
     * Generates a random number and uses that number to pick a name.
     *
     * @return a random last name.
     */
    private String randomLastName() {
        Random rand = new Random();

        int n = rand.nextInt(10) + 1;
        switch (n) {
            case 1:
                return "Meilke";
            case 2:
                return "Jones";
            case 3:
                return "Johnstone";
            case 4:
                return "Smith";
            case 5:
                return "Brown";
            case 6:
                return "Reid";
            case 7:
                return "Williams";
            case 8:
                return "Taylor";
            case 9:
                return "Davis";
            case 10:
                return "Love";
        }
        //if method makes it to here something fucked up
        System.out.println("Something went wrong");
        return null;
    }
    
    @Override
    public String toString() {
        String gender;
        if(isFemale) {
            gender = "Female";
        } else {
            gender = "Male";
        }
        return firstName + " " + lastName + "\n" + gender + "\nS: " + specialArray[0] + "\nP: " + specialArray[1] + 
                "\nE: " + specialArray[2] + "\nC: " + specialArray[3] + "\nI: " + specialArray[4] + 
                "\nA: " + specialArray[5] + "\nL: " + specialArray[6] + "\n"; 
    }
}
