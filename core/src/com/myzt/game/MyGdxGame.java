package com.myzt.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.myzt.game.Screens.MenuScreen;
import com.myzt.game.Utilities.AssetLoader;
import com.myzt.game.Utilities.SettingsManager;

public class MyGdxGame extends Game {
	private Music backgroundMusic;
	private Sound clickSound;
	private  AssetLoader loader;
	@Override
	public void create () {
		SettingsManager.initialize();
		String[] size=SettingsManager.getWindowSize().split("x");
		Gdx.graphics.setWindowedMode(Integer.parseInt(size[0]),Integer.parseInt(size[1]));
		loader=AssetLoader.getInstance();
		initiateClickSound();
		initiateBGM();
		setScreen(new MenuScreen(this));


	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
	super.dispose();
	}
	private void initiateBGM(){

		backgroundMusic=loader.getAssetByName(Music.class, SettingsManager.getBackgroundMusic());
		if (backgroundMusic != null) {
			backgroundMusic.setLooping(true);
			backgroundMusic.setVolume(SettingsManager.getMusicVolume());
			backgroundMusic.play();
		}
	}
	private void initiateClickSound() {
		clickSound = loader.getAssetByName(Sound.class, SettingsManager.getClickSound());
	}
	public void playClickSound(){
		clickSound.play(SettingsManager.getSoundVolume());
	}
	public Sound getClickSound(){

		return clickSound;
	}

	public void setClickSound(Sound clickSound) {
		this.clickSound = clickSound;
	}

	public Music getBackgroundMusic(){
		return  backgroundMusic;
	}

	public void setBackgroundMusic(Music backgroundMusic) {
		this.backgroundMusic = backgroundMusic;
	}
}
