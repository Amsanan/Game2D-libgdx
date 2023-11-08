package com.myzt.game.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.*;
import com.myzt.game.MyGdxGame;
import com.myzt.game.Utilities.AssetLoader;
import com.myzt.game.Utilities.SettingsManager;

public class PlayScreen implements Screen {
    private AssetLoader assetLoader;
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private MyGdxGame game;
    private Viewport viewport; // Use ExtendViewport for scaling
    private float scale;

    public PlayScreen(MyGdxGame game) {
        assetLoader = AssetLoader.getInstance();
        SettingsManager.initialize();
        this.game = game;

        tiledMap = assetLoader.getAssetByName(TiledMap.class, "ZTMap");
        scale = 1f / (int) tiledMap.getProperties().get("tilewidth");

        camera = new OrthographicCamera();
        viewport = new ExtendViewport((float) Gdx.graphics.getWidth() / 2*scale, (float) Gdx.graphics.getHeight() / 2*scale, camera);
        viewport.apply(); // Apply the viewport

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, scale);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    // Transition to the menu screen when the Esc key is pressed
                    game.setScreen(new MenuScreen(game));
                }
                return true; // Consume the event
            }
        });
    }

    public void handleInput(float delta) {
        // Handle input here
    }

    public void update(float delta) {
        // Update game logic here
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        update(delta);

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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
        tiledMap.dispose();
    }
}

