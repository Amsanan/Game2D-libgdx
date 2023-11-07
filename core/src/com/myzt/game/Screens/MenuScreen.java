package com.myzt.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.myzt.game.MyGdxGame;
import com.myzt.game.Utilities.AssetLoader;
import com.myzt.game.Utilities.SettingsManager;

import java.util.concurrent.TimeUnit;

public class MenuScreen implements Screen {
    private Stage stage;
    private AssetLoader assetLoader;
    private MyGdxGame game;
    private Sound clickSound;
    public MenuScreen(MyGdxGame game) {
        SettingsManager.initialize();
        String[] size=SettingsManager.getWindowSize().split("x");
        stage = new Stage(new ScreenViewport());
        this.assetLoader = AssetLoader.getInstance();
        this.game=game;
        clickSound= game.getClickSound();
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);

        Skin skin = assetLoader.getSkin(); // Replace with the path to your skin file
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(skin.get("big", TextButton.TextButtonStyle.class));

        // Modify the button style to make the buttons bigger
        buttonStyle.font.getData().setScale(2.0f); // Increase the text size

        TextButton playButton = new TextButton("Play", buttonStyle);
        TextButton optionsButton = new TextButton("Options", buttonStyle);
        TextButton exitButton = new TextButton("Exit", buttonStyle);

        // Set the width of the buttons
        float buttonWidth = 300f; // Adjust the width as needed
        playButton.setWidth(buttonWidth);
        optionsButton.setWidth(buttonWidth);
        exitButton.setWidth(buttonWidth);

        Label titleLabel = new Label("ZT Game", skin, "title"); // Use your own title style

        // Define the width of the table as a percentage of the stage width
        float tableWidthPercentage = 0.6f; // Adjust the percentage as needed

        // Set the table's width as a percentage of the stage width
        table.setWidth(stage.getWidth() * tableWidthPercentage);

        // Add title with some padding
        table.add(titleLabel).padBottom(50).colspan(2).row();

        // Add the "Play" button with the specified width
        table.add(playButton).pad(10).width(buttonWidth).colspan(2).row();

        // Add the "Options" button with the specified width
        table.add(optionsButton).pad(10).width(buttonWidth).colspan(2).row();

        // Add the "Exit" button with the specified width
        table.add(exitButton).pad(10).width(buttonWidth).colspan(2).row();

        stage.addActor(table);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playClickSound();

                game.setScreen(new PlayScreen(game));
                // Handle the Play button click (e.g., switch to the game screen)
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playClickSound();

                game.setScreen(new OptionsScreen(game));
                // Handle the Options button click (e.g., open options screen)
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playClickSound();
                try {
                    TimeUnit.MILLISECONDS.sleep(110);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Gdx.app.exit(); // Exit the game
            }
        });


        setInputProcessingEnabled(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    // Implement other Screen interface methods as needed

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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    public void setInputProcessingEnabled(boolean enabled) {
        if (enabled) {
            Gdx.input.setInputProcessor(stage);
        } else {
            Gdx.input.setInputProcessor(null);
        }
    }
}
