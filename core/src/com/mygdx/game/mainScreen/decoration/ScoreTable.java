package com.mygdx.game.mainScreen.decoration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.mainScreen.MainScreen;
import com.mygdx.game.ultils.Render;
// Lớp này dùng để tạo bảng điểm của trò chơi
public class ScoreTable implements Render {
    private static SpriteBatch BATCH;// đối tượng dùng để vẽ
    private static ScoreIcon SNAKE_ICON; // số lượng rắn cần bắt
    private static ScoreIcon GARBAGE_ICON; // số lượng rác đã thu thập được
    private static ScoreIcon TIME_ICON; // thời giam bắt rắn
    private static ScoreIcon PRESENT_GARBAGE_ICON;// số lượng rác hiện có trên bản đồ
    private static Texture TABLE;//Tạo background cho bảng điểm
    public ScoreTable(SpriteBatch batch){
        //khởi tạo
        if(BATCH==null)
            BATCH = batch;

        //nạp dữ liệu hình ảnh từ file
        SNAKE_ICON = new ScoreIcon(BATCH, "snakeIcon.png");
        GARBAGE_ICON = new ScoreIcon(BATCH, "garbageIcon.png");
        TIME_ICON = new ScoreIcon(BATCH, "timeIcon.png");
        PRESENT_GARBAGE_ICON = new ScoreIcon(BATCH, "garbage.png");
        TABLE = new Texture("table.jpg");

        //thiết lập tọa độ và các điều kiện của từng loại icon
        PRESENT_GARBAGE_ICON.setX(Gdx.graphics.getWidth() - ScoreIcon.getWIDTH()*4);
        PRESENT_GARBAGE_ICON.setY(Gdx.graphics.getHeight() - ScoreIcon.getHEIGHT());
        PRESENT_GARBAGE_ICON.setCondition(MainScreen.MAX_GARBAGE);
        SNAKE_ICON.setX(Gdx.graphics.getWidth() - ScoreIcon.getWIDTH()*3);
        SNAKE_ICON.setY(Gdx.graphics.getHeight() - ScoreIcon.getHEIGHT());
        SNAKE_ICON.setCondition(MainScreen.MAX_SNAKE);
        GARBAGE_ICON.setX(Gdx.graphics.getWidth() - ScoreIcon.getWIDTH()*2);
        GARBAGE_ICON.setY(Gdx.graphics.getHeight() - ScoreIcon.getHEIGHT());
        GARBAGE_ICON.setCondition(MainScreen.COLLECTIVE_GARBAGE);
        TIME_ICON.setX(Gdx.graphics.getWidth() - ScoreIcon.getWIDTH());
        TIME_ICON.setY(Gdx.graphics.getHeight() - ScoreIcon.getHEIGHT());
        TIME_ICON.setCondition(MainScreen.MAX_TIME);

    }

    //cập nhật giá trị cho các icon
    public void updateValues(int snake, int collectiveGarbage,int presentGarbage, int time ){
        SNAKE_ICON.setValue(snake);
        GARBAGE_ICON.setValue(collectiveGarbage);
        TIME_ICON.setValue(time);
        PRESENT_GARBAGE_ICON.setValue(presentGarbage);
    }
    //vẽ icon ra màn hình
    @Override
    public void render() {
        BATCH.draw(TABLE, Gdx.graphics.getWidth() - ScoreIcon.getWIDTH() * 4,
                Gdx.graphics.getHeight() - ScoreIcon.getHEIGHT()-30);
        PRESENT_GARBAGE_ICON.render();
        SNAKE_ICON.render();
        GARBAGE_ICON.render();
        TIME_ICON.render();
    }
}
