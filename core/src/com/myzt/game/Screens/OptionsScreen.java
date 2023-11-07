package com.myzt.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.myzt.game.MyGdxGame;
import com.myzt.game.Utilities.AssetLoader;
import com.myzt.game.Utilities.SettingsManager;

public class OptionsScreen implements Screen {
    private Stage stage;
    private AssetLoader assetLoader;
    private MyGdxGame game;
    private Music backgroundMusic;
    private Sound clickSound;
    public OptionsScreen(MyGdxGame game) {
        stage = new Stage(new ScreenViewport());
        this.assetLoader = AssetLoader.getInstance();
        this.game=game;
        backgroundMusic= game.getBackgroundMusic();
        clickSound=game.getClickSound();
    }

    @Override
    public void show() {
        SettingsManager.initialize();
        Table table = new Table();
        table.setFillParent(true);

        Skin skin = assetLoader.getSkin(); // Replace with the path to your skin file

        // Sound and Music Volume
        Label soundLabel = new Label("Sound Volume:", skin);
        Label musicLabel = new Label("Music Volume:", skin);
        final Slider soundVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundVolumeSlider.setValue(SettingsManager.getSoundVolume());
        final Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        musicVolumeSlider.setValue(SettingsManager.getMusicVolume());
        // Drop-down menu for Window Sizes
        Label windowSizeLabel = new Label("Window Size:", skin);
        final SelectBox<String> windowSizeSelectBox = new SelectBox<>(skin);
        String[] windowSizeOptions = { "1024x768","800x600", "1280x720","1000x800","fullscreen"};
        windowSizeSelectBox.setItems(windowSizeOptions);
        String[] size=SettingsManager.getWindowSize().split("x");
        if (Integer.parseInt(size[0]) >1280&&Integer.parseInt(size[1])  >720){
            windowSizeSelectBox.setSelected(windowSizeOptions[4]);
        }else {
        windowSizeSelectBox.setSelected(SettingsManager.getWindowSize());}
        Label bgmLabel = new Label("Background Music:", skin);
        final SelectBox<String> bgmSelectBox = new SelectBox<>(skin);
       bgmSelectBox.setItems(assetLoader.getMusicNames());
        bgmSelectBox.setSelected(SettingsManager.getBackgroundMusic());
        float labelWidth = soundLabel.getPrefWidth(); // Get the preferred width of the labels
        TextButton Back=new TextButton("Back",skin);
        table.add(soundLabel).width(labelWidth).pad(10).row();
        table.add(soundVolumeSlider).width(labelWidth).pad(10).row();
        table.add(musicLabel).width(labelWidth).pad(10).row();
        table.add(musicVolumeSlider).width(labelWidth).pad(10).row();
        table.add(windowSizeLabel).width(labelWidth).pad(10).row();
        table.add(windowSizeSelectBox).width(labelWidth).pad(10).row();
        table.add(bgmLabel).width(labelWidth).pad(10).row();
        table.add(bgmSelectBox).width(labelWidth).pad(10).row();
        table.add(Back).width(labelWidth).pad(10).row();
        stage.addActor(table);

        soundVolumeSlider.setValue(SettingsManager.getSoundVolume()); // Set default values for sound and music volume
        musicVolumeSlider.setValue(SettingsManager.getMusicVolume());

        soundVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float soundVolume = soundVolumeSlider.getValue();
                SettingsManager.saveSoundVolume(soundVolume);
            }
        });

        musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float musicVolume = musicVolumeSlider.getValue();
                SettingsManager.saveMusicVolume(musicVolume);
                if (backgroundMusic!=null){
                backgroundMusic.setVolume(SettingsManager.getMusicVolume());
                game.setBackgroundMusic(backgroundMusic);
                }
            }
        });

        windowSizeSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedWindowSize = windowSizeSelectBox.getSelected();
                if (selectedWindowSize.equals("fullscreen")){
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    selectedWindowSize= Gdx.graphics.getWidth() +"x"+Gdx.graphics.getHeight();
                }
                SettingsManager.saveWindowSize(selectedWindowSize);
                String[] parts=selectedWindowSize.split("x");
                if (parts.length == 2) {
                    String widthString = parts[0];
                    String heightString = parts[1];

                    int width = Integer.parseInt(widthString);
                    int height = Integer.parseInt(heightString);

                  Gdx.graphics.setWindowedMode(width,height);
                } else {
                    System.err.println("Invalid size format");
                }

            }
        });
        bgmSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedBGM = bgmSelectBox.getSelected();
                SettingsManager.saveBackgroundMusic(selectedBGM);
                // Update the background music
                updateBackgroundMusic(selectedBGM);
            }
        });
        Back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playClickSound();
                game.setScreen(new MenuScreen(game));

            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    // Implement other Screen interface methods as needed
    private void updateBackgroundMusic(String selectedBGM) {
        Music newBackgroundMusic = assetLoader.getAssetByName(Music.class, selectedBGM);
        if (newBackgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
            backgroundMusic = newBackgroundMusic;
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(SettingsManager.getMusicVolume());
            backgroundMusic.play();
        }
    }
    @Override
    public void render(float delta) {
        // Clear screen and draw the stage
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    stage.dispose();
    }

    // Implement other Screen interface methods
}

