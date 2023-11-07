package com.myzt.game.Screens.Hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.myzt.game.Utilities.AssetLoader;
import com.myzt.game.Utilities.SettingsManager;

public class Hud {
    private Stage stage;
    private Viewport viewport;
    private AssetLoader loader;

    public Hud (SpriteBatch hb){
        loader=AssetLoader.getInstance();
        Skin skin=loader.getAssetByName(Skin.class,"glassy");
        SettingsManager.initialize();
        String[] size=SettingsManager.getWindowSize().split("x");
        viewport=new FitViewport(Integer.parseInt(size[0]),Integer.parseInt(size[1]));
        stage=new Stage(viewport,hb);
        Table table=new Table();
        table.top();
        table.setFillParent(true);

        stage.addActor(table);
    }

    public Stage getStage() {
        return stage;
    }

}
