package com.myzt.game.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsManager {
    private static final String PREFERENCES_NAME = "GameSettings";
    private static final String SOUND_VOLUME_KEY = "SoundVolume";
    private static final String MUSIC_VOLUME_KEY = "MusicVolume";
    private static final String WINDOW_SIZE_KEY = "WindowSize";
    private static final String BACKGROUND_MUSIC_NAME="Background_Music";
    private static final String CLICK_SOUND="Click_Sound";
    private static Preferences preferences;

    public static void initialize() {
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    public static void saveSoundVolume(float soundVolume) {
        preferences.putFloat(SOUND_VOLUME_KEY, soundVolume);
        preferences.flush();
    }

    public static float getSoundVolume() {
        return preferences.getFloat(SOUND_VOLUME_KEY, 0.5f); // Default value: 0.5f
    }

    public static void saveMusicVolume(float musicVolume) {
        preferences.putFloat(MUSIC_VOLUME_KEY, musicVolume);
        preferences.flush();
    }
    public static void saveBackgroundMusic(String BGM){
        preferences.putString(BACKGROUND_MUSIC_NAME,BGM);
        preferences.flush();
    }
    public static String getBackgroundMusic(){
        return preferences.getString(BACKGROUND_MUSIC_NAME,"EDM Loop #1");
    }
    public static String getClickSound(){
        return preferences.getString(CLICK_SOUND,"click-button-140881");
    }
    public static float getMusicVolume() {
        return preferences.getFloat(MUSIC_VOLUME_KEY,  0.5f); // Default value: 0.5f
    }

    public static void saveWindowSize(String windowSize) {
        preferences.putString(WINDOW_SIZE_KEY, windowSize);
        preferences.flush();
    }

    public static String getWindowSize() {
        return preferences.getString(WINDOW_SIZE_KEY, "800x600"); // Default value: "800x600"
    }
}

