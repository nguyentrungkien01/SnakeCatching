package com.mygdx.game.mainScreen.decoration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ultils.Render;

// Lớp này có tác dụng táo đối tưởng hiển thị trên bảng điểm
public class ScoreIcon implements Render {
    private final Texture icon; //khung chứa ảnh của vật thể cần vẽ
    private static SpriteBatch BATCH; // đối tượng dùng để vẽ
    private float x;// tọa độ x để vẽ
    private float y;// tọa độ y để vẽ
    private final static float WIDTH = 130; // chiều rộng
    private final static float HEIGHT = 100;// chiều cao
    private int value = 0;// giá trị sẽ vẽ ra
    private final BitmapFont bitmapFont; // đối tượng dùng để tạo font chữ cho trò chơi
    private final GlyphLayout glyphLayout;
    private int condition;// điều kiện vẽ của đối tượng

    // khởi gán các giá trị cần thiết ban đầu
    public ScoreIcon(SpriteBatch batch, String path) {
        //khởi gán giá trị đối tượng dùng để vẽ
        if (BATCH == null)
            BATCH = batch;
        icon = new Texture(path);//nạp hình ảnh từ file
        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt")); // tạo font chữ bitmap từ file
        glyphLayout=new GlyphLayout();
    }

    // Phương thức này được gọi nhằm dể vẽ đồng xu ra màn hình liên tục
    @Override
    public void render() {
        // Tạo một layout cho giá trị cần hiển thị với 1 font cụ thể
        glyphLayout.setText(bitmapFont, value + "/" + condition);
        //vẽ hình ảnh
        BATCH.draw(getIcon(), this.x, this.y, WIDTH, HEIGHT);
        //vẽ giá trị
        bitmapFont.draw(BATCH, glyphLayout, this.x + WIDTH / 2 - glyphLayout.width / 2, y);
    }

    /**
     * ******* Các getter, setter cần dùng đến ************
     */

    public static float getWIDTH() {
        return WIDTH;
    }

    public static float getHEIGHT() {
        return HEIGHT;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public Texture getIcon() {
        return icon;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
}
