package com.mygdx.game.startScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ultils.Render;

//vẽ backgound cho màn hình bắt đầu
public class StartScreenBg implements Render {
    private static SpriteBatch BATCH;

    private final Texture backGroundTexture ;
    public StartScreenBg(SpriteBatch batch) {
        if (BATCH == null)
            BATCH = batch;

        //background
        backGroundTexture = new Texture("bgStart.jpg");
    }

    @Override
    public void render() {
        //vẽ background
        BATCH.draw(backGroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }
}
