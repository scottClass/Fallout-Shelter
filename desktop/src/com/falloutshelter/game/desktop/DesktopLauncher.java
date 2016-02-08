package com.falloutshelter.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.falloutshelter.game.MyGdxGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.addIcon("Icon.png", FileType.Internal);
        config.title = "Fallout Shelter";
        config.width = 1024;
        config.height = 768;
        new LwjglApplication(new MyGdxGame(), config);
    }
}
