package com.mygdx.game.endScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Lớp này tượng trưng cho màn hình kết thúc game
public class EndScreen implements Screen {
    private static SpriteBatch BATCH;//đối tượng dùng để vẽ
    private final EndScreenBg endScreenBg;// backGround cho screen
    private final ButtonTable buttonTable;// Danh sách các button
     static  Music music;// Nhạc nền
    //Khởi tạo các giả trị
    public EndScreen(SpriteBatch batch){
        if(BATCH==null)
            BATCH= batch;
        endScreenBg = new EndScreenBg(BATCH);
        music = Gdx.audio.newMusic(Gdx.files.internal("endMusic.mp3"));
        music.play();
        buttonTable = new ButtonTable(BATCH);
    }
    //Vẽ hình ảnh ra màn hình
    @Override
    public void render(float delta) {
        BATCH.begin();
        endScreenBg.render();
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

    }

    @Override
    public void dispose() {
    }
}
