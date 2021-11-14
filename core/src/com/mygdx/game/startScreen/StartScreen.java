package com.mygdx.game.startScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;

//màn hình bắt đầu game
public class StartScreen implements Screen {
    private static SpriteBatch BATCH;//đối tượng dùng để vẽ
    private final StartScreenBg bg;//background
    private final ButtonTable buttonTable ;//danh sách các button
    private final Music music;//nhạc nền
    //Khởi gán các giá trị
    public StartScreen(SpriteBatch batch) {
        if (BATCH == null)
            BATCH = batch;
        bg= new StartScreenBg(BATCH);
        buttonTable = new ButtonTable(BATCH);
        music = Gdx.audio.newMusic(Gdx.files.internal("startMusic.mp3"));
        music.setLooping(true);
        music.play();

    }
    @Override
    public void render(float delta) {
        if(MainGame.MODE == MainGame.Mode.START)
            this.dispose();
        BATCH.begin();
        bg.render();
        buttonTable.render();
        BATCH.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        music.stop();
    }

    @Override
    public void dispose() {
    }
}
