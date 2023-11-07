package com.myzt.game.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ArrayReflection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class AssetLoader {

    private AssetManager assetManager;
    private static AssetLoader instance;
    private Skin skin;

    public AssetLoader() {
        assetManager = new AssetManager();
        loadAssets(Gdx.files.internal("assets"));
        assetManager.finishLoading();
        skin = getAssetByName(Skin.class, "sgx");
    }

  private  void  loadAssets(FileHandle directory){
      if (directory.isDirectory()){
          for (FileHandle dir: directory.list()
               ) {
              loadAssets(dir);
          }
      }
      else {
          FileHandle File=directory;
          String[] parts=File.path().split("/");
          if (parts.length >= 2 && parts[0].equals("assets")) {
              String secondPart = parts[1];
              switch (secondPart){
                  case "skin":{
                      if (File.extension().equals("json")){
                         assetManager.load(File.path(), Skin.class);
                      }
                      break;
                  }
                  case "music":{
                      assetManager.load(File.path(), Music.class);
                    break;
                  }
                  case "sounds":{
                      assetManager.load(File.path(), Sound.class);
                  }
                  case "maps":{
                      if (File.extension().equals("tmx")){
                          assetManager.setLoader(TiledMap.class,new TmxMapLoader());
                          assetManager.load(File.path(), TiledMap.class);
                          System.out.println(File.path());
                      }
                      break;
                  }

              }
          } else {
              System.out.println("Invalid path");
          }
      }

  }
    public <T> T getAssetByName(Class<T> assetClass, String partialName) {
        Array<String> assetNames = assetManager.getAssetNames();

        for (String assetName : assetNames) {
            if (assetName.contains(partialName)) {
                if (assetManager.isLoaded(assetName, assetClass)) {
                    return assetManager.get(assetName, assetClass);
                }
            }
        }
        return null; // Asset not found
    }
    public static AssetLoader getInstance() {
        if (instance == null) {
            instance = new AssetLoader();
        }
        return instance;
    }
    public Skin getSkin() {
        return skin;
    }
   public HashMap<TiledMap ,String >getMaps(){
       HashMap<TiledMap ,String > map=new HashMap<TiledMap,String>();
       Array<TiledMap> maps=new Array<TiledMap>();
        for (String name:assetManager.getAssetNames()){
            for (TiledMap m:assetManager.getAll(TiledMap.class,maps)) {
                map.put(m,assetManager.getAssetFileName(m));
            }
        }
        return map;
   }

    public Array<String> getMusicNames(){
        Array<Music>musics = new Array<>();
        Array<String>musicnames=new Array<>();
        assetManager.getAll(Music.class,musics);
        for (Music m:musics
             ) {
            String name=assetManager.getAssetFileName(m);
            String[] parts=name.split("/");
            musicnames.add(parts[3]);
        }
        musicnames.sort();

        return musicnames;
    }

    public void dispose() {
        assetManager.dispose();
    }
}
