/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.falloutshelter.game;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Scott
 */
public class AssetManager {

    public static Texture test;
    public static Texture buildIcon;
    public static Texture upgradeIcon;
    public static Texture cancelBuildIcon;
    public static Texture buildSpace;

    public static void Load() {
        test = new Texture("test.png");
        buildIcon = new Texture("build_icon.png");
        upgradeIcon = new Texture("upgrade_icon.png");
        cancelBuildIcon = new Texture("cancelBuild_icon.png");
        buildSpace = new Texture("buildSpace.png");
    }
}
